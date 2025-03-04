package com.fashionmall.user.dto.response;

import com.fashionmall.common.jwt.UserRoleEnum;
import com.fashionmall.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoResponseDto {

    private String email;
    private String userName;
    private String nickName;
    private String contact;
    private UserRoleEnum role;

    public static UserInfoResponseDto from (User user) {
        return new UserInfoResponseDto(
                user.getEmail(),
                user.getUserName(),
                user.getNickName(),
                user.getContact(),
                user.getRole()
        );
    }
}
