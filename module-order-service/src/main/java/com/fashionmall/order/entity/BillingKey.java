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
@Table(name = "billing_key")
public class BillingKey extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "customer_uid")
    private String customerUid; //구매자의 결제 수단 식별 고유번호

    @Column(name = "card_nickname")
    private String cardNickname; //카드 별명

    @Column(name = "card_name")
    private String cardName; //카드명

    @Column(name = "card_number")
    private String cardNumber; //마스킹 카드번호

    @Column(name = "card_type")
    private String cardType; //카드유형

    @Builder
    public BillingKey(Long userId, String customerUid, String cardNickname, String cardName, String cardNumber, String cardType) {
        this.userId = userId;
        this.customerUid = customerUid;
        this.cardNickname = cardNickname;
        this.cardName = cardName;
        this.cardNumber = cardNumber;
        this.cardType = cardType;
    }
}
