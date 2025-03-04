package com.fashionmall.order.repository;

import com.fashionmall.order.dto.response.UserBillingKeyResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.fashionmall.order.entity.QBillingKey.billingKey;

@Repository
@RequiredArgsConstructor
public class BillingKeyRepositoryImpl implements BillingKeyRepositoryCustom {

    private final JPAQueryFactory queryFactory;


    public List<UserBillingKeyResponseDto> findBillingKeyByUserId(Long userId) {

        return queryFactory
                .select(Projections.constructor(UserBillingKeyResponseDto.class,
                        billingKey.id,
                        billingKey.cardNickname,
                        billingKey.cardName,
                        billingKey.cardType,
                        billingKey.cardNumber))
                .from(billingKey)
                .where(billingKey.userId.eq(userId))
                .orderBy(billingKey.id.asc())
                .fetch();
    }

    @Override
    public String findCustomerUidById(Long userId, Long id) {
        return queryFactory
                .select(billingKey.customerUid)
                .from(billingKey)
                .where(billingKey.id.eq(id), billingKey.userId.eq(userId))
                .fetchOne();
    }
}
