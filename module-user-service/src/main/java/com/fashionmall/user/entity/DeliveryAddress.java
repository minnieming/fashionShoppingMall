package com.fashionmall.user.entity;

import com.fashionmall.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Entity
@Getter
@Table(name = "delivery_address")
@Slf4j(topic = "판매상품 테이블")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeliveryAddress extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false)
    private Long userId;

    @Column (nullable = false, length = 10)
    private String zipCode;

    @Column (nullable = false, length = 100)
    private String roadAddress;

    @Builder
    public DeliveryAddress (Long userId, String zipCode, String roadAddress) {
        this.userId = userId;
        this.zipCode = zipCode;
        this.roadAddress = roadAddress;
    }
}
