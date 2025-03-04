package com.fashionmall.coupon.repository;

import com.fashionmall.common.moduleApi.dto.CouponDto;
import com.fashionmall.common.response.PageInfoResponseDto;
import com.fashionmall.coupon.dto.response.AdminCouponResponseDto;
import com.fashionmall.coupon.dto.response.UserCouponResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CouponRepositoryCustom {

    PageInfoResponseDto<AdminCouponResponseDto> couponListPaged(Pageable pageable);

    PageInfoResponseDto<UserCouponResponseDto> findUserCouponByUserIdToApi(Long userId, Pageable pageable);

    List<UserCouponResponseDto> findDownloadableCoupon(Long userId);

    List<CouponDto> findUserCouponByUserIdToApi(Long userId);

}
