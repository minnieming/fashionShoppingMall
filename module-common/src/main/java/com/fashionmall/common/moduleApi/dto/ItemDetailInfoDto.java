package com.fashionmall.common.moduleApi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ItemDetailInfoDto {

    private Long id;
    private String itemDetailName;
    private int price;
    private int itemDiscountValue;
    private String discountType;
    private String imageUrl;

}
