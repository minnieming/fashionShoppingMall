package com.fashionmall.review.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponseMyPageDto {

    private Long id;
    private String itemName;
    private Long itemId;
    private String content;
    private LocalDateTime createdAt;

}
