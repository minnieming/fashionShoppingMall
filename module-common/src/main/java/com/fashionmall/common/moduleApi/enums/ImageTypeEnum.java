package com.fashionmall.common.moduleApi.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.security.Principal;

@Getter
@RequiredArgsConstructor
public enum ImageTypeEnum {

    MAIN ("메인이미지"), DESCRIPTION ("서브이미지");

    private final String imageType;
}
