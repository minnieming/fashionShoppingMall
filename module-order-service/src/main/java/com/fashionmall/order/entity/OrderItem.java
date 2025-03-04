package com.fashionmall.order.entity;

import com.fashionmall.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "order_item")
public class OrderItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orders_id", nullable = false)
    private Orders orders;

    @Column(name = "item_detail_id", nullable = false)
    private Long itemDetailId;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "discount_price", nullable = false)
    private int itemDiscountPrice;

    @Builder
    public OrderItem(Long itemDetailId, int price, int quantity, int itemDiscountPrice) {
        this.itemDetailId = itemDetailId;
        this.price = price;
        this.quantity = quantity;
        this.itemDiscountPrice = itemDiscountPrice;
    }
}
