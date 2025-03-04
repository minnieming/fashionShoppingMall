package com.fashionmall.review.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ReviewStatus {
    ACTIVATED("활성화"), INACTIVATED("비활성화");

    private final String status;
}
