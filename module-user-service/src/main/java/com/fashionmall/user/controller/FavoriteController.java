package com.fashionmall.user.controller;

import com.fashionmall.common.response.CommonResponse;
import com.fashionmall.common.util.ApiResponseUtil;
import com.fashionmall.user.dto.request.FavoriteRequestDto;
import com.fashionmall.user.dto.response.FavoriteResponseDto;
import com.fashionmall.user.security.UserDetailsImpl;
import com.fashionmall.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j(topic = "favorite_controller")
@RestController
@RequiredArgsConstructor
public class FavoriteController {

    private final UserService userService;

    @PatchMapping ("/favorite/{itemId}")
    public CommonResponse <FavoriteResponseDto> updateFavorite (@PathVariable Long itemId,
                                                                @Valid @RequestBody FavoriteRequestDto favoriteRequestDto,
                                                                @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ApiResponseUtil.success(userService.updateFavorite(itemId, favoriteRequestDto, userDetails.getUserid()));
    }
}
