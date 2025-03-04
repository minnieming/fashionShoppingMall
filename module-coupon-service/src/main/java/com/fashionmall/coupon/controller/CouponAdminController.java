package com.fashionmall.coupon.controller;

import com.fashionmall.common.response.CommonResponse;
import com.fashionmall.common.response.PageInfoResponseDto;
import com.fashionmall.common.util.ApiResponseUtil;
import com.fashionmall.coupon.dto.request.CouponRequestDto;
import com.fashionmall.coupon.dto.request.CouponUpdateRequestDto;
import com.fashionmall.coupon.dto.response.AdminCouponResponseDto;
import com.fashionmall.coupon.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CouponAdminController {

    private final CouponService couponService;

    //(관리자)쿠폰조회
    @GetMapping("/admin/coupon")
    public CommonResponse<PageInfoResponseDto<AdminCouponResponseDto>> getCouponList(@RequestParam(defaultValue = "1") int pageNo,
                                                                                     @RequestParam(defaultValue = "10") int size) {
        //관리자 인증 관련 추가
        return ApiResponseUtil.success(couponService.getCoupons(pageNo, size));
    }

    //(관리자)쿠폰등록
    @PostMapping("/admin/coupon")
    public CommonResponse<Long> publishCoupon(@RequestBody CouponRequestDto couponRequestDto) {
        //관리자 인증 관련 추가
        return ApiResponseUtil.success(couponService.publishCoupon(couponRequestDto));
    }

    //(관리자)쿠폰수정
    @PutMapping("/admin/coupon/{couponId}")
    public CommonResponse<Long> updateCoupon(@PathVariable("couponId") Long couponId,
                                             @RequestBody CouponUpdateRequestDto couponUpdateRequestDto) {
        //관리자 인증 관련 추가
        couponUpdateRequestDto.setId(couponId);
        return ApiResponseUtil.success(couponService.updateCoupon(couponUpdateRequestDto));
    }

    //(관리자)쿠폰삭제
    @DeleteMapping("/admin/coupon/{couponId}")
    public CommonResponse<Long> deleteCoupon(@PathVariable("couponId") Long couponId) {
        //관리자 인증 관련 추가
        return ApiResponseUtil.success(couponService.deleteCoupon(couponId));
    }
}
