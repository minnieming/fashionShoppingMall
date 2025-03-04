package com.fashionmall.cart.dto.request;

import com.fashionmall.cart.entity.Cart;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartRequestDto {

    @NotNull
    private List<CartRequestDtoList> cartRequestDtoList;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CartRequestDtoList {

        @NotNull(message = "itemDetail 아이디를 입력해주세요")
        private Long itemDetailId;

        @NotNull
        @Positive(message = "해당 상품의 개수를 입력해주세요")
        private int quantity;

        @JsonProperty("is_selected")
        @NotNull(message = "장바구니 선택 여부를 입력해주세요")
        private boolean isSelected;

        public Cart toEntity(Long userId, Long imageId, int price, String itemDetailName) {
            return Cart.builder()
                    .userId(userId)
                    .itemDetailId(this.itemDetailId)
                    .imageId(imageId)
                    .itemDetailName(itemDetailName)
                    .quantity(this.quantity)
                    .price(price)
                    .isSelected(this.isSelected)
                    .build();
        }
    }

}
