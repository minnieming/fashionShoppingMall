package com.fashionmall.order.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderItemDetailResponseDto {

    private Long itemDetailId;
    private int price;
    private int quantity;
    private int itemDiscountPrice;

    private int totalPrice;
    private int totalDiscountedPrice;

    private String itemDetailName;
    private String itemImageUrl;

    public OrderItemDetailResponseDto(Long itemDetailId, int price, int quantity, int itemDiscountPrice, int totalPrice, int totalDiscountedPrice) {
        this.itemDetailId = itemDetailId;
        this.price = price;
        this.quantity = quantity;
        this.itemDiscountPrice = itemDiscountPrice;
        this.totalPrice = totalPrice;
        this.totalDiscountedPrice = totalDiscountedPrice;
    }
}