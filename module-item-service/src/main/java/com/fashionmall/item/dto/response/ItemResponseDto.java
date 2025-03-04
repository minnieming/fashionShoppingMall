package com.fashionmall.item.dto.response;

import com.fashionmall.item.entity.Item;
import com.fashionmall.item.entity.ItemDetail;
import com.fashionmall.item.enums.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Comparator;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemResponseDto {

    private Long id;
    private Long imageId;
    private String imageUrl;
    private String name;
    private StatusEnum status;
    private List<ItemDetailResponseDto> itemDetailResponseDtoList;

    public static ItemResponseDto from(Item item, String imageUrl) {

        List<ItemDetailResponseDto> itemDetailResponseDtoList = item.getItemDetails().stream()
                .map(ItemDetailResponseDto::from)
                .toList();

        return ItemResponseDto.builder()
                .id(item.getId())
                .imageId(item.getImageId())
                .imageUrl(imageUrl)
                .name(item.getName())
                .status(item.getStatus())
                .itemDetailResponseDtoList(itemDetailResponseDtoList)
                .build();
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemDetailResponseDto {

        private Long id;
        private String name;
        private Long imageId;
        private String subImageUrl;
        private int price;
        private int quantity;
        private StatusEnum status;


        public static ItemDetailResponseDto from(ItemDetail itemDetail) {
            return ItemDetailResponseDto.builder()
                    .id(itemDetail.getId())
                    .name(itemDetail.getName())
                    .imageId(itemDetail.getImageId())
                    .subImageUrl(itemDetail.getImageUrl())
                    .price(itemDetail.getPrice())
                    .quantity(itemDetail.getQuantity())
                    .status(itemDetail.getStatus())
                    .build();
        }
    }
}
