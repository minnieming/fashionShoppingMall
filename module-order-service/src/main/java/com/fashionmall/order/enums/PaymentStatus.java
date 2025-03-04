package com.fashionmall.order.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum PaymentStatus {
    PAID("결제성공"), CANCELED("결제취소");

    private final String status;
}
