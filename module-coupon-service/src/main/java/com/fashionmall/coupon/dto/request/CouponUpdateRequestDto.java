package com.fashionmall.coupon.dto.request;

import com.fashionmall.coupon.enums.CouponStatus;
import com.fashionmall.coupon.enums.DiscountType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CouponUpdateRequestDto {

    private Long id;
    private String name;
    private DiscountType discountType;
    private int discountValue;
    private int minPurchasePrice;
    private Integer maxDiscountPrice;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private CouponStatus status;

}
