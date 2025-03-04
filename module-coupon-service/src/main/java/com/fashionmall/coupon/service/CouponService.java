package com.fashionmall.coupon.service;

import com.fashionmall.common.moduleApi.dto.CouponDto;
import com.fashionmall.common.response.PageInfoResponseDto;
import com.fashionmall.coupon.dto.request.CouponRequestDto;
import com.fashionmall.coupon.dto.request.CouponUpdateRequestDto;
import com.fashionmall.coupon.dto.response.AdminCouponResponseDto;
import com.fashionmall.coupon.dto.response.UserCouponResponseDto;

import java.util.List;

public interface CouponService {

    PageInfoResponseDto<AdminCouponResponseDto> getCoupons(int pageNo, int size);

    Long publishCoupon(CouponRequestDto couponRequestDto);

    Long updateCoupon(CouponUpdateRequestDto couponUpdateRequestDto);

    Long deleteCoupon(Long couponId);

    PageInfoResponseDto<UserCouponResponseDto> getUserCoupons(Long userId, int pageNo, int size);

    List<UserCouponResponseDto> getDownloadableCoupons(Long userId);

    Long downloadCoupon(Long userId, Long couponId);

    List<CouponDto> getUserCouponsToApi(Long userId);
}
