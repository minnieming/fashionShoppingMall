package com.fashionmall.coupon.controller;

import com.fashionmall.common.moduleApi.dto.CouponDto;
import com.fashionmall.common.response.CommonResponse;
import com.fashionmall.common.util.ApiResponseUtil;
import com.fashionmall.coupon.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/coupon")
public class ApiCouponController {

    private final CouponService couponService;

    @GetMapping("/getCoupon/{userId}")
    public CommonResponse<List<CouponDto>> getCoupon(@PathVariable Long userId) {
        return ApiResponseUtil.success(couponService.getUserCouponsToApi(userId));
    }

    @GetMapping("/test")
    public CommonResponse<String> test() {
        return ApiResponseUtil.success("test");
    }
}
