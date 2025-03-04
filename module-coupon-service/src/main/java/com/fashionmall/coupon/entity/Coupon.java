package com.fashionmall.coupon.entity;

import com.fashionmall.common.entity.BaseEntity;
import com.fashionmall.coupon.enums.CouponStatus;
import com.fashionmall.coupon.enums.CouponType;
import com.fashionmall.coupon.enums.DiscountType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "coupon")
public class Coupon extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name; //쿠폰명

    @Enumerated(EnumType.STRING)
    @Column(name = "coupon_type", nullable = false)
    private CouponType couponType; //쿠폰 발행 타입, DOWNLOAD

    @Enumerated(EnumType.STRING)
    @Column(name = "discount_type", nullable = false)
    private DiscountType discountType; //쿠폰 할인 타입(RATE: 정률, AMOUNT: 정액)

    @Column(name = "discount_value", nullable = false)
    private int discountValue; //할인 값

    @Column(name = "min_purchase_price", nullable = false)
    private int minPurchasePrice; //최소 주문 금액

    @Column(name = "max_discount_price")
    private Integer maxDiscountPrice; //최대 할인 금액(정률 할인),

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate; //할인 적용 시작 기간

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate; //할인 적용 종료 기간

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private CouponStatus status; //쿠폰 활성 상태(ACTIVATED: 활성화, INACTIVATED: 비활성화)

    @Builder
    public Coupon(String name,
                  CouponType couponType,
                  DiscountType discountType,
                  int discountValue,
                  int minPurchasePrice,
                  Integer maxDiscountPrice,
                  LocalDateTime startDate,
                  LocalDateTime endDate,
                  CouponStatus status) {
        this.name = name;
        this.couponType = couponType;
        this.discountType = discountType;
        this.discountValue = discountValue;
        this.minPurchasePrice = minPurchasePrice;
        this.maxDiscountPrice = maxDiscountPrice;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updateDiscountType(DiscountType discountType) {
        this.discountType = discountType;
    }

    public void updateDiscountValue(int discountValue) {
        this.discountValue = discountValue;
    }

    public void updateMinPurchasePrice(int minPurchasePrice) {
        this.minPurchasePrice = minPurchasePrice;
    }

    public void updateMaxDiscountPrice(Integer maxDiscountPrice) {
        this.maxDiscountPrice = maxDiscountPrice;
    }

    public void updateStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void updateEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public void updateStatus(CouponStatus status) {
        this.status = status;
    }

    public void setMaxDiscountPriceNull() {
        this.maxDiscountPrice = null;
    }
}
