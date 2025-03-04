package com.fashionmall.user.service;

import com.fashionmall.common.moduleApi.dto.DeliveryAddressDto;
import com.fashionmall.common.moduleApi.dto.LikeItemListResponseDto;
import com.fashionmall.common.response.PageInfoResponseDto;
import com.fashionmall.user.dto.request.DeliveryAddressRequestDto;
import com.fashionmall.user.dto.request.FavoriteRequestDto;
import com.fashionmall.user.dto.request.LoginRequestDto;
import com.fashionmall.user.dto.request.SignUpRequestDto;
import com.fashionmall.user.dto.request.UpdateUserInfoRequestDto;
import com.fashionmall.user.dto.response.FavoriteResponseDto;
import com.fashionmall.user.dto.response.LoginResponseDto;
import com.fashionmall.user.dto.response.UserInfoResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface UserService {

    // User
    Long signUp (SignUpRequestDto signUpRequestDto);

    LoginResponseDto login (LoginRequestDto loginRequestDto, HttpServletRequest request, HttpServletResponse response);

    Long updateUserInfo (UpdateUserInfoRequestDto updateUserInfoRequestDto, Long userId);

    UserInfoResponseDto userInfo(Long userId);

    String getRefreshToken (String refreshToken);

    Void logout(String accessToken);

    Long confirmUserInfoApi (Long userId);

    // DeliveryAddress
    Long createDeliveryAddress (DeliveryAddressRequestDto deliveryAddressRequestDto, Long userId);

    List<DeliveryAddressDto> getDeliveryAddress (Long userId);

    // Favorite
    FavoriteResponseDto updateFavorite (Long itemId, FavoriteRequestDto favoriteRequestDto, Long userId);

    PageInfoResponseDto<LikeItemListResponseDto> favoriteList (int pageNo, int size, Long itemId, Long userId);
}
