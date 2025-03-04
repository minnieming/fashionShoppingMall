package com.fashionmall.common.moduleApi.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReferenceTypeEnum {

    ITEM ("상품"), REVIEW ("리뷰");

    private final String referenceType;
}
