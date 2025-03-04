package com.fashionmall.order.entity;

import com.fashionmall.common.entity.BaseEntity;
import com.fashionmall.order.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
public class Orders extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "coupon_id")
    private Long couponId;

    @Column(name = "total_price", nullable = false)
    private int totalPrice;

    @Column(name = "coupon_discount_price", nullable = false)
    private int couponDiscountPrice;

    @Column(name = "item_discount_price", nullable = false)
    private int totalItemDiscountPrice;

    @Column(name = "payment_price", nullable = false)
    private int paymentPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;

    @Column(name = "zipcode", nullable = false)
    private String zipcode;

    @Column(name = "road_address", nullable = false)
    private String roadAddress;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @Builder
    public Orders(Long userId, Long couponId, int totalPrice, int couponDiscountPrice, int totalItemDiscountPrice, int paymentPrice, String zipcode, String roadAddress, List<OrderItem> orderItems) {
        this.userId = userId;
        this.couponId = couponId;
        this.totalPrice = totalPrice;
        this.couponDiscountPrice = couponDiscountPrice;
        this.totalItemDiscountPrice = totalItemDiscountPrice;
        this.paymentPrice = paymentPrice;
        this.status = OrderStatus.ORDERED;
        this.zipcode = zipcode;
        this.roadAddress = roadAddress;
        this.orderItems = orderItems;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
