package com.fashionmall.cart.controller;

import com.fashionmall.cart.dto.response.CartResponseDto;
import com.fashionmall.cart.security.UserDetailsImpl;
import com.fashionmall.cart.service.CartService;
import com.fashionmall.common.response.CommonResponse;
import com.fashionmall.common.util.ApiResponseUtil;
import com.fashionmall.common.util.ProfileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CartQueryController {

    private final CartService cartService;
    private final ProfileUtil profileUtil;

    @GetMapping("/cart")
    public CommonResponse<List<CartResponseDto>> getCartList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ApiResponseUtil.success(cartService.getCartList(userDetails.getUserid()));
    }

    @GetMapping("/cart/profile")
    public String getCartProfile() {
        return profileUtil.getCurrentProfile();
    }
}
