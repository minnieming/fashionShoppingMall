package com.fashionmall.item.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponseDto {

    private long category1Id;
    private String category1Name;
    private List<Category2Info> category2List;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Category2Info {

        private long category2Id;
        private String category2Name;

    }
}
