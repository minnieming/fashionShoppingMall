package com.fashionmall.item.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StatusEnum {

    ACTIVATED ("활성화"), INACTIVATED ("비활성화");

    private final String status;

}
