package com.fashionmall.cart.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartCalculateRequestDto {

    private List<CartItem> items;

    @Getter
    @NoArgsConstructor
    public static class CartItem {

        @NotNull (message = "장바구니에서 선택한 상품의 ID를 적어주세요")
        private Long id;

    }
}
