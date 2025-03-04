package com.fashionmall.cart.entity;

import com.fashionmall.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Entity
@Getter
@Table (name = "cart")
@Slf4j(topic = "장바구니")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "user_id", nullable = false)
    private Long userId; //  받아오는 사용자 Id

    @Column (name = "item_detail_id", nullable = false)
    private Long itemDetailId; //  받아오는 상품 id

    @Column (name = "imageId", nullable = false)
    private Long imageId; // 받아오는 ImageId

    @Column (name = "item_detail_name", nullable = false)
    private String itemDetailName;

    @Column (name = "quantity", nullable = false)
    private int quantity; // 해당 제품의 선택 수량

    @Column (name = "price", nullable = false)
    private int price;

    @Column (name = "is_selected", nullable = false)
    private boolean isSelected ;

    @Builder
    public Cart (Long userId, Long itemDetailId, Long imageId, String itemDetailName, int quantity, int price, boolean isSelected) {
        this.userId = userId;
        this.itemDetailId = itemDetailId;
        this.imageId = imageId;
        this.itemDetailName = itemDetailName;
        this.quantity = quantity;
        this.price = price;
        this.isSelected = isSelected;
    }

    public void updateQuantity (int itemQuantity) {
        this.quantity = itemQuantity;
    }

    public void updateIsSelected (boolean isSelected) {
        this.isSelected = isSelected;
    }

}
