package com.fashionmall.coupon.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CouponStatus {
    ACTIVATED("활성화"), INACTIVATED("비활성화");

    private final String status;
}
