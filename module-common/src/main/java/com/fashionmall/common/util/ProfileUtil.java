package com.fashionmall.common.util;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProfileUtil {

    private final Environment env;

    public String getCurrentProfile() {
        List<String> profile = Arrays.asList(env.getActiveProfiles());
        List<String> realProfiles = Arrays.asList("green", "blue");
        String defaultProfile = profile.isEmpty() ? "default" : profile.get(0);

        return profile.stream()
                .filter(realProfiles::contains)
                .findAny()
                .orElse(defaultProfile);
    }
}
