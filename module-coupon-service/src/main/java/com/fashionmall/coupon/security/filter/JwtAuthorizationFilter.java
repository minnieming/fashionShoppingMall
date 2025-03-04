package com.fashionmall.coupon.security.filter;

import com.fashionmall.common.jwt.JwtUtil;
import com.fashionmall.common.jwt.UserRoleEnum;
import com.fashionmall.common.redis.RedisUtil;
import com.fashionmall.coupon.security.UserDetailsImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j(topic = "JWT 검증 및 인가")
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (request.getCookies() != null && request.getCookies().length > 0) {
            log.info("request {} {}", request.getCookies()[0].getName(), request.getCookies()[0].getValue());
        }

        if (!StringUtils.hasText(request.getHeader(HttpHeaders.AUTHORIZATION))) {
            filterChain.doFilter(request, response);
            return;
        }

        String tokenValue = jwtUtil.getTokenFromRequest(request, HttpHeaders.AUTHORIZATION);

        System.out.println("tokenValue = " + tokenValue);

        tokenValue = jwtUtil.substringToken(tokenValue);

        if (redisUtil.hasKeyBlackList(tokenValue)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        if (!jwtUtil.validateToken(tokenValue)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String email = jwtUtil.getSubject(tokenValue);
        String id = jwtUtil.getId(tokenValue);
        String roleString = jwtUtil.getRole(tokenValue);

        try {
            UserRoleEnum role = UserRoleEnum.valueOf(roleString);

            setAuthentication(email, id, role);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        filterChain.doFilter(request, response);

    }

    public void setAuthentication(String email, String userId, UserRoleEnum role) {

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(email, userId, role);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);

    }

    private Authentication createAuthentication(String email, String userId, UserRoleEnum role) {

        UserDetails userDetails = new UserDetailsImpl(email, Long.valueOf(userId), role);

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
