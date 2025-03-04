package com.fashionmall.item.entity;

import com.fashionmall.common.entity.BaseEntity;
import com.fashionmall.common.moduleApi.enums.ItemDiscountTypeEnum;
import com.fashionmall.item.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Entity
@Getter
@Table (name = "item_discount")
@Slf4j (topic = "상품 할인 테이블")
@NoArgsConstructor (access = AccessLevel.PROTECTED)
public class ItemDiscount extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne @JoinColumn (name = "item_id", nullable = false)
    private Item item;

    @Enumerated (EnumType.STRING) @Column (nullable = false)
    private ItemDiscountTypeEnum type;

    @Column (name = "item_discount_value", nullable = false)
    private int value; // '할인 값(정률: %, 정액: 원)'

    @Enumerated (EnumType.STRING)
    private StatusEnum status = StatusEnum.INACTIVATED;

    @Builder
    public ItemDiscount (Item item, ItemDiscountTypeEnum type, int value, StatusEnum status) {
        this.item = item;
        this.type = type;
        this.value = value;
        this.status = (status != null) ? status : StatusEnum.INACTIVATED;;
    }
    public void updateItemDiscountType (ItemDiscountTypeEnum itemDiscountType) {
        this.type = itemDiscountType;
    }
    public void updateValue (int value) {
        this.value = value;
    }
    public void updateStatus (StatusEnum status) {
        this.status = status;
    }
}
