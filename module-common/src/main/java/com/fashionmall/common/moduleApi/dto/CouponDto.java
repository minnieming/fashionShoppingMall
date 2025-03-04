package com.fashionmall.common.moduleApi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CouponDto {

    private Long id;
    private String name;
    private String discountType;
    private int discountValue;
    private int minPurchasePrice;
    private Integer maxDiscountPrice;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

}
