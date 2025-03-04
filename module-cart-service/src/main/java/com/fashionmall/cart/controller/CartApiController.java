package com.fashionmall.cart.controller;

import com.fashionmall.cart.service.CartService;
import com.fashionmall.common.moduleApi.dto.CartItemDto;
import com.fashionmall.common.moduleApi.dto.ItemDetailDto;
import com.fashionmall.common.response.CommonResponse;
import com.fashionmall.common.util.ApiResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartApiController {

    private final CartService cartService;

    @GetMapping("/itemDetail/{userId}")
    public CommonResponse<List<ItemDetailDto>> getItemDetailFromCartApi(@PathVariable Long userId) {
        return ApiResponseUtil.success(cartService.getItemDetailFromCartApi(userId));
    }

    @GetMapping("/getIsSelectedItem")
    public CommonResponse<List<CartItemDto>> getIsSelectedItemApi() {
        Long userId = 1L;
        return ApiResponseUtil.success(cartService.getIsSelectedItemApi(userId));
    }
}
