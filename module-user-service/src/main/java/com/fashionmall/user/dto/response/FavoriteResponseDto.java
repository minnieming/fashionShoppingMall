package com.fashionmall.user.dto.response;

import com.fashionmall.user.entity.Favorite;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteResponseDto {

    private Long userId;
    private Long favoriteId;
    private Long itemId;
    private boolean isSelected;

    public static FavoriteResponseDto from (Favorite favorite) {
        return FavoriteResponseDto.builder()
                .userId(favorite.getUserId())
                .favoriteId(favorite.getId())
                .itemId (favorite.getItemId())
                .isSelected(favorite.isSelected())
                .build();
    }
}
