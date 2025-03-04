package com.fashionmall.order.dto.request;

import com.fashionmall.order.entity.BillingKey;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BillingKeyRequestDto {
    private Long userId;
    @JsonProperty("card_nickname")
    private String cardNickname;
    @JsonProperty("card_number")
    private String cardNumber;
    @JsonProperty("expiry")
    private String expiry;
    @JsonProperty("birth")
    private String birth;
    @JsonProperty("pwd_2digit")
    private String pwd2digit;

    public BillingKey toEntity(String cardNumber, String cardName, String cardType, String customerUid) {
        return BillingKey
                .builder()
                .userId(userId)
                .cardNickname(cardNickname)
                .cardNumber(cardNumber)
                .cardName(cardName)
                .cardType(cardType)
                .customerUid(customerUid)
                .build();
    }

}