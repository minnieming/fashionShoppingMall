package com.fashionmall.cart.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartUpdateRequestDto {

    @NotNull @Positive (message = "수정할 상품의 개수를 입력해주세요")
    private int quantity;

    @JsonProperty("is_selected")
    private Boolean isSelected;

}
