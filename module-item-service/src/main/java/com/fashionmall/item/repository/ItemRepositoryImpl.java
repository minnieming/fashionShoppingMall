package com.fashionmall.item.repository;

import com.fashionmall.common.moduleApi.dto.LikeItemListResponseDto;
import com.fashionmall.common.moduleApi.dto.ItemPriceNameDto;
import com.fashionmall.common.response.PageInfoResponseDto;
import com.fashionmall.item.dto.response.AdminItemDetailResponseDto;
import com.fashionmall.item.dto.response.AdminItemResponseDto;
import com.fashionmall.item.dto.response.ItemDetailListResponseDto;
import com.fashionmall.item.dto.response.ItemListResponseDto;
import com.fashionmall.common.moduleApi.enums.ItemDiscountTypeEnum;
import com.fashionmall.item.dto.response.*;
import com.fashionmall.item.enums.StatusEnum;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.EnumPath;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;


import static com.fashionmall.item.entity.QCategory1.category1;
import static com.fashionmall.item.entity.QCategory2.category2;
import static com.fashionmall.item.entity.QItem.item;
import static com.fashionmall.item.entity.QItemCategoryMapping.itemCategoryMapping;

import static com.fashionmall.item.entity.QItemDetail.itemDetail;
import static com.fashionmall.item.entity.QItemDiscount.itemDiscount;

@Repository
@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<CategoryResponseDto> getCategoryList() {

        List<CategoryResponseDto> categoryList = queryFactory
                .select(Projections.constructor(CategoryResponseDto.class,
                        category1.id,
                        category1.name,
                        Projections.list(
                                Projections.constructor(CategoryResponseDto.Category2Info.class,
                                        category2.id,
                                        category2.name
                                )
                        )
                ))
                .from(category1)
                .innerJoin(category1.category2s, category2)
                .orderBy(category1.id.asc())
                .fetch();

        return categoryList;
    }

    @Override
    public List<LikeItemListResponseDto> getItemInfo (Long itemId, Long userId) {

        return queryFactory
                .select(Projections.constructor(LikeItemListResponseDto.class,
                        Projections.constructor(LikeItemListResponseDto.ItemInfo.class,
                                item.id,
                                item.name,
                                item.imageId,
                                item.imageUrl
                        ),
                        Projections.constructor(LikeItemListResponseDto.ItemDetailInfo.class,
                                itemDetail.price,
                                ExpressionUtils.as(calculateDiscount(itemDetail.price, itemDiscount.status, itemDiscount.type, itemDiscount.value),
                                        "discountPrice")
                        )
                ))
                .from(item)
                .innerJoin(item.itemDetails, itemDetail)
                .innerJoin(item.itemCategoryMappings, itemCategoryMapping)
                .innerJoin(item.itemDiscounts, itemDiscount)
                .where(item.id.eq(itemId).and(item.workerId.eq(userId)))
                .orderBy(item.id.desc())
                .fetch();
    }

    @Override
    public PageInfoResponseDto <ItemListResponseDto> itemListPageNation (Pageable pageable, String itemName, Long category1, Long category2) {

        List<ItemListResponseDto> itemList = queryFactory
                .select(Projections.constructor(ItemListResponseDto.class,
                        Projections.constructor(ItemListResponseDto.ItemInfo.class,
                                item.id,
                                item.name,
                                item.imageId,
                                item.imageUrl
                        ),
                        Projections.constructor(ItemListResponseDto.ItemDetailInfo.class,
                                itemDetail.price,
                                ExpressionUtils.as(calculateDiscount(itemDetail.price, itemDiscount.status, itemDiscount.type, itemDiscount.value),
                                        "discountPrice")
                        )
                ))
                .from(item)
                .leftJoin(item.itemDetails, itemDetail)
                .leftJoin(item.itemCategoryMappings, itemCategoryMapping)
                .leftJoin(item.itemDiscounts, itemDiscount)
                .where(getFilter(itemName, category1, category2))
                .orderBy(item.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long fetchOne = queryFactory
                .select(item.count())
                .from(item)
                .fetchOne();

        int totalCount = fetchOne.intValue();

        return PageInfoResponseDto.of(pageable, itemList, totalCount);

    }

    @Override
    public PageInfoResponseDto <AdminItemResponseDto> adminItemListPageNation (Pageable pageable, String itemName, Long category1, Long category2) {

        int offset = (int) pageable.getOffset();
        int size = pageable.getPageSize();

        List<AdminItemResponseDto> adminItemList = queryFactory
                .select(Projections.constructor(AdminItemResponseDto.class,
                        Projections.constructor(AdminItemResponseDto.ItemInfo.class,
                                item.id,
                                item.name,
                                item.workerId,
                                item.imageId,
                                item.imageUrl
                        ),
                        Projections.constructor(AdminItemResponseDto.ItemDetailInfo.class,
                                itemDetail.price,
                                ExpressionUtils.as(calculateDiscount(itemDetail.price, itemDiscount.status, itemDiscount.type, itemDiscount.value),
                                        "discountPrice")
                        )
                ))
                .from(item)
                .innerJoin(item.itemDetails, itemDetail)
                .innerJoin(item.itemCategoryMappings, itemCategoryMapping)
                .innerJoin(item.itemDiscounts, itemDiscount)
                .where(getFilter(itemName, category1, category2))
                .orderBy(item.id.desc())
                .offset(offset)
                .limit(size)
                .fetch();

        Long countList = queryFactory
                .select(item.count())
                .from(item)
                .fetchOne();

        int totalCount = countList.intValue();

        return PageInfoResponseDto.of(pageable, adminItemList, totalCount);

    }

    @Override
    public PageInfoResponseDto <AdminItemDetailResponseDto> adminItemDetailListPageNation (Long itemId, Pageable pageable) {

        List<AdminItemDetailResponseDto> adminItemDetailList = queryFactory
                .select(Projections.constructor(AdminItemDetailResponseDto.class,
                        Projections.constructor(AdminItemDetailResponseDto.ItemInfo.class,
                                item.id,
                                item.name,
                                item.workerId,
                                item.status
                        ),
                        Projections.constructor(AdminItemDetailResponseDto.ItemDetailInfo.class,
                                itemDetail.itemColor.color,
                                itemDetail.itemSize.size,
                                itemDetail.name,
                                itemDetail.price,
                                ExpressionUtils.as(calculateDiscount(itemDetail.price, itemDiscount.status, itemDiscount.type, itemDiscount.value),
                                        "discountPrice"),
                                itemDetail.quantity,
                                itemDetail.status,
                                itemDetail.imageId,
                                itemDetail.imageUrl
                        ),
                        Projections.constructor(AdminItemDetailResponseDto.ItemDiscountInfo.class,
                                itemDiscount.type,
                                itemDiscount.value,
                                itemDiscount.status
                        ),
                        Projections.constructor(AdminItemDetailResponseDto.ItemCategoryInfo.class,
                                itemCategoryMapping.category1.id,
                                itemCategoryMapping.category2Id
                        )
                ))
                .from(item)
                .innerJoin(item.itemDetails, itemDetail)
                .innerJoin(item.itemDiscounts, itemDiscount)
                .innerJoin(item.itemCategoryMappings, itemCategoryMapping)
                .where(item.id.eq(itemId))
                .orderBy(item.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long countList = queryFactory
                .select(item.count())
                .from(item)
                .where(item.id.eq(itemId))
                .fetchOne();

        int totalCount = countList.intValue();

        return PageInfoResponseDto.of(pageable, adminItemDetailList, totalCount);
    }

    private BooleanExpression getFilter (String itemName, Long category1, Long category2) {

        BooleanExpression filters = item.isNotNull();

        if (itemName != null && !itemName.isEmpty()) {
            filters = filters.and(item.name.containsIgnoreCase(itemName));
        }

        if (category1 != null) {
            filters = filters.and(itemCategoryMapping.category1.id.eq(category1));
        }

        if (category2 != null) {
            filters = filters.and(itemCategoryMapping.category2Id.eq(category2));
        }

        return filters;
    }

    public ItemPriceNameDto getDiscountPrice (Long itemDetailId, Long itemDiscountId) {

        return queryFactory
                .select(Projections.fields(ItemPriceNameDto.class,
                        itemDetail.id.as("itemDetailId"),
                        ExpressionUtils.as(calculateDiscount(itemDetail.price, itemDiscount.status, itemDiscount.type, itemDiscount.value),
                                "price"),
                        item.name
                        )
                )
                .from(item)
                .innerJoin(item.itemDetails, itemDetail)
                .innerJoin(item.itemDiscounts, itemDiscount)
                .where(itemDetail.id.eq(itemDetailId)
                        .and(itemDiscount.id.eq(itemDiscountId)))
                .orderBy(itemDetail.id.desc())
                .fetchOne();
    }

    private NumberExpression<Integer> calculateDiscount (NumberExpression<Integer> price, EnumPath<StatusEnum> status, EnumPath<ItemDiscountTypeEnum> type, NumberExpression<Integer> value) {
        return new CaseBuilder()
                .when(status.eq(StatusEnum.ACTIVATED).and(type.eq(ItemDiscountTypeEnum.RATE)))
                .then(price.subtract(price.multiply(value).divide(100)))
                .when(status.eq(StatusEnum.ACTIVATED).and(type.eq(ItemDiscountTypeEnum.AMOUNT)))
                .then(price.subtract(value))
                .otherwise(price);
    }
}
