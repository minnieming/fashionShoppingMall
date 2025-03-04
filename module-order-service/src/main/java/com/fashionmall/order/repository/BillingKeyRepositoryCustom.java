package com.fashionmall.order.repository;

import com.fashionmall.order.dto.response.UserBillingKeyResponseDto;

import java.util.List;

public interface BillingKeyRepositoryCustom {

    List<UserBillingKeyResponseDto> findBillingKeyByUserId(Long userId);

    String findCustomerUidById(Long userId, Long id);
}
