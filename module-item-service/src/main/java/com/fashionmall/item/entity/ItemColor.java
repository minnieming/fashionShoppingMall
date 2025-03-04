package com.fashionmall.item.entity;

import com.fashionmall.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Entity
@Getter
@Table (name = "item_color")
@Slf4j (topic = "의류 색상 테이블")
@NoArgsConstructor (access = AccessLevel.PROTECTED)
public class ItemColor extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "item_color", nullable = false)
    private String color;

    @Builder
    public ItemColor (String color) {
        this.color = color;
    }
}
