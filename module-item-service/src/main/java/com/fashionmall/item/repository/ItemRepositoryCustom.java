package com.fashionmall.item.repository;

import com.fashionmall.common.moduleApi.dto.LikeItemListResponseDto;
import com.fashionmall.common.moduleApi.dto.ItemPriceNameDto;
import com.fashionmall.common.response.PageInfoResponseDto;
import com.fashionmall.item.dto.response.AdminItemDetailResponseDto;
import com.fashionmall.item.dto.response.AdminItemResponseDto;
import com.fashionmall.item.dto.response.ItemDetailListResponseDto;
import com.fashionmall.item.dto.response.ItemListResponseDto;
import com.fashionmall.item.dto.response.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemRepositoryCustom {

    List<LikeItemListResponseDto> getItemInfo (Long itemId, Long userId);

    PageInfoResponseDto <ItemListResponseDto> itemListPageNation (Pageable pageable, String itemName, Long category1, Long category2);

    PageInfoResponseDto <AdminItemResponseDto> adminItemListPageNation (Pageable pageable, String itemName, Long category1, Long category2);

    PageInfoResponseDto <AdminItemDetailResponseDto> adminItemDetailListPageNation (Long itemId, Pageable pageable);

    ItemPriceNameDto getDiscountPrice (Long itemDetailId, Long itemDiscountId);

    List<CategoryResponseDto> getCategoryList();
}
