package com.fashionmall.common.moduleApi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ItemPriceNameDto {

    private long itemDetailId;
    private int price;
    private String name;
}
