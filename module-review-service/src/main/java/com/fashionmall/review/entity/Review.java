package com.fashionmall.review.entity;

import com.fashionmall.common.entity.BaseEntity;
import com.fashionmall.review.enums.ReviewStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "review")
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "nick_name", nullable = false)
    private String nickName;

    @Column(name = "item_id", nullable = false)
    private Long itemId;

    @Column(name = "item_name", nullable = false)
    private String itemName;

    @Column(name = "content", nullable = false, length = 300)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ReviewStatus status;

    @Builder
    public Review(Long userId, String nickName, Long itemId, String itemName, String content) {
        this.userId = userId;
        this.nickName = nickName;
        this.itemId = itemId;
        this.itemName = itemName;
        this.content = content;
        this.status = ReviewStatus.ACTIVATED;
    }
}
