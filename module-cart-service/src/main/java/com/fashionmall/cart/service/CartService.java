package com.fashionmall.cart.service;

import com.fashionmall.cart.dto.request.CartCalculateRequestDto;
import com.fashionmall.cart.dto.request.CartRequestDto;
import com.fashionmall.cart.dto.request.CartUpdateRequestDto;
import com.fashionmall.cart.dto.response.CartUpdateResponseDto;
import com.fashionmall.common.moduleApi.dto.CartItemDto;
import com.fashionmall.common.moduleApi.dto.ItemDetailDto;

import java.util.List;
import com.fashionmall.cart.dto.response.CartCalculateResponseDto;
import com.fashionmall.cart.dto.response.CartResponseDto;

public interface CartService {
    List<Long> createCart (CartRequestDto cartRequestDto, Long userId);
    CartUpdateResponseDto updateCart (Long cartId, CartUpdateRequestDto cartUpdateRequestDto, Long userId);
    Long deleteCart(Long cartId, Long userId);

    List <ItemDetailDto> getItemDetailFromCartApi (Long userId);

    List<CartResponseDto> getCartList(Long userId);

    List<CartCalculateResponseDto> calculateCart(CartCalculateRequestDto cartCalculateRequestDto, Long userId);

    List<CartItemDto> getIsSelectedItemApi(Long userId);
}
