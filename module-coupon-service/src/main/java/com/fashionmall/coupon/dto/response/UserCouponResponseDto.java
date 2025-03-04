package com.fashionmall.coupon.dto.response;

import com.fashionmall.coupon.entity.Coupon;
import com.fashionmall.coupon.enums.DiscountType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserCouponResponseDto {

    private Long couponId;
    private String name;
    private DiscountType discountType;
    private int discountValue;
    private int minPurchasePrice;
    private Integer maxDiscountPrice;
    private LocalDateTime startDate;
    private LocalDateTime endDate;


    public static UserCouponResponseDto from(Coupon coupon) {
        return new UserCouponResponseDto(
                coupon.getId(),
                coupon.getName(),
                coupon.getDiscountType(),
                coupon.getDiscountValue(),
                coupon.getMinPurchasePrice(),
                coupon.getMaxDiscountPrice(),
                coupon.getStartDate(),
                coupon.getEndDate()
        );
    }

}
