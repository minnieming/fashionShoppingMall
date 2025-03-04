package com.fashionmall.item.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemListResponseDto {

    private ItemInfo itemInfo;
    private ItemDetailInfo itemDetailInfo;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemInfo {

        private long id;
        private String name;
        private long imageId;
        private String imageUrl;

    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemDetailInfo {

        private int price;
        private int discountPrice;

    }
}
