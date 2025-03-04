package com.fashionmall.review.service;

import com.fashionmall.common.response.PageInfoResponseDto;
import com.fashionmall.review.dto.request.ReviewRequestDto;
import com.fashionmall.review.dto.response.ReviewResponseItemDto;
import com.fashionmall.review.dto.response.ReviewResponseMyPageDto;

public interface ReviewService {

    Long createReview(ReviewRequestDto reviewRequestDto);

    PageInfoResponseDto<ReviewResponseMyPageDto> getMyReviews(Long userId, int pageNo, int size);

    PageInfoResponseDto<ReviewResponseItemDto> getItemReviews(Long itemId, int pageNo, int size);

    void deleteReview(Long userId, Long reviewId);
}
