package com.fashionmall.cart.repository;

import com.fashionmall.cart.dto.response.CartResponseDto;
import com.fashionmall.common.moduleApi.dto.ItemDetailDto;

import java.util.List;

public interface CartRepositoryCustom {

    List<ItemDetailDto> getItemDetailFromCartApi(Long userId);

    List<CartResponseDto> getCartList (Long userId, int discountPrice, String itemName, boolean isSelected, String imageUrl);
}
