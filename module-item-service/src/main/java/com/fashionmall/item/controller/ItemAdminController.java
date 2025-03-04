package com.fashionmall.item.controller;

import com.fashionmall.common.response.CommonResponse;
import com.fashionmall.common.util.ApiResponseUtil;
import com.fashionmall.item.dto.request.ItemDiscountRequestDto;
import com.fashionmall.item.dto.request.ItemRequestDto;
import com.fashionmall.item.dto.request.ItemUpdateRequestDto;
import com.fashionmall.item.dto.response.ItemDiscountResponseDto;
import com.fashionmall.item.dto.response.ItemResponseDto;
import com.fashionmall.item.dto.response.ItemUpdateResponseDto;
import com.fashionmall.item.security.UserDetailsImpl;
import com.fashionmall.item.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j(topic = "item_admin_controller")
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class ItemAdminController {

    private final ItemService itemService;

    @PostMapping("/item")
    public CommonResponse<ItemResponseDto> createItem(@Valid @RequestBody ItemRequestDto itemRequestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ApiResponseUtil.success(itemService.createItem(itemRequestDto, userDetails.getUserid()));
    }

    @PostMapping("/itemDiscount")
    public CommonResponse<ItemDiscountResponseDto> createItemDiscount(@Valid @RequestBody ItemDiscountRequestDto itemDiscountRequestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ApiResponseUtil.success(itemService.createItemDiscount(itemDiscountRequestDto, userDetails.getUserid()));
    }

    @PatchMapping("/item/{itemId}")
    public CommonResponse<ItemUpdateResponseDto> updateItem(@PathVariable Long itemId,
        @Valid @RequestBody ItemUpdateRequestDto itemUpdateRequestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ApiResponseUtil.success(itemService.updateItem(itemId, itemUpdateRequestDto, userDetails.getUserid()));
    }

    @DeleteMapping("/item/{itemId}")
    public CommonResponse<Long> deleteItem(@PathVariable Long itemId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ApiResponseUtil.success(itemService.deleteItem(itemId, userDetails.getUserid()));
    }

}
