package com.fashionmall.coupon.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum DiscountType {
    RATE("정률"), AMOUNT("정액");

    private final String discountType;
}
