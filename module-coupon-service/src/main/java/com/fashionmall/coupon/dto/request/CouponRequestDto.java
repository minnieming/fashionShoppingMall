package com.fashionmall.coupon.dto.request;

import com.fashionmall.coupon.entity.Coupon;
import com.fashionmall.coupon.enums.CouponStatus;
import com.fashionmall.coupon.enums.CouponType;
import com.fashionmall.coupon.enums.DiscountType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@ToString
public class CouponRequestDto {

    private Long id;
    private String name;
    private CouponType couponType;
    private DiscountType discountType;
    private int discountValue;
    private int minPurchasePrice;
    private Integer maxDiscountPrice;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private CouponStatus status;

    public Coupon toEntity() {
        return Coupon
                .builder()
                .name(name)
                .couponType(couponType)
                .discountType(discountType)
                .discountValue(discountValue)
                .minPurchasePrice(minPurchasePrice)
                .maxDiscountPrice(maxDiscountPrice)
                .startDate(startDate)
                .endDate(endDate)
                .status(status)
                .build();
    }
}
