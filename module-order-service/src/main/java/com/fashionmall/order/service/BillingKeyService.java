package com.fashionmall.order.service;

import com.fashionmall.order.dto.request.BillingKeyRequestDto;
import com.fashionmall.order.dto.response.UserBillingKeyResponseDto;

import java.util.List;

public interface BillingKeyService {

    Long issueBillingKey(BillingKeyRequestDto billingKeyRequestDto);

    List<UserBillingKeyResponseDto> getUserBillingKeyList(Long userId);

    Long deleteBillingKey(Long userId, Long billingKeyId);
}
