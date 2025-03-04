package com.fashionmall.order.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderItemResponseDto {

    private Long itemDetailId;
    private int quantity;

}