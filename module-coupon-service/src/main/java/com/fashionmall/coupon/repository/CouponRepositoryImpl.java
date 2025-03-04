package com.fashionmall.coupon.repository;

import com.fashionmall.common.moduleApi.dto.CouponDto;
import com.fashionmall.common.response.PageInfoResponseDto;
import com.fashionmall.coupon.dto.response.AdminCouponResponseDto;
import com.fashionmall.coupon.dto.response.UserCouponResponseDto;
import com.fashionmall.coupon.enums.CouponStatus;
import com.fashionmall.coupon.enums.CouponType;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.fashionmall.coupon.entity.QCoupon.coupon;
import static com.fashionmall.coupon.entity.QUserCoupon.userCoupon;


@Repository
@RequiredArgsConstructor
public class CouponRepositoryImpl implements CouponRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public PageInfoResponseDto<AdminCouponResponseDto> couponListPaged(Pageable pageable) {

        int offset = (int) pageable.getOffset();
        int size = pageable.getPageSize();

        List<AdminCouponResponseDto> content = queryFactory
                .select(Projections.constructor(AdminCouponResponseDto.class,
                        coupon.id,
                        coupon.name,
                        coupon.discountType,
                        coupon.discountValue,
                        coupon.minPurchasePrice,
                        coupon.maxDiscountPrice,
                        coupon.startDate,
                        coupon.endDate,
                        coupon.createdAt,
                        coupon.updatedAt))
                .from(coupon)
                .orderBy(coupon.id.desc())
                .offset(offset)
                .limit(size)
                .fetch();

        Long fetchOne = queryFactory
                .select(coupon.count())
                .from(coupon)
                .fetchOne();

        int totalCount = fetchOne.intValue();

        return PageInfoResponseDto.of(pageable, content, totalCount);
    }

    @Override
    public PageInfoResponseDto<UserCouponResponseDto> findUserCouponByUserIdToApi(Long userId, Pageable pageable) {

        int offset = (int) pageable.getOffset();
        int size = pageable.getPageSize();

        List<UserCouponResponseDto> content = queryFactory
                .select(Projections.constructor(UserCouponResponseDto.class,
                        coupon.id,
                        coupon.name,
                        coupon.discountType,
                        coupon.discountValue,
                        coupon.minPurchasePrice,
                        coupon.maxDiscountPrice,
                        coupon.startDate,
                        coupon.endDate))
                .from(userCoupon)
                .join(userCoupon.coupon, coupon)
                .where(userCoupon.userId.eq(userId),
                        userCoupon.isUsed.eq(false),
                        coupon.endDate.after(LocalDateTime.now()),
                        coupon.status.eq(CouponStatus.ACTIVATED))
                .orderBy(coupon.id.desc())
                .offset(offset)
                .limit(size)
                .fetch();

        Long fetchOne = queryFactory
                .select(coupon.count())
                .from(coupon)
                .join(userCoupon)
                .where(userCoupon.userId.eq(userId),
                        userCoupon.isUsed.eq(false),
                        coupon.endDate.after(LocalDateTime.now()),
                        coupon.status.eq(CouponStatus.ACTIVATED))
                .fetchOne();

        int totalCount = fetchOne.intValue();

        return PageInfoResponseDto.of(pageable, content, totalCount);
    }

    @Override
    public List<UserCouponResponseDto> findDownloadableCoupon(Long userId) {

        return queryFactory
                .select(Projections.constructor(UserCouponResponseDto.class,
                        coupon.id,
                        coupon.name,
                        coupon.discountType,
                        coupon.discountValue,
                        coupon.minPurchasePrice,
                        coupon.maxDiscountPrice,
                        coupon.startDate,
                        coupon.endDate))
                .from(coupon)
                .leftJoin(userCoupon)
                .on(userCoupon.coupon.eq(coupon)
                        .and(userCoupon.userId.eq(userId)))
                .where(coupon.couponType.eq(CouponType.DOWNLOAD),
                        coupon.status.eq(CouponStatus.ACTIVATED),
                        coupon.endDate.after(LocalDateTime.now()),
                        userCoupon.id.isNull())
                .orderBy(coupon.id.desc())
                .fetch();
    }

    @Override
    public List<CouponDto> findUserCouponByUserIdToApi(Long userId) {
        return queryFactory
                .select(Projections.constructor(CouponDto.class,
                        coupon.id,
                        coupon.name,
                        coupon.discountType.stringValue(),
                        coupon.discountValue,
                        coupon.minPurchasePrice,
                        coupon.maxDiscountPrice,
                        coupon.startDate,
                        coupon.endDate))
                .from(userCoupon)
                .join(userCoupon.coupon, coupon)
                .where(userCoupon.userId.eq(userId),
                        userCoupon.isUsed.eq(false),
                        coupon.endDate.after(LocalDateTime.now()),
                        coupon.status.eq(CouponStatus.ACTIVATED))
                .orderBy(coupon.id.desc())
                .fetch();
    }
}
