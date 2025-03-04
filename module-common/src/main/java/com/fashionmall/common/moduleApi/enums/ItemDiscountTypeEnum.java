package com.fashionmall.common.moduleApi.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ItemDiscountTypeEnum {

    RATE ("정률"), AMOUNT ("정액");

    private final String type;
}
