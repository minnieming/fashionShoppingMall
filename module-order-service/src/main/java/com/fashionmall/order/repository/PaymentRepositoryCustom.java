package com.fashionmall.order.repository;

import com.fashionmall.order.entity.Payment;

public interface PaymentRepositoryCustom {

    Payment findByUserIdAndOrdersId(Long userId, Long orderId);
}