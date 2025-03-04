package com.fashionmall.order.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserBillingKeyResponseDto {

    private Long id;

    @JsonProperty("card_nickname")
    private String cardNickname;

    @JsonProperty("card_name")
    private String cardName;

    @JsonProperty("card_type")
    private String cardType;

    @JsonProperty("card_number")
    private String cardNumber;
}
