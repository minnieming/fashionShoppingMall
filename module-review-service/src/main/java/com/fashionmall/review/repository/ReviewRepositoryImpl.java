package com.fashionmall.review.repository;

import com.fashionmall.common.response.PageInfoResponseDto;
import com.fashionmall.review.dto.response.ReviewResponseItemDto;
import com.fashionmall.review.dto.response.ReviewResponseMyPageDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.fashionmall.review.entity.QReview.review;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public PageInfoResponseDto<ReviewResponseMyPageDto> findReviewByUserId(Long userId, Pageable pageable) {
        int offset = (int) pageable.getOffset();
        int size = pageable.getPageSize();

        List<ReviewResponseMyPageDto> content = queryFactory
                .select(Projections.constructor(ReviewResponseMyPageDto.class,
                        review.id,
                        review.itemName,
                        review.itemId,
                        review.content,
                        review.createdAt))
                .from(review)
                .where(review.userId.eq(userId))
                .offset(offset)
                .limit(size)
                .orderBy(review.id.desc())
                .fetch();

        Long fetchOne = queryFactory
                .select(review.count())
                .from(review)
                .where(review.userId.eq(userId))
                .fetchOne();

        int totalCount = fetchOne.intValue();

        return PageInfoResponseDto.of(pageable, content, totalCount);
    }

    @Override
    public PageInfoResponseDto<ReviewResponseItemDto> findItemReviewByItemId(Long itemId, Pageable pageable) {
        int offset = (int) pageable.getOffset();
        int size = pageable.getPageSize();

        List<ReviewResponseItemDto> content = queryFactory
                .select(Projections.constructor(ReviewResponseItemDto.class,
                        review.id,
                        review.userId,
                        review.nickName,
                        review.content,
                        review.createdAt))
                .from(review)
                .where(review.itemId.eq(itemId))
                .offset(offset)
                .limit(size)
                .orderBy(review.id.desc())
                .fetch();

        Long fetchOne = queryFactory
                .select(review.count())
                .from(review)
                .where(review.itemId.eq(itemId))
                .fetchOne();

        int totalCount = fetchOne.intValue();

        return PageInfoResponseDto.of(pageable, content, totalCount);
    }

    @Override
    public void deleteReviewByUserIdAndId(Long userId, Long id) {
        queryFactory
                .delete(review)
                .where(review.userId.eq(userId).and(review.id.eq(id)));
    }
}
