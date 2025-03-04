package com.fashionmall.item.dto.request;

import com.fashionmall.common.moduleApi.enums.ItemDiscountTypeEnum;
import com.fashionmall.item.enums.StatusEnum;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ItemDiscountRequestDto {

    @NotNull (message = "상품 아이디를 넣어주세요")
    private Long id;

    @NotNull (message = "상품 할인 정보를 입력해주세요")
    private List<ItemDiscountDtos> itemDiscountRequestDtoList;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemDiscountDtos {

        @NotNull(message = "상품 할인 타입을 입력해주세요 (RATE/AMOUNT)")
        private ItemDiscountTypeEnum type;

        @NotNull
        @Min(value = 0, message = "0보다 큰 수를 입력해주세요")
        private int value;

    }
}
