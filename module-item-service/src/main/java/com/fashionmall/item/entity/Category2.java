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
@Table (name = "category2")
@Slf4j (topic = "서브 카테고리")
@NoArgsConstructor (access = AccessLevel.PROTECTED)
public class Category2 extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "category2_name", nullable = false)
    private String name;

    @ManyToOne @JoinColumn (name = "category1_id",nullable = false)
    private Category1 category1;

    @Builder
    public Category2 (Category1 category1, String name) {
        this.category1 = category1;
        this.name = name;
    }
}
