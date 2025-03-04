package com.fashionmall.item.controller;

import com.fashionmall.common.response.CommonResponse;
import com.fashionmall.common.response.PageInfoResponseDto;
import com.fashionmall.common.util.ApiResponseUtil;
import com.fashionmall.item.dto.response.AdminItemDetailResponseDto;
import com.fashionmall.item.dto.response.AdminItemResponseDto;
import com.fashionmall.item.dto.response.CategoryResponseDto;
import com.fashionmall.item.dto.response.ItemDetailListResponseDto;
import com.fashionmall.item.dto.response.ItemListResponseDto;
import com.fashionmall.item.security.UserDetailsImpl;
import com.fashionmall.item.service.ItemService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j(topic = "itemQueryController")
@RestController
@RequiredArgsConstructor
public class ItemQueryController {

    private final ItemService itemService;

    @GetMapping("/item")
    public CommonResponse<PageInfoResponseDto<ItemListResponseDto>> getItemList(@RequestParam(defaultValue = "1") int pageNo,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(required = false) String itemName,
        @RequestParam(required = false) Long category1,
        @RequestParam(required = false) Long category2) {
        return ApiResponseUtil.success(itemService.getItemList(pageNo, size, itemName, category1, category2));
    }

    @GetMapping("/item/{itemId}")
    public CommonResponse<List<ItemDetailListResponseDto>> getItemDetailList(@PathVariable Long itemId) {
        return ApiResponseUtil.success(itemService.getItemDetailList(itemId));
    }

    @GetMapping("/admin/item")
    public CommonResponse<PageInfoResponseDto<AdminItemResponseDto>> getAdminItemList(@RequestParam(defaultValue = "1") int pageNo,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(required = false) String itemName,
        @RequestParam(required = false) Long category1,
        @RequestParam(required = false) Long category2,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ApiResponseUtil.success(itemService.getAdminItemList(pageNo, size, itemName, category1, category2, userDetails.getUserid()));
    }

    @GetMapping("/admin/item/{itemId}")
    public CommonResponse<PageInfoResponseDto<AdminItemDetailResponseDto>> getAdminItemDetailList(@PathVariable Long itemId,
        @RequestParam(defaultValue = "1") int pageNo,
        @RequestParam(defaultValue = "10") int size,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ApiResponseUtil.success(itemService.getAdminItemDetailList(itemId, pageNo, size, userDetails.getUserid()));
    }

    @GetMapping("/category")
    public CommonResponse<List<CategoryResponseDto>> getCategoryList() {
        return ApiResponseUtil.success(itemService.getCategoryList());
    }
}
