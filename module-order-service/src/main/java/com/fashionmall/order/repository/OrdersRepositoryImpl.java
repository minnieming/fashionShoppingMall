package com.fashionmall.order.repository;

import com.fashionmall.common.moduleApi.dto.OrderItemDto;
import com.fashionmall.common.response.PageInfoResponseDto;
import com.fashionmall.order.dto.response.OrderItemDetailResponseDto;
import com.fashionmall.order.dto.response.OrdersDetailResponseDto;
import com.fashionmall.order.dto.response.OrdersResponseDto;
import com.fashionmall.order.enums.OrderStatus;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.fashionmall.order.entity.QOrderItem.orderItem;
import static com.fashionmall.order.entity.QOrders.orders;

@Repository
@RequiredArgsConstructor
public class OrdersRepositoryImpl implements OrdersRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<OrderItemDto> findOrderItemsByOrderId(Long orderId) {
        return queryFactory
                .select(Projections.constructor(OrderItemDto.class,
                        orderItem.itemDetailId,
                        orderItem.quantity))
                .from(orderItem)
                .where(orderItem.orders.id.eq(orderId))
                .fetch();
    }

    @Override
    public PageInfoResponseDto<OrdersResponseDto> findOrdersByUserId(Long userId, Pageable pageable) {
        int offset = (int) pageable.getOffset();
        int size = pageable.getPageSize();

        List<OrdersResponseDto> content = queryFactory
                .select(Projections.constructor(OrdersResponseDto.class,
                        orders.id,
                        orders.status,
                        orders.paymentPrice,
                        orders.createdAt,
                        JPAExpressions
                                .select(orderItem.itemDetailId)
                                .from(orderItem)
                                .where(orderItem.orders.eq(orders))
                                .orderBy(orderItem.orders.id.asc())
                                .limit(1)))
                .from(orders)
                .where(orders.userId.eq(userId))
                .offset(offset)
                .limit(size)
                .fetch();

        Long fetchOne = queryFactory
                .select(orders.count())
                .from(orders)
                .where(orders.userId.eq(userId))
                .fetchOne();

        int totalCount = fetchOne.intValue();

        return PageInfoResponseDto.of(pageable, content, totalCount);
    }

    @Override
    public OrdersDetailResponseDto findOrdersDetailsByUserIdAndOrderId(Long userId, Long orderId) {
        return queryFactory
                .select(Projections.constructor(OrdersDetailResponseDto.class,
                        orders.id,
                        orders.totalPrice,
                        orders.couponDiscountPrice,
                        orders.totalItemDiscountPrice,
                        orders.paymentPrice,
                        orders.zipcode,
                        orders.roadAddress,
                        JPAExpressions
                                .select(Projections.constructor(OrderItemDetailResponseDto.class,
                                        orderItem.itemDetailId,
                                        orderItem.price,
                                        orderItem.quantity,
                                        orderItem.itemDiscountPrice,
                                        orderItem.price.multiply(orderItem.quantity).as("totalPrice"),
                                        orderItem.price.multiply(orderItem.quantity)
                                                .subtract(orderItem.itemDiscountPrice.multiply(orderItem.quantity))
                                                .as("totalDiscountPrice"))
                                )
                                .from(orderItem)
                                .where(orderItem.orders.id.eq(orderId))
                ))
                .from(orders)
                .where(orders.id.eq(orderId))
                .fetchOne();
    }

    @Override
    public Long cancelOrderById(Long userId, Long orderId) {
        long update = queryFactory
                .update(orders)
                .where(orders.userId.eq(userId), orders.id.eq(orderId))
                .set(orders.status, OrderStatus.CANCELED)
                .execute();

        return update > 0 ? orderId : null;
    }
}