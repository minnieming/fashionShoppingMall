package com.fashionmall.order.controller;

import com.fashionmall.common.response.CommonResponse;
import com.fashionmall.common.util.ApiResponseUtil;
import com.fashionmall.order.dto.request.BillingKeyRequestDto;
import com.fashionmall.order.dto.response.UserBillingKeyResponseDto;
import com.fashionmall.order.security.UserDetailsImpl;
import com.fashionmall.order.service.BillingKeyService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BillingKeyController {

    private final BillingKeyService billingKeyService;

    @PostMapping("/billingKey")
    public CommonResponse<Long> issueBillingKey(@RequestBody BillingKeyRequestDto billingKeyRequestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        billingKeyRequestDto.setUserId(userDetails.getUserid());
        return ApiResponseUtil.success(billingKeyService.issueBillingKey(billingKeyRequestDto));
    }

    @GetMapping("/billingKey")
    public CommonResponse<List<UserBillingKeyResponseDto>> getBillingKey(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ApiResponseUtil.success(billingKeyService.getUserBillingKeyList(userDetails.getUserid()));
    }

    @DeleteMapping("/billingKey/{billingKeyId}")
    public CommonResponse<Long> deleteBillingKey(@PathVariable(value = "billingKeyId") Long billingKeyId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ApiResponseUtil.success(billingKeyService.deleteBillingKey(userDetails.getUserid(), billingKeyId));
    }
}
