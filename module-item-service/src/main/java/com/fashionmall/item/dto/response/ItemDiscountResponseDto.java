package com.fashionmall.item.dto.response;

import com.fashionmall.item.entity.Item;
import com.fashionmall.item.entity.ItemDiscount;
import com.fashionmall.common.moduleApi.enums.ItemDiscountTypeEnum;
import com.fashionmall.item.enums.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDiscountResponseDto {

    private Long id;
    private List<ItemDiscountDtos> itemDiscountDtoList;

//    public static ItemDiscountResponseDto from (Item item) {
//        List <ItemDiscountDtos> itemDiscountResponseDtoList = item.getItemDiscounts().stream()
//                .map(ItemDiscountDtos::from)
//                .toList();
//
//        return ItemDiscountResponseDto.builder()
//                .id(item.getId())
//                .itemDiscountDtoList(itemDiscountResponseDtoList)
//                .build();
//    }

    public static ItemDiscountResponseDto fromItemDiscounts (Long itemId, List<ItemDiscount> newlyCreatedDiscounts) {
        List<ItemDiscountDtos> itemDiscountDtoList = newlyCreatedDiscounts.stream()
                .map(ItemDiscountDtos::from)
                .toList();

        return ItemDiscountResponseDto.builder()
                .id(itemId)
                .itemDiscountDtoList(itemDiscountDtoList)
                .build();
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemDiscountDtos {

        private Long id;
        private ItemDiscountTypeEnum type;
        private int value;
        private StatusEnum status;

        public static ItemDiscountDtos from (ItemDiscount itemDiscount) {
            return ItemDiscountDtos.builder()
                    .id(itemDiscount.getId())
                    .type(itemDiscount.getType())
                    .value(itemDiscount.getValue())
                    .status(itemDiscount.getStatus())
                    .build();
        }
    }
}
