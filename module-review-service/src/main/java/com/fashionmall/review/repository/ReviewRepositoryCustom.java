package com.fashionmall.review.repository;

import com.fashionmall.common.response.PageInfoResponseDto;
import com.fashionmall.review.dto.response.ReviewResponseItemDto;
import com.fashionmall.review.dto.response.ReviewResponseMyPageDto;
import org.springframework.data.domain.Pageable;

public interface ReviewRepositoryCustom {

    PageInfoResponseDto<ReviewResponseMyPageDto> findReviewByUserId(Long userId, Pageable pageable);

    PageInfoResponseDto<ReviewResponseItemDto> findItemReviewByItemId(Long itemId, Pageable pageable);

    void deleteReviewByUserIdAndId(Long userId, Long id);
}
