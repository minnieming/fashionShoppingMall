package com.fashionmall.order.infra.iamPort.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TokenRequestDto {

    @JsonProperty("imp_key")
    private String impKey;
    @JsonProperty("imp_secret")
    private String impSecret;

}
