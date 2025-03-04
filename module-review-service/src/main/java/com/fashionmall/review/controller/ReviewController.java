package com.fashionmall.review.controller;

import com.fashionmall.common.response.CommonResponse;
import com.fashionmall.common.response.PageInfoResponseDto;
import com.fashionmall.common.util.ApiResponseUtil;
import com.fashionmall.common.util.ProfileUtil;
import com.fashionmall.review.dto.request.ReviewRequestDto;
import com.fashionmall.review.dto.response.ReviewResponseItemDto;
import com.fashionmall.review.dto.response.ReviewResponseMyPageDto;
import com.fashionmall.review.security.UserDetailsImpl;
import com.fashionmall.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final ProfileUtil profileUtil;

    @GetMapping("/review")
    public CommonResponse<PageInfoResponseDto<ReviewResponseMyPageDto>> getMyReview(@RequestParam(defaultValue = "1") int pageNo,
                                                                                    @RequestParam(defaultValue = "10") int size,
                                                                                    @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ApiResponseUtil.success(reviewService.getMyReviews(userDetails.getUserid(), pageNo, size));
    }

    @GetMapping("/review/item/{itemId}")
    public CommonResponse<PageInfoResponseDto<ReviewResponseItemDto>> getItemReview(@PathVariable("itemId") Long itemId,
                                                                                    @RequestParam(defaultValue = "1") int pageNo,
                                                                                    @RequestParam(defaultValue = "10") int size) {
        return ApiResponseUtil.success(reviewService.getItemReviews(itemId, pageNo, size));
    }

    @PostMapping("/review/item/{itemId}")
    public CommonResponse<Long> postReview(@PathVariable("itemId") Long itemId,
                                           @RequestBody ReviewRequestDto reviewRequestDto,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        reviewRequestDto.setUserId(userDetails.getUserid());
        reviewRequestDto.setNickName(userDetails.getUsername());
        reviewRequestDto.setItemId(itemId);
        return ApiResponseUtil.success(reviewService.createReview(reviewRequestDto));
    }

    @DeleteMapping("/review/{reviewId}")
    public void deleteReview(@PathVariable("reviewId") Long reviewId,
                             @AuthenticationPrincipal UserDetailsImpl userDetails) {
        reviewService.deleteReview(userDetails.getUserid(), reviewId);
    }

    @GetMapping("/review/profile")
    public String getCartProfile() {
        return profileUtil.getCurrentProfile();
    }
}
