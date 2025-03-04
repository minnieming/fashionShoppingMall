package com.fashionmall.cart.controller;

import com.fashionmall.cart.dto.request.CartCalculateRequestDto;
import com.fashionmall.cart.dto.request.CartRequestDto;
import com.fashionmall.cart.dto.request.CartUpdateRequestDto;
import com.fashionmall.cart.dto.response.CartCalculateResponseDto;
import com.fashionmall.cart.dto.response.CartUpdateResponseDto;
import com.fashionmall.cart.security.UserDetailsImpl;
import com.fashionmall.cart.service.CartService;
import com.fashionmall.common.response.CommonResponse;
import com.fashionmall.common.util.ApiResponseUtil;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j(topic = "cart controller")
@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/cart")
    public CommonResponse<List<Long>> createCart(@Valid @RequestBody CartRequestDto cartRequestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ApiResponseUtil.success(cartService.createCart(cartRequestDto, userDetails.getUserid()));
    }

    @PatchMapping("/cart/{cartId}")
    public CommonResponse<CartUpdateResponseDto> updateCart(@PathVariable Long cartId,
        @Valid @RequestBody CartUpdateRequestDto cartUpdateRequestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ApiResponseUtil.success(cartService.updateCart(cartId, cartUpdateRequestDto, userDetails.getUserid()));
    }

    @DeleteMapping("/cart/{cartId}")
    public CommonResponse<Long> deleteCart(@PathVariable Long cartId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ApiResponseUtil.success(cartService.deleteCart(cartId, userDetails.getUserid()));
    }

    @GetMapping("/cart/calculate")
    public CommonResponse<List<CartCalculateResponseDto>> calculateCart(@Valid @RequestBody CartCalculateRequestDto cartCalculateRequestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ApiResponseUtil.success(cartService.calculateCart(cartCalculateRequestDto, userDetails.getUserid()));
    }

}
