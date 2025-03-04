package com.fashionmall.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserInfoRequestDto {

    @NotBlank
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*\\W)[a-zA-Z0-9\\W]{8,}$",
            message = "대문자, 소문자, 특수문자, 숫자 포함 8자리 이상")
    private String oldPassword;

    @NotBlank
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*\\W)[a-zA-Z0-9\\W]{8,}$",
            message = "대문자, 소문자, 특수문자, 숫자 포함 8자리 이상")
    private String newPassword;

    @NotBlank
    @Pattern (regexp = "^[a-zA-Z0-9가-힣]{6,}$", message = "특수문자 제외, 6자 이상")
    private String nickName;

    @NotBlank
    @Pattern (regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "전화번호 형식이 올바르지 않습니다")
    private String contact;
}
