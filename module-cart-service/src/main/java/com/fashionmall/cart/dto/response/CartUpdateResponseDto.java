package com.fashionmall.cart.dto.response;

import com.fashionmall.cart.entity.Cart;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartUpdateResponseDto {

    private Long id;
    private int quantity;
    private int price;
    private boolean isSelected;

    public static CartUpdateResponseDto from (Cart cart) {
        return CartUpdateResponseDto.builder()
                .id(cart.getId())
                .quantity(cart.getQuantity())
                .price(cart.getPrice())
                .isSelected(cart.isSelected())
                .build();
    }
}
