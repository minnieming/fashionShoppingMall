package com.fashionmall.coupon.dto.response;

import com.fashionmall.coupon.entity.Coupon;
import com.fashionmall.coupon.enums.DiscountType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AdminCouponResponseDto {

    private Long id;
    private String name;
    private DiscountType discountType;
    private int discountValue;
    private int minPurchasePrice;
    private Integer maxDiscountPrice;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public static AdminCouponResponseDto from(Coupon coupon) {
        return new AdminCouponResponseDto(
                coupon.getId(),
                coupon.getName(),
                coupon.getDiscountType(),
                coupon.getDiscountValue(),
                coupon.getMinPurchasePrice(),
                coupon.getMaxDiscountPrice(),
                coupon.getStartDate(),
                coupon.getEndDate(),
                coupon.getCreatedAt(),
                coupon.getUpdatedAt()
        );
    }

}
