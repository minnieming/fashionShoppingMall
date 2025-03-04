package com.fashionmall.review.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponseItemDto {

    private Long id;
    private Long userId;
    private String nickName;
    private String content;
    private LocalDateTime createdAt;

}
