package com.fashionmall.order.repository;

import com.fashionmall.order.entity.Payment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.fashionmall.order.entity.QPayment.payment;

@Repository
@RequiredArgsConstructor
public class PaymentRepositoryImpl implements PaymentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Payment findByUserIdAndOrdersId(Long userId, Long orderId) {
        return queryFactory
                .selectFrom(payment)
                .where(payment.userId.eq(userId), payment.orders.id.eq(orderId))
                .fetchFirst();
    }
}