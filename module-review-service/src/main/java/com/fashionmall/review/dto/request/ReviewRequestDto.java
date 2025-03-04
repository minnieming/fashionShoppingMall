package com.fashionmall.review.dto.request;

import com.fashionmall.review.entity.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReviewRequestDto {

    private Long userId;
    private String nickName;
    private Long itemId;
    private String content;

    public Review toEntity(String itemName) {
        return Review
                .builder()
                .userId(userId)
                .nickName(nickName)
                .itemId(itemId)
                .itemName(itemName)
                .content(content)
                .build();
    }
}
