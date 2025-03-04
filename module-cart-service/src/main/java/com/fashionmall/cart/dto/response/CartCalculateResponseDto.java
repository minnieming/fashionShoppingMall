package com.fashionmall.cart.dto.response;

import com.fashionmall.cart.dto.request.CartCalculateRequestDto;
import com.fashionmall.cart.entity.Cart;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.text.NumberFormat;
import java.util.Locale;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartCalculateResponseDto {

    private Long id;
    private int totalPrice;

    public static CartCalculateResponseDto of (Long cartId, int totalPrice) {
       return CartCalculateResponseDto.builder()
               .id(cartId)
               .totalPrice(totalPrice)
               .build();
    }
}
