package com.fashionmall.common.jwt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum UserRoleEnum {

    USER ("ROLE_USER"),
    ADMIN ("ROLE_ADMIN");

    private final String authority;

}