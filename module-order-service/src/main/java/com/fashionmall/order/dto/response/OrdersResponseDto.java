package com.fashionmall.order.dto.response;

import com.fashionmall.order.enums.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class OrdersResponseDto {

    private Long ordersId;
    private OrderStatus orderStatus;
    private int paymentPrice;
    private LocalDateTime createAt;


    public OrdersResponseDto(Long ordersId, OrderStatus orderStatus, int paymentPrice, LocalDateTime createAt) {
        this.ordersId = ordersId;
        this.orderStatus = orderStatus;
        this.paymentPrice = paymentPrice;
        this.createAt = createAt;
    }
}