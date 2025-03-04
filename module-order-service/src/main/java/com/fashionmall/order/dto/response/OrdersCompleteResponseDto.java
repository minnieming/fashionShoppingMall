package com.fashionmall.order.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class OrdersCompleteResponseDto {

    private Long orderId;
    private LocalDateTime createTime;
    private int paymentPrice;

}