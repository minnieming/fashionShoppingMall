package com.fashionmall.order.service;

import com.fashionmall.order.dto.request.BillingKeyRequestDto;
import com.fashionmall.order.dto.response.BillingKeyResponseDto;
import com.fashionmall.order.dto.response.UserBillingKeyResponseDto;
import com.fashionmall.order.entity.BillingKey;
import com.fashionmall.order.infra.iamPort.dto.IamPortResponseDto;
import com.fashionmall.order.infra.iamPort.util.IamPortClient;
import com.fashionmall.order.repository.BillingKeyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class BillingKeyServiceImpl implements BillingKeyService {

    private final BillingKeyRepository billingKeyRepository;
    private final IamPortClient iamPortClient;

    @Transactional
    @Override
    public Long issueBillingKey(BillingKeyRequestDto billingKeyRequestDto) {

        String customerUid = UUID.randomUUID().toString();

        Map<String, String> request = Map.of(
                "pg", "nice",
                "card_number", billingKeyRequestDto.getCardNumber(),
                "expiry", billingKeyRequestDto.getExpiry(),
                "birth", billingKeyRequestDto.getBirth(),
                "pwd_2digit", billingKeyRequestDto.getPwd2digit());

        IamPortResponseDto<BillingKeyResponseDto> IamPortResponse = iamPortClient.getBillingKey(customerUid, request);

        String cardName = IamPortResponse.getResponse().getCardName();
        String cardType = IamPortResponse.getResponse().getCardType();

        if (cardType.equals("0")) {
            cardType = "신용카드";
        } else if (cardType.equals("1")) {
            cardType = "체크카드";
        }

        String cardNumber = maskCardNumber(billingKeyRequestDto.getCardNumber());

        BillingKey billingKey = billingKeyRequestDto.toEntity(cardNumber, cardName, cardType, customerUid);
        billingKeyRepository.save(billingKey);

        return billingKey.getId();
    }

    @Override
    public List<UserBillingKeyResponseDto> getUserBillingKeyList(Long userId) {

        return billingKeyRepository.findBillingKeyByUserId(userId);
    }

    @Transactional
    @Override
    public Long deleteBillingKey(Long userId, Long billingKeyId) {

        String customerUid = billingKeyRepository.findCustomerUidById(userId, billingKeyId);

        iamPortClient.deleteBillingKey(customerUid);

        billingKeyRepository.deleteById(billingKeyId);

        return billingKeyId;
    }

    private String maskCardNumber(String cardNumber) {
        int length = cardNumber.length();
        String last4Digits = cardNumber.substring(length - 4);
        return "*".repeat(length - 4) + last4Digits;
    }
}
