package com.fashionmall.item.controller;

import com.fashionmall.common.util.ProfileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j(topic = "item controller")
@RestController
@RequiredArgsConstructor
public class ItemController {

    private final ProfileUtil profileUtil;

    @GetMapping("/item/profile")
    public String getCartProfile() {
        return profileUtil.getCurrentProfile();
    }
}
