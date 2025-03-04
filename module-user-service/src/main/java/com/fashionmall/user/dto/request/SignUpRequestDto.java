package com.fashionmall.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequestDto {

    @NotBlank(message = "이메일을 입력해주세요")
    @Email(message = "이메일 형식으로 입력해주세요")
    private String email;

    @NotBlank (message = "비밀번호를 입력해주세요")
    @Pattern (regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*\\W)[a-zA-Z0-9\\W]{8,}$",
            message = "대문자, 소문자, 특수문자, 숫자 포함 8자리 이상")
    private String password;

    @NotBlank(message = "동일한 비밀번호를 입력해주세요")
    private String confirmPassword;

    @NotBlank (message = "이름을 입력해주세요")
    @Pattern (regexp = "^[a-zA-Z0-9가-힣]{1,15}$", message = "특수문자 제외, 15자 이내")
    private String userName;

    @NotBlank (message = "닉네임을 입력해주세요")
    @Pattern (regexp = "^[a-zA-Z0-9가-힣]{6,}$", message = "특수문자 제외, 6자 이상")
    private String nickName;

    @NotBlank (message = "연락처를 입력해주세요")
    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "전화번호 형식이 올바르지 않습니다")
    private String contact;

    private boolean admin;
}
