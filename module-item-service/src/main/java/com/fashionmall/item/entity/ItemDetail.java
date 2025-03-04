package com.fashionmall.item.entity;

import com.fashionmall.common.entity.BaseEntity;
import com.fashionmall.item.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Entity
@Getter
@Table (name = "item_detail")
@Slf4j (topic = "상품 상세 테이블")
@NoArgsConstructor (access = AccessLevel.PROTECTED)
public class ItemDetail extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne @JoinColumn (name = "item_id", nullable = false)
    private Item item;

    @ManyToOne @JoinColumn (name = "item_size_id", nullable = false)
    private ItemSize itemSize;

    @ManyToOne @JoinColumn (name = "item_color_id", nullable = false)
    private ItemColor itemColor;

    @Column (name = "image_description_id", nullable = false)
    private Long imageId;

    @Column (name = "image_description_url", length = 500, nullable = false)
    private String imageUrl;

    @Column (name = "item_detail_name", nullable = false)
    private String name;

    @Enumerated (EnumType.STRING)
    private StatusEnum status = StatusEnum.ACTIVATED; // 품절 여부

    @Column (name = "item_price", nullable = false)
    private int price;

    @Column (name = "item_quantity", nullable = false)
    private int quantity; // 재고수량

    @Builder
    public ItemDetail (Item item, ItemSize itemSize, ItemColor itemColor, Long imageId, String imageUrl, String name, StatusEnum status, int price, int quantity) {
        this.item = item;
        this.itemSize = itemSize;
        this.itemColor = itemColor;
        this.imageId = imageId;
        this.imageUrl = imageUrl;
        this.name = name;
        this.status = status;
        this.price = price;
        this.quantity = quantity;
    }

    public void updateName (String nName) {
        this.name = name;
    }
    public void updatePrice (int price) {
        this.price = price;
    }
    public void updateQuantity (int quantity) {
        this.quantity = quantity;
    }
    public void deductQuantity (int quantity) {
        this.quantity -= quantity;
    }
    public void restoreQuantity (int quantity) {
        this.quantity += quantity;
    }
    public void updateState (StatusEnum state) {
        this.status = state;
    }
    public void updateImageId (Long imageId) {
        this.imageId = imageId;
    }
    public void updateImageUrl (String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public void updateSize (ItemSize itemSize) { this.itemSize = itemSize;}
    public void updateColor (ItemColor itemColor) { this.itemColor = itemColor;}

}
