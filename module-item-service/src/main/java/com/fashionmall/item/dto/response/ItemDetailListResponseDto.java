package com.fashionmall.item.dto.response;

import com.fashionmall.common.moduleApi.enums.ItemDiscountTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDetailListResponseDto {

    private ItemInfo itemInfo;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemInfo {

        private long id;
        private String name;
        private String imageUrl;

        private List<ItemDetailInfo> itemDetailInfo;
        private List<ItemDiscountInfo> itemDiscountInfo;

    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemDetailInfo {

        private long id;
        private String color;
        private String size;
        private String name;
        private int price;
        private int discountPrice;
        private int quantity;
        private long imageId;
        private String imageUrl;

    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemDiscountInfo {

        private ItemDiscountTypeEnum type;
        private int value;

    }

}
