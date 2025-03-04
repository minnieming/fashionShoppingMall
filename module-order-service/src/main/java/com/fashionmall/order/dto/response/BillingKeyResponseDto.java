package com.fashionmall.order.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BillingKeyResponseDto {

    @JsonProperty("customer_uid")
    private String customerUid;
    @JsonProperty("pg_provider")
    private String pgProvider;
    @JsonProperty("pg_id")
    private String pgId;
    @JsonProperty("card_name")
    private String cardName;
    @JsonProperty("card_type")
    private String cardType;
    @JsonProperty("inserted")
    private int inserted;
    @JsonProperty("updated")
    private int updated;

}
