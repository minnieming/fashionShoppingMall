package com.fashionmall.common.moduleApi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemDetailDto {

    private Long id;
    private String name;
    private int price;
    private int itemDiscountValue;
    private String discountType;
    private int quantity;
    private String imageUrl;

    public int getTotalPrice() {
        return price * quantity;
    }
}
