package com.fashionmall.cart.repository;

import com.fashionmall.common.moduleApi.dto.ItemDetailDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import com.fashionmall.cart.dto.response.CartResponseDto;

import java.util.List;

import static com.fashionmall.cart.entity.QCart.cart;

@Repository
@RequiredArgsConstructor
public class CartRepositoryImpl implements CartRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ItemDetailDto> getItemDetailFromCartApi(Long userId) {
        return jpaQueryFactory
                .select(Projections.constructor(ItemDetailDto.class,
                        cart.itemDetailId,
                        cart.itemDetailName,
                        cart.price,
                        cart.quantity
                ))
                .from(cart)
                .where(cart.userId.eq(userId),
                        cart.isSelected.eq(true))
                .orderBy(cart.id.desc())
                .fetch();
        }

    @Override
    public List<CartResponseDto> getCartList (Long userId, int discountPrice, String itemName, boolean isSelected, String imageUrl) {

        List<CartResponseDto> cartList = jpaQueryFactory
                .select(Projections.constructor(CartResponseDto.class,
                        cart.id,
                        cart.itemDetailId,
                        Expressions.constant(itemName),
                        Expressions.constant(imageUrl),
                        cart.quantity,
                        cart.price,
                        Expressions.constant(discountPrice),
                        cart.isSelected
                        ))
                .from(cart)
                .where(cart.userId.eq(userId).and(cart.isSelected.eq(isSelected)))
                .orderBy(cart.id.desc())
                .fetch();

        return cartList;
    }
}
