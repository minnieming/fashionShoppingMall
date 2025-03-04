package com.fashionmall.review.service;

import com.fashionmall.common.moduleApi.util.ModuleApiUtil;
import com.fashionmall.common.response.PageInfoResponseDto;
import com.fashionmall.review.dto.request.ReviewRequestDto;
import com.fashionmall.review.dto.response.ReviewResponseItemDto;
import com.fashionmall.review.dto.response.ReviewResponseMyPageDto;
import com.fashionmall.review.entity.Review;
import com.fashionmall.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ModuleApiUtil moduleApiUtil;

    @Override
    public Long createReview(ReviewRequestDto reviewRequestDto) {

        Long itemId = reviewRequestDto.getItemId();

        String itemName = moduleApiUtil.getItemNameApi(itemId);

        Review review = reviewRequestDto.toEntity(itemName);

        return reviewRepository.save(review).getId();
    }

    @Override
    public PageInfoResponseDto<ReviewResponseMyPageDto> getMyReviews(Long userId, int pageNo, int size) {
        PageRequest pageRequest = PageRequest.of(pageNo - 1, size);
        return reviewRepository.findReviewByUserId(userId, pageRequest);
    }

    @Override
    public PageInfoResponseDto<ReviewResponseItemDto> getItemReviews(Long itemId, int pageNo, int size) {
        PageRequest pageRequest = PageRequest.of(pageNo - 1, size);
        return reviewRepository.findItemReviewByItemId(itemId, pageRequest);
    }

    @Override
    public void deleteReview(Long userId, Long reviewId) {
        reviewRepository.deleteReviewByUserIdAndId(userId, reviewId);
    }


}
