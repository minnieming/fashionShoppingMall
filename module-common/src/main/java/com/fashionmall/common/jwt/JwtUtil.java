package com.fashionmall.common.jwt;


import com.fashionmall.common.exception.CustomException;
import com.fashionmall.common.exception.ErrorResponseCode;
import com.fashionmall.common.redis.RedisUtil;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j(topic = "JWT Util")
@Component
public class JwtUtil {

    public static final String AUTHORIZATION_ROLE_KEY = "role";
    public static final String BEARER_PREFIX = "Bearer ";

    public final long ACCESS_TOKEN_EXPIRATION_TIME = 60 * 60 * 1000L; // 60분
    public final long REFRESH_TOKEN_EXPIRATION_TIME = 24 * 60 * 60 * 1000L; // 24시간

    @Value("${jwt.secret.key}")
    private String secretKey;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @PostConstruct
    public void init() {
        log.debug("Initializing JwtUtil with secretKey: {}", secretKey);
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    public String createToken (String email, UserRoleEnum role, Long userId, long expirationTime) {

        Date date = new Date();

        return Jwts.builder()
                .setSubject(email)
                .setId(String.valueOf(userId))
                .claim(AUTHORIZATION_ROLE_KEY, role)
                .setIssuedAt(date)
                .setExpiration(new Date (date.getTime() + expirationTime))
                .signWith(key, signatureAlgorithm)
                .compact();
    }

    public ResponseCookie createTokenToCookie (String tokenName, String accessToken, long expiration) {

        return ResponseCookie.from(tokenName, accessToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(expiration / 1000)
                .sameSite("Strict")
                .build();
    }

    public void addCookiesToResponse (HttpServletResponse response, String accessToken, String refreshToken) {

        ResponseCookie accessCookie = createTokenToCookie("access_token", accessToken, ACCESS_TOKEN_EXPIRATION_TIME);
        ResponseCookie refreshCookie = createTokenToCookie("refresh_token", refreshToken, REFRESH_TOKEN_EXPIRATION_TIME);

        response.addHeader("Set-AccessCookie", accessCookie.toString());
        response.addHeader("Set-RefreshCookie", refreshCookie.toString());
    }

    public String substringToken (String tokenValue) {
        if (StringUtils.hasText (tokenValue) && tokenValue.startsWith (BEARER_PREFIX)) {
            return tokenValue.substring(7);
        }
        throw new CustomException(ErrorResponseCode.JWT_NOT_FOUND_TOKEN);
    }

    public boolean validateToken (String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            log.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;
    }

    public Claims getUserInfoFromToken (String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public String getTokenFromRequest(HttpServletRequest req, String cookieName) {
        String accessToken = req.getHeader(cookieName);
        if (accessToken != null) {
            return accessToken;
        }
        throw new CustomException(ErrorResponseCode.JWT_NOT_FOUND_TOKEN);
    }

    public String getJwtFromHeader(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION); // ----------
        if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith(BEARER_PREFIX)) {
            return authorizationHeader.substring(7); // "Bearer " 이후의 토큰 부분만 추출
        }
        throw new CustomException(ErrorResponseCode.JWT_NOT_FOUND_TOKEN);
    }

    public boolean validateToken(String token, RedisUtil redisUtil) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            if (redisUtil.hasKeyBlackList(token)) {
                return false;
            }
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            log.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;
    }

    public String getSubject(String token) {

        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }

    public String getId(String token) {

        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getId();
    }

    public String getRole(String token) {

        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().get(AUTHORIZATION_ROLE_KEY, String.class);
    }

    public Date getExpiration(String token) {

        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getExpiration();
    }

}
