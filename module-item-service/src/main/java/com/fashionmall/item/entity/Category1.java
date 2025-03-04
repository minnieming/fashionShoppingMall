package com.fashionmall.item.entity;

import com.fashionmall.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table (name = "category1")
@Slf4j (topic = "메인 카테고리")
@NoArgsConstructor (access = AccessLevel.PROTECTED)
public class Category1 extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "category1_name", nullable = false)
    private String name;

    @OneToMany (mappedBy = "category1", cascade = CascadeType.ALL, orphanRemoval = true)
    private List <Category2> category2s = new ArrayList<>();

    @OneToMany (mappedBy = "category1", cascade = CascadeType.ALL, orphanRemoval = true)
    private List <ItemCategoryMapping> itemCategoryMappings = new ArrayList<>();

    @Builder
    public Category1 (String name) {
        this.name = name;
    }
}
