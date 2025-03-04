package com.fashionmall.order.service;

import com.fashionmall.order.dto.request.PaymentCancelRequestDto;

public interface PaymentService {

    void cancelPayment(PaymentCancelRequestDto paymentCancelRequestDto);
}