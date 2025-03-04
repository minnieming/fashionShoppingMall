package com.fashionmall.user.controller;

import com.fashionmall.common.jwt.JwtUtil;
import com.fashionmall.common.redis.RefreshToken;
import com.fashionmall.common.response.CommonResponse;
import com.fashionmall.common.util.ApiResponseUtil;
import com.fashionmall.user.dto.request.LoginRequestDto;
import com.fashionmall.user.dto.request.SignUpRequestDto;
import com.fashionmall.user.dto.request.UpdateUserInfoRequestDto;
import com.fashionmall.user.dto.response.LoginResponseDto;
import com.fashionmall.user.security.UserDetailsImpl;
import com.fashionmall.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/signUp")
    public CommonResponse<Long> signUp (@Valid @RequestBody SignUpRequestDto signUpRequestDto) {
        return ApiResponseUtil.success(userService.signUp(signUpRequestDto));
    }

    @PostMapping("/login")
    public CommonResponse<LoginResponseDto> login (@RequestBody LoginRequestDto loginRequestDto, HttpServletRequest request, HttpServletResponse response) {
        return ApiResponseUtil.success(userService.login(loginRequestDto, request, response));
    }

    @PatchMapping("/{userId}")
    public CommonResponse<Long> updateUserInfo (@Valid @RequestBody UpdateUserInfoRequestDto updateUserInfoRequestDto,
                                                @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ApiResponseUtil.success(userService.updateUserInfo(updateUserInfoRequestDto, userDetails.getUserid()));
    }

    @PostMapping ("/auth/refresh")
    public CommonResponse<String> getRefreshToken (@RequestBody RefreshToken refreshToken) {
        return ApiResponseUtil.success(userService.getRefreshToken (refreshToken.getRefreshToken()));
    }

    @PostMapping ("/logout")
    public CommonResponse<Void> logout (HttpServletRequest request) {

        String accessToken = jwtUtil.getJwtFromHeader(request);

        return ApiResponseUtil.success(userService.logout(accessToken));
    }
}
