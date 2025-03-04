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
@Table (name = "item_size")
@Slf4j (topic = "의류 사이즈 테이블")
@NoArgsConstructor (access = AccessLevel.PROTECTED)
public class ItemSize extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "item_size", nullable = false)
    private String size;

    @Builder
    public ItemSize (Long sizeId, String size) {
        this.id = sizeId;
        this.size = size;
    }

}
