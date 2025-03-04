package com.fashionmall.order.service;

import com.fashionmall.common.exception.CustomException;
import com.fashionmall.common.exception.ErrorResponseCode;
import com.fashionmall.common.moduleApi.dto.*;
import com.fashionmall.common.moduleApi.util.ModuleApiUtil;
import com.fashionmall.common.response.PageInfoResponseDto;
import com.fashionmall.order.dto.request.OrderItemRequestDto;
import com.fashionmall.order.dto.request.OrderPaymentRequestDto;
import com.fashionmall.order.dto.response.OrderItemDetailResponseDto;
import com.fashionmall.order.dto.response.OrdersDetailResponseDto;
import com.fashionmall.order.dto.response.OrdersResponseDto;
import com.fashionmall.order.dto.response.PaymentResponseDto;
import com.fashionmall.order.entity.BillingKey;
import com.fashionmall.order.entity.Orders;
import com.fashionmall.order.entity.Payment;
import com.fashionmall.order.enums.OrderStatus;
import com.fashionmall.order.event.StockDeductEvent;
import com.fashionmall.order.event.StockRestoreEvent;
import com.fashionmall.order.infra.iamPort.dto.IamPortResponseDto;
import com.fashionmall.order.infra.iamPort.util.IamPortClient;
import com.fashionmall.order.repository.BillingKeyRepository;
import com.fashionmall.order.repository.OrdersRepository;
import com.fashionmall.order.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class OrdersServiceImpl implements OrdersService {

    private final ModuleApiUtil moduleApiUtil;
    private final OrdersRepository ordersRepository;
    private final PaymentRepository paymentRepository;
    private final BillingKeyRepository billingKeyRepository;
    private final IamPortClient iamPortClient;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    @Override
    public Long createAndPaymentOrder(OrderPaymentRequestDto orderPaymentRequestDto) {
        //<<주문>>
        Long userId = orderPaymentRequestDto.getUserId();
        Long couponId = orderPaymentRequestDto.getCouponId();
        Long deliveryAddressId = orderPaymentRequestDto.getDeliveryAddressId();
        Long billingKeyId = orderPaymentRequestDto.getBillingKeyId();

        //데이터 가져오기
        List<CouponDto> userCouponApi = moduleApiUtil.getUserCouponApi(userId);
        List<DeliveryAddressDto> userDeliveryAddressApi = moduleApiUtil.getUserDeliveryAddressApi(userId);
        List<BillingKey> billingKeys = billingKeyRepository.findByUserId(userId);
        List<CartItemDto> isSelectedItemApi = moduleApiUtil.getIsSelectedItemApi();
        List<Long> itemIds = isSelectedItemApi.stream()
                .map(CartItemDto::getId)
                .toList();
        List<ItemDetailInfoDto> itemDetailInfoApi = moduleApiUtil.getItemDetailInfoApi(itemIds);

        List<ItemDetailDto> list = itemDetailInfoApi.stream()
                .map(itemInfo -> new ItemDetailDto(
                        itemInfo.getId(),
                        itemInfo.getItemDetailName(),
                        itemInfo.getPrice(),
                        itemInfo.getItemDiscountValue(),
                        itemInfo.getDiscountType(),
                        0,
                        itemInfo.getImageUrl()
                )).toList();

        Map<Long, ItemDetailDto> collect = list.stream().collect(Collectors.toMap(ItemDetailDto::getId, itemDetailDto -> itemDetailDto));

        for (CartItemDto cartItemDto : isSelectedItemApi) {
            ItemDetailDto itemDetailDto = collect.get(cartItemDto.getId());
            if (itemDetailDto != null) {
                itemDetailDto.setQuantity(cartItemDto.getQuantity());
            } else {
                throw new CustomException(ErrorResponseCode.WRONG_ITEM_DETAIL_ID);
            }
        }

        List<ItemDetailDto> itemDetailApi = List.copyOf(collect.values());

        //주문 항목 생성
        List<OrderItemRequestDto> orderItems = itemDetailApi.stream()
                .map(itemDetail -> {
                    int originalPrice = itemDetail.getPrice();
                    int itemDiscountPrice = itemDetail.getDiscountType().equals("RATE")
                            ? (int) (originalPrice * (itemDetail.getItemDiscountValue() / 100.0))
                            : itemDetail.getItemDiscountValue();
                    int finalPrice = originalPrice - itemDiscountPrice;

                    return new OrderItemRequestDto(
                            itemDetail.getId(),
                            itemDetail.getName(),
                            itemDetail.getQuantity(),
                            originalPrice,
                            itemDiscountPrice,
                            finalPrice,
                            itemDetail.getImageUrl()
                    );
                })
                .toList();

        orderPaymentRequestDto.setOrderItemsDto(orderItems);

        //재고 확인(ver.2)
        List<Long> itemDetailIds = orderItems.stream()
                .map(OrderItemRequestDto::getItemDetailId)
                .toList();

        Map<Long, Integer> itemQuantityApi = moduleApiUtil.getItemStockApi(itemDetailIds);
        for (OrderItemRequestDto orderItem : orderItems) {
            Long itemDetailId = orderItem.getItemDetailId();
            int quantity = orderItem.getQuantity();

            Integer orDefault = itemQuantityApi.getOrDefault(itemDetailId, 0);
            if (quantity > orDefault) {
                throw new CustomException(ErrorResponseCode.OUT_OF_STOCK);
            }
        }

        //배송지 설정
        if (deliveryAddressId != null) {
            DeliveryAddressDto deliveryAddressDto = userDeliveryAddressApi.stream()
                    .filter(deliveryAddress -> deliveryAddressId.equals(deliveryAddress.getId()))
                    .findFirst()
                    .orElseThrow(() -> new CustomException(ErrorResponseCode.NOT_FOUND));

            String zipcode = deliveryAddressDto.getZipcode();
            String roadAddress = deliveryAddressDto.getRoadAddress();

            orderPaymentRequestDto.setZipcode(zipcode);
            orderPaymentRequestDto.setRoadAddress(roadAddress);
        }

        //가격 계산
        int totalPrice = itemDetailApi.stream().mapToInt(ItemDetailDto::getTotalPrice).sum();
        int totalItemDiscountPrice = orderItems.stream().mapToInt(OrderItemRequestDto::getTotalItemDiscountPrice).sum();
        int couponDiscountPrice = 0;

        //쿠폰 적용
        if (couponId != null) {
            CouponDto couponDto = userCouponApi.stream()
                    .filter(coupon -> coupon.getId().equals(couponId))
                    .findFirst()
                    .orElseThrow(() -> new CustomException(ErrorResponseCode.NOT_FOUND));

            String discountType = couponDto.getDiscountType();
            int discountValue = couponDto.getDiscountValue();

            if (discountType.equals("RATE")) {
                couponDiscountPrice = (int) (totalPrice * (discountValue / 100.0));
            } else {
                couponDiscountPrice = discountValue;
            }
        }

        int paymentPrice = totalPrice - totalItemDiscountPrice - couponDiscountPrice;

        //billingKey 검증
        if (billingKeys.isEmpty()) {
            throw new CustomException(ErrorResponseCode.NOT_FOUND);
        }

        if (billingKeyId != null && !billingKeyRepository.existsById(billingKeyId)) {
            throw new CustomException(ErrorResponseCode.ORDER_NOT_FOUND_BILLING_KEY);
        }

        //DTO 정보 입력
        orderPaymentRequestDto.setTotalPrice(totalPrice);
        orderPaymentRequestDto.setCouponDiscountPrice(couponDiscountPrice);
        orderPaymentRequestDto.setTotalItemDiscountPrice(totalItemDiscountPrice);
        orderPaymentRequestDto.setPaymentPrice(paymentPrice);

        Orders order = ordersRepository.save(orderPaymentRequestDto.toOrders());

        //<<결제>>
        //빌링키 가져오기
        BillingKey billingKey = billingKeyRepository.findById(billingKeyId)
                .orElseThrow(() -> new CustomException(ErrorResponseCode.ORDER_NOT_FOUND_BILLING_KEY));
        orderPaymentRequestDto.setCustomerUid(billingKey.getCustomerUid());

        //merchant_uid 추가
        String merchantUid = createMerchantUid(order.getId());
        orderPaymentRequestDto.setMerchantUid(merchantUid);

        //비인증 일회성 결제
        //IamPortResponseDto<PaymentResponseDto> post = iamPortClient.onetimePayment(paymentRequestDto);
        IamPortResponseDto<PaymentResponseDto> post = iamPortClient.billingKeyPayment(orderPaymentRequestDto);

        String impUid = post.getResponse().getImpUid();
        long unixPaidAt = (long) post.getResponse().getPaidAt();
        LocalDateTime paidAt = LocalDateTime.ofInstant(Instant.ofEpochSecond(unixPaidAt), ZoneId.systemDefault());

        Payment payment = orderPaymentRequestDto.toPayment(order, billingKey, impUid, paidAt);

        paymentRepository.save(payment);

        order.setPayment(payment);

        //재고차감
        eventPublisher.publishEvent(new StockDeductEvent(order.getId()));

        return order.getId();
    }

    @Override
    public PageInfoResponseDto<OrdersResponseDto> getOrders(Long userId, int pageNo, int size) {
        PageRequest pageRequest = PageRequest.of(pageNo - 1, size);
        return ordersRepository.findOrdersByUserId(userId, pageRequest);
    }

    @Override
    public OrdersDetailResponseDto getOrderDetail(Long userId, Long orderId) {
        OrdersDetailResponseDto ordersDetails = ordersRepository.findOrdersDetailsByUserIdAndOrderId(userId, orderId);

        List<Long> itemDetailIds = ordersDetails.getOrderItemsDto().stream()
                .map(OrderItemDetailResponseDto::getItemDetailId)
                .toList();

        List<Long> imageList = new ArrayList<>();
        
        ordersDetails.getOrderItemsDto().forEach(orderItemDetail -> {
            Long itemDetailId = orderItemDetail.getItemDetailId();
            ItemDetailResponseDto itemDetailApi = moduleApiUtil.getItemDetailApi(itemDetailId);

            if (itemDetailApi != null) {
                orderItemDetail.setItemDetailName(itemDetailApi.getName());
                Long imageId = itemDetailApi.getImageId();
                imageList.add(imageId);

                List<ImageDataDto> imageApi = moduleApiUtil.getImageApi(imageList);

                if (!imageApi.isEmpty()) {
                    orderItemDetail.setItemImageUrl(imageApi.get(0).getUrl());
                }

                imageList.remove(imageId);
            }
        });

        return ordersDetails;
    }

    @Transactional
    @Override
    public Long cancelOrder(Long userId, Long orderId) {

        Long cancelOrder = ordersRepository.cancelOrderById(userId, orderId);

        if (cancelOrder != null) {
            eventPublisher.publishEvent(new StockRestoreEvent(orderId));
        } else {
            throw new CustomException(ErrorResponseCode.NOT_FOUND);
        }

        Orders orders = ordersRepository.findById(orderId).orElseThrow(() -> new CustomException(ErrorResponseCode.NOT_FOUND));
        orders.setStatus(OrderStatus.CANCELED);

        return orderId;
    }

    public String createMerchantUid(Long orderId) {
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String counterNumber = String.format("%03d", orderId);
        return "FM-" + date + "-" + counterNumber;
    }
}