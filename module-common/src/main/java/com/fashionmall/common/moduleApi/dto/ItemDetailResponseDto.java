package com.fashionmall.common.moduleApi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDetailResponseDto {

    private Long imageId;
    private String name;
    private int price;

}
