package com.fashionmall.order.infra.iamPort.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TokenResponseDto {

    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("expired_at")
    private int expiredAt;
    @JsonProperty("now")
    private int now;
}
