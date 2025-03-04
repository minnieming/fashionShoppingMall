package com.fashionmall.item.security;


import com.fashionmall.common.jwt.UserRoleEnum;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Slf4j(topic = "UserDetailsImpl")
@RequiredArgsConstructor
@Getter
public class UserDetailsImpl implements UserDetails {

    private final String email;
    private final Long userId;
    private final UserRoleEnum role;

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return email;
    }

    public Long getUserid() {
        return userId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        String authority = role.getAuthority();

        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(simpleGrantedAuthority);

        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof UserDetailsImpl)) {
            return false;
        }
        UserDetailsImpl other = (UserDetailsImpl) obj;
        return Objects.equals(email, other.email) && Objects.equals(userId, other.userId) && Objects.equals(role, other.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, userId, role);
    }
}