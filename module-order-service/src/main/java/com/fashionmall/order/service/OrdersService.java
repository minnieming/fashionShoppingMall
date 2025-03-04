package com.fashionmall.order.service;

import com.fashionmall.common.response.PageInfoResponseDto;
import com.fashionmall.order.dto.request.OrderPaymentRequestDto;
import com.fashionmall.order.dto.response.OrdersDetailResponseDto;
import com.fashionmall.order.dto.response.OrdersResponseDto;

public interface OrdersService {

    Long createAndPaymentOrder(OrderPaymentRequestDto orderPaymentRequestDto);

    PageInfoResponseDto<OrdersResponseDto> getOrders(Long userId, int pageNo, int size);

    OrdersDetailResponseDto getOrderDetail(Long userId, Long orderId);

    Long cancelOrder(Long userId, Long orderId);
}