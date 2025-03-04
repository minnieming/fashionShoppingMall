package com.fashionmall.user.entity;

import com.fashionmall.common.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "favorite")
public class Favorite extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false)
    private boolean isSelected;

    @Column (nullable = false)
    private Long itemId;

    @Column (nullable = false)
    private Long userId;

    @Builder
    public Favorite (boolean isSelected, Long itemId, Long userId) {
        this.isSelected = isSelected;
        this.itemId = itemId;
        this.userId = userId;
    }

}
