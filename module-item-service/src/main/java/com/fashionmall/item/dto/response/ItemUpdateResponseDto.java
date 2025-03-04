package com.fashionmall.item.dto.response;

import com.fashionmall.item.entity.*;
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
public class ItemUpdateResponseDto {

    private Long id;
    private Long workerId;
    private Long imageId;
    private String imageUrl;
    private String name;
    private StatusEnum status;
    private List<ItemCategoryMappingResponseDto> itemCategoryMappingResponseDtoLists;
    private List<ItemDetailResponseDto> itemDetailResponseDtoLists;
    private List<ItemDiscountResponseDto> itemDiscountResponseDtoLists;



    public static ItemUpdateResponseDto from(Item item,
                                             List<ItemCategoryMapping> updatedCategoryMappings,
                                             List<ItemDetail> updatedItemDetails,
                                             List<ItemDiscount> updatedItemDiscounts) {

        List<ItemUpdateResponseDto.ItemCategoryMappingResponseDto> itemCategoryMappingResponseDtoList = updatedCategoryMappings.stream()
                .map(ItemCategoryMappingResponseDto::from)
                .toList();

        List<ItemUpdateResponseDto.ItemDetailResponseDto> itemDetailResponseDtoList = updatedItemDetails.stream()
                .map(ItemUpdateResponseDto.ItemDetailResponseDto::from)
                .toList();

        List<ItemUpdateResponseDto.ItemDiscountResponseDto> itemDiscountResponseDtoList = updatedItemDiscounts.stream()
                .map(ItemUpdateResponseDto.ItemDiscountResponseDto::from)
                .toList();

        return ItemUpdateResponseDto.builder()
                .id(item.getId())
                .workerId(item.getWorkerId())
                .imageId(item.getImageId())
                .imageUrl(item.getImageUrl())
                .name(item.getName())
                .status(item.getStatus())
                .itemCategoryMappingResponseDtoLists(itemCategoryMappingResponseDtoList)
                .itemDetailResponseDtoLists(itemDetailResponseDtoList)
                .itemDiscountResponseDtoLists(itemDiscountResponseDtoList)
                .build();
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemCategoryMappingResponseDto {

        private Long id;
        private Long category1Id;
        private Long category2Id;

        public static ItemUpdateResponseDto.ItemCategoryMappingResponseDto from (ItemCategoryMapping itemCategoryMapping) {
            return ItemCategoryMappingResponseDto.builder()
                    .id(itemCategoryMapping.getId())
                    .category1Id(itemCategoryMapping.getCategory1().getId())
                    .category2Id(itemCategoryMapping.getCategory2Id())
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemDetailResponseDto {

        private Long id;
        private String name;
        private Long imageId;
        private String imageUrl;
        private int price;
        private int quantity;
        private StatusEnum status;

        public static ItemUpdateResponseDto.ItemDetailResponseDto from(ItemDetail itemDetail) {
            return ItemDetailResponseDto.builder()
                    .id(itemDetail.getId())
                    .name(itemDetail.getName())
                    .imageId(itemDetail.getImageId())
                    .imageUrl(itemDetail.getImageUrl())
                    .price(itemDetail.getPrice())
                    .quantity(itemDetail.getQuantity())
                    .status(itemDetail.getStatus())
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemDiscountResponseDto {

        private Long id;
        private ItemDiscountTypeEnum type;
        private int value;
        private StatusEnum status;

        public static ItemUpdateResponseDto.ItemDiscountResponseDto from (ItemDiscount itemDiscount) {
            return ItemDiscountResponseDto.builder()
                    .id(itemDiscount.getId())
                    .type(itemDiscount.getType())
                    .value(itemDiscount.getValue())
                    .status(itemDiscount.getStatus())
                    .build();
        }
    }

}
