package com.fashionmall.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryAddressRequestDto {

    @NotNull(message = "우편번호를 입력해주세요")
    @Pattern(regexp = "^[0-9]+$", message = "우편번호는 숫자만 입력 가능합니다")
    private String zipcode;

    @NotBlank(message = "주소를 입력해주세요")
    private String roadAddress;

}
