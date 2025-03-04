package com.fashionmall.order.dto.request;

import com.fashionmall.order.entity.BillingKey;
import com.fashionmall.order.entity.OrderItem;
import com.fashionmall.order.entity.Orders;
import com.fashionmall.order.entity.Payment;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderPaymentRequestDto {

    private Long userId;
    private Long couponId;
    // 직접 입력시 Null
    private Long deliveryAddressId;
    private Long billingKeyId;

    private String zipcode;
    private String roadAddress;

    private int totalPrice;
    private int couponDiscountPrice;
    private int totalItemDiscountPrice;
    @JsonProperty("amount")
    private int paymentPrice;

    private List<OrderItemRequestDto> orderItemsDto;

    @JsonProperty("customer_uid")
    private String customerUid;
    @JsonProperty("merchant_uid")
    private String merchantUid;

    private String pg;
    @JsonProperty("cart_quota")
    private int cardQuota;

    public Orders toOrders() {
        List<OrderItem> orderItems = orderItemsDto.stream()
                .map(orderitems -> new OrderItem(
                        orderitems.getItemDetailId(),
                        orderitems.getOriginalPrice(),
                        orderitems.getQuantity(),
                        orderitems.getItemDiscountPrice()

                ))
                .toList();

        return Orders
                .builder()
                .userId(userId)
                .couponId(couponId)
                .zipcode(zipcode)
                .roadAddress(roadAddress)
                .totalPrice(totalPrice)
                .couponDiscountPrice(couponDiscountPrice)
                .totalItemDiscountPrice(totalItemDiscountPrice)
                .paymentPrice(paymentPrice)
                .orderItems(orderItems)
                .build();
    }

    public Payment toPayment(Orders orders, BillingKey billingKey, String impUid, LocalDateTime paidAt) {
        return Payment
                .builder()
                .userId(userId)
                .orders(orders)
                .billingKey(billingKey)
                .impUid(impUid)
                .paidAt(paidAt)
                .merchantUid(merchantUid)
                .price(paymentPrice)
                .cardQuota(cardQuota)
                .build();
    }
}