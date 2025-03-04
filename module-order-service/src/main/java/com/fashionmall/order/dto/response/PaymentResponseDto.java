package com.fashionmall.order.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponseDto {

    @JsonProperty("imp_uid")
    private String impUid;
    @JsonProperty("merchant_uid")
    private String merchantUid;
    @JsonProperty("amount")
    private int amount;
    @JsonProperty("cancel_amount")
    private int cancelAmount;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("status")
    private String status;
    @JsonProperty("paid_at")
    private int paidAt;
    @JsonProperty("cancelled_at")
    private int cancelledAt;

}