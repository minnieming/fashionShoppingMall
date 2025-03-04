package com.fashionmall.order.controller;

import com.fashionmall.common.response.CommonResponse;
import com.fashionmall.common.response.PageInfoResponseDto;
import com.fashionmall.common.util.ApiResponseUtil;
import com.fashionmall.common.util.ProfileUtil;
import com.fashionmall.order.dto.request.OrderPaymentRequestDto;
import com.fashionmall.order.dto.request.PaymentCancelRequestDto;
import com.fashionmall.order.dto.response.OrdersDetailResponseDto;
import com.fashionmall.order.dto.response.OrdersResponseDto;
import com.fashionmall.order.security.UserDetailsImpl;
import com.fashionmall.order.service.OrdersService;
import com.fashionmall.order.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrdersService ordersService;
    private final PaymentService paymentService;
    private final ProfileUtil profileUtil;

    @PostMapping("/order")
    public CommonResponse<Long> createOrderAndPayment(@RequestBody OrderPaymentRequestDto orderPaymentRequestDto,
                                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {
        orderPaymentRequestDto.setUserId(userDetails.getUserid());
        return ApiResponseUtil.success(ordersService.createAndPaymentOrder(orderPaymentRequestDto));
    }

    @GetMapping("/order")
    public CommonResponse<PageInfoResponseDto<OrdersResponseDto>> getUserOrderList(@RequestParam(defaultValue = "1") int pageNo,
                                                                                   @RequestParam(defaultValue = "10") int size,
                                                                                   @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ApiResponseUtil.success(ordersService.getOrders(userDetails.getUserid(), pageNo, size));
    }

    @GetMapping("/order/{ordersId}")
    public CommonResponse<OrdersDetailResponseDto> getOrderDetail(@PathVariable Long ordersId,
                                                                  @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ApiResponseUtil.success(ordersService.getOrderDetail(userDetails.getUserid(), ordersId));
    }

    @PatchMapping("/order/{orderId}/cancel")
    public CommonResponse<Long> cancelOrder(@PathVariable Long orderId,
                                            @RequestBody PaymentCancelRequestDto paymentCancelRequestDto,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long userId = userDetails.getUserid();

        paymentCancelRequestDto.setUserId(userId);
        paymentCancelRequestDto.setOrderId(orderId);
        paymentService.cancelPayment(paymentCancelRequestDto);
        return ApiResponseUtil.success(ordersService.cancelOrder(userId, orderId));
    }

    @GetMapping("/order/profile")
    public String getCartProfile() {
        return profileUtil.getCurrentProfile();
    }
}