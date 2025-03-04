package com.fashionmall.common.redis;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@RedisHash(value = "refreshToken", timeToLive = 86400)
public class RefreshToken {

    @Id
    private Long userId;

    private String refreshToken;

    @Override
    public String toString() {

        return "RefreshToken{"
                + "userId=" + userId + ", refreshToken='" + refreshToken + '\'' + '}';

    }
}
