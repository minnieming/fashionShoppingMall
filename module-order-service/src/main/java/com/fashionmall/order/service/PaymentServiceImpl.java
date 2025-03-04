package com.fashionmall.order.service;

import com.fashionmall.order.dto.request.PaymentCancelRequestDto;
import com.fashionmall.order.dto.response.PaymentResponseDto;
import com.fashionmall.order.entity.Payment;
import com.fashionmall.order.enums.PaymentStatus;
import com.fashionmall.order.infra.iamPort.dto.IamPortResponseDto;
import com.fashionmall.order.infra.iamPort.util.IamPortClient;
import com.fashionmall.order.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final IamPortClient iamPortClient;

    @Override
    public void cancelPayment(PaymentCancelRequestDto paymentCancelRequestDto) {

        Long userId = paymentCancelRequestDto.getUserId();
        Long orderId = paymentCancelRequestDto.getOrderId();
        Payment payment = paymentRepository.findByUserIdAndOrdersId(userId, orderId);

        paymentCancelRequestDto.setMerchantUid(payment.getMerchantUid());
        paymentCancelRequestDto.setCancelReason(paymentCancelRequestDto.getCancelReason());

        IamPortResponseDto<PaymentResponseDto> post = iamPortClient.cancelPayment(paymentCancelRequestDto);

        int cancelAmount = post.getResponse().getCancelAmount();
        long unixCancelledAt = post.getResponse().getCancelledAt();
        LocalDateTime cancelledAt = LocalDateTime.ofInstant(Instant.ofEpochSecond(unixCancelledAt), ZoneId.systemDefault());

        payment.setCancelPrice(cancelAmount);
        payment.setCanceled_at(cancelledAt);
        payment.setCancelReason(paymentCancelRequestDto.getCancelReason());
        payment.setStatus(PaymentStatus.CANCELED);
    }
}