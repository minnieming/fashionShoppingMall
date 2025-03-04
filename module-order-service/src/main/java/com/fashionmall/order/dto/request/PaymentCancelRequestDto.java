package com.fashionmall.order.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PaymentCancelRequestDto {

    private Long userId;
    private Long orderId;

    @JsonProperty("customer_uid")
    private String customerUid;
    @JsonProperty("merchant_uid")
    private String merchantUid;
    @JsonProperty("amount")
    private int amount;
    private String pg;
    @JsonProperty("reason")
    private String cancelReason;

}