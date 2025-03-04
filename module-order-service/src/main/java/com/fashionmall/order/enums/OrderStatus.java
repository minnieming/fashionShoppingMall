package com.fashionmall.order.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum OrderStatus {
    ORDERED("주문성공"), CANCELED("주문취소");

    private final String status;
}
