package com.fashionmall.item.dto.request;

import com.fashionmall.common.moduleApi.enums.ItemDiscountTypeEnum;
import com.fashionmall.item.enums.StatusEnum;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ItemUpdateRequestDto {

    @NotBlank (message = "상품명을 입력해주세요")
    private String name; // 상품명
    @NotNull (message = "상품 활성화 여부를 입력해주세요(enum)")
    private StatusEnum state; // 상품 재고 현황

    private String imageFileName;

    @NotNull (message = "상품의 카테고리를 입력해주세요")
    private List<ItemUpdateRequestDto.CategoryRequestDto> categoryRequestDtoList;

    @NotNull (message = "상품의 상세 정보들을 입력해주세요")
    private List <ItemUpdateRequestDto.ItemDetailRequestDto> itemDetailRequestDtoList;

    @NotNull (message = "상품의 할인 정보들을 입력해주세요")
    private List <ItemUpdateRequestDto.ItemDiscountRequestDto> itemDiscountRequestDtoList;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CategoryRequestDto {

        @NotNull (message = "카테고리 매핑 아이디를 입력해주세요")
        private Long id;
        @NotNull @Min(value = 1, message = "상위 카테고리 ID는 1 이상의 값이어야 합니다.")
        private Long category1Id; // 상위 카테고리
        @NotNull @Min(value = 1, message = "하위 카테고리 ID는 1 이상의 값이어야 합니다.")
        private Long category2Id; // 하위 카테고리

    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemDetailRequestDto {

        @NotNull (message = "상품 상세 아이디를 입력해주세요")
        private Long id;
        @NotBlank (message = "상품 상세명을 입력해주세요")
        private String name; // 상품 상세명
        @NotNull (message = "상품 전시 상태 여부를 입력해주세요(enum)")
        private StatusEnum status; // 상품 전시 상태

        private String imageFileName;

        @NotNull @Positive (message = "상품 가격을 입력해주세요. 상품 가격은 0보다 커야합니다")
        private int price;
        @NotNull @Min (value = 0, message = "재고 수량을 입력해주세요")
        private int quantity; // 재고 현황

        @NotNull @Min(value = 1, message = "사이즈 ID는 1 이상의 값이어야 합니다.")
        private Long sizeId;
        @NotNull @Min(value = 1, message = "색상 ID는 1 이상의 값이어야 합니다.")
        private Long colorId;

    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemDiscountRequestDto {


        @NotNull (message = "상품 할인 아이디를 입력해주세요")
        private Long id;

        @NotNull
        private ItemDiscountTypeEnum type; // 할인 타입 : 정률, 정액

        @Positive (message = "할인값을 입력해주세요. 할인값은 0보다 커야합니다")
        private int value; // 할인값 : 정률, 정액

        @NotNull
        private StatusEnum status; // 할인 활성화 상태

    }
}
