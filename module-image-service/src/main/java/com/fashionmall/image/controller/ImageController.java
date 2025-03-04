package com.fashionmall.image.controller;

import com.fashionmall.common.util.ProfileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ImageController {

    private final ProfileUtil profileUtil;

    @GetMapping("/image/profile")
    public String getCartProfile() {
        return profileUtil.getCurrentProfile();
    }
}
