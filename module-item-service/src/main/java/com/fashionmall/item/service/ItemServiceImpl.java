package com.fashionmall.item.service;

import com.fashionmall.common.exception.CustomException;
import com.fashionmall.common.exception.ErrorResponseCode;
import com.fashionmall.common.moduleApi.dto.*;
import com.fashionmall.common.moduleApi.enums.ImageTypeEnum;
import com.fashionmall.common.moduleApi.enums.ItemDiscountTypeEnum;
import com.fashionmall.common.moduleApi.enums.ReferenceTypeEnum;
import com.fashionmall.common.moduleApi.util.ModuleApiUtil;
import com.fashionmall.common.response.PageInfoResponseDto;
import com.fashionmall.item.dto.request.ItemDiscountRequestDto;
import com.fashionmall.item.dto.request.ItemRequestDto;
import com.fashionmall.item.dto.request.ItemUpdateRequestDto;
import com.fashionmall.item.dto.response.*;
import com.fashionmall.item.entity.*;
import com.fashionmall.item.enums.StatusEnum;
import com.fashionmall.item.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "itemService")
@Transactional(readOnly = true)
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ItemColorRepository itemColorRepository;
    private final ItemSizeRepository itemSizeRepository;
    private final ItemDetailRepository itemDetailRepository;
    private final Category1Repository category1Repository;
    private final Category2Repository category2Repository;
    private final ItemCategoryMappingRepository itemCategoryMappingRepository;
    private final ItemDiscountRepository itemDiscountRepository;
    private final ModuleApiUtil moduleApiUtil;

    @Override
    public List<CategoryResponseDto> getCategoryList() {
        return itemRepository.getCategoryList();
    }

    @Override
    public PageInfoResponseDto<ItemListResponseDto> getItemList(int pageNo, int size, String itemName, Long category1, Long category2) {
        PageRequest pageRequest = PageRequest.of(pageNo -1, size);
        return itemRepository.itemListPageNation (pageRequest, itemName, category1, category2);
    }

    @Override
    @Transactional
    public List<LikeItemListResponseDto> getItemInfoApi(Long itemId, Long userId) {
        return itemRepository.getItemInfo(itemId, userId);
    }

    @Override
    public List<ItemDetailListResponseDto> getItemDetailList(Long itemId) {

        Item item = itemRepository.findById(itemId).orElseThrow(() -> new CustomException(ErrorResponseCode.WRONG_ITEM_ID));

        List<ItemDetailListResponseDto.ItemDetailInfo> itemDetailInfos = item.getItemDetails().stream()
                .map(itemDetail -> ItemDetailListResponseDto.ItemDetailInfo.builder()
                        .id(itemDetail.getId())
                        .color(itemDetail.getItemColor().getColor())
                        .size(itemDetail.getItemSize().getSize())
                        .name(itemDetail.getName())
                        .price(itemDetail.getPrice())
                        .discountPrice(calculateDiscountPrice(item, itemDetail))
                        .quantity(itemDetail.getQuantity())
                        .imageId(itemDetail.getImageId())
                        .imageUrl(itemDetail.getImageUrl())
                        .build())
                .collect(Collectors.toList());

        List<ItemDetailListResponseDto.ItemDiscountInfo> itemDiscountInfos = item.getItemDiscounts().isEmpty() ?
                List.of(ItemDetailListResponseDto.ItemDiscountInfo.builder()
                        .type(null)
                        .value(0)
                        .build()) :
                item.getItemDiscounts().stream()
                        .map(itemDiscount -> ItemDetailListResponseDto.ItemDiscountInfo.builder()
                                .type(itemDiscount.getType())
                                .value(itemDiscount.getValue())
                                .build())
                        .collect(Collectors.toList());

        ItemDetailListResponseDto.ItemInfo itemInfo = ItemDetailListResponseDto.ItemInfo.builder()
                .id(item.getId())
                .name(item.getName())
                .imageUrl(item.getImageUrl())
                .itemDetailInfo(itemDetailInfos)
                .itemDiscountInfo(itemDiscountInfos)
                .build();

        ItemDetailListResponseDto itemDetailListResponseDto = ItemDetailListResponseDto.builder()
                .itemInfo(itemInfo)
                .build();

        return List.of(itemDetailListResponseDto);
    }

    private int calculateDiscountPrice(Item item, ItemDetail itemDetail) {
        int originalPrice = itemDetail.getPrice();
        int discountPrice = originalPrice; // 원래 가격으로 초기화

        // Item의 할인 정보가 있을 경우 할인 적용
        for (ItemDiscount itemDiscount : item.getItemDiscounts()) {
            discountPrice = discountPrice(originalPrice, itemDiscount.getStatus(), itemDiscount.getType(), itemDiscount.getValue());
        }

        return discountPrice; // 계산된 할인 가격 반환
    }

    public int discountPrice (int price, StatusEnum status, ItemDiscountTypeEnum type, int value) {
        int discountPrice = 0;
        if (status == StatusEnum.ACTIVATED && type == ItemDiscountTypeEnum.RATE) {
            discountPrice = price - (price/value);
        } else if (status == StatusEnum.ACTIVATED && type == ItemDiscountTypeEnum.AMOUNT) {
            discountPrice = price - value;
        } else {
            discountPrice = price;
        }
        return discountPrice;
    }

    @Override
    @Transactional
    public ItemResponseDto createItem(ItemRequestDto itemRequestDto, Long workerId) {

        moduleApiUtil.confirmUserInfoApi(workerId);

        // 상품 등록
        Item item = Item.builder()
                .workerId(workerId)
                .imageId(0L)
                .imageUrl("무슨 내용이 들어갈까요?")
                .name(itemRequestDto.getName())
                .status(itemRequestDto.getState())
                .build();
        itemRepository.save(item);

        Map<Long, String> mainImage = uploadImageApi(itemRequestDto.getImageFileName(), item.getId(), ReferenceTypeEnum.ITEM, ImageTypeEnum.MAIN);

        Long imageId = mainImage.keySet().iterator().next();
        String imageUrl = mainImage.get(imageId);

        item.updateImageId(imageId);
        item.updateImageUrl(imageUrl);
        itemRepository.save(item);

        // 카테고리 등록
        ItemRequestDto.CategoryRequestDto categoryRequestDto = itemRequestDto.getCategoryRequestDtoList();

        Category1 category1 = findCategory1(categoryRequestDto.getCategory1Id());
        Category2 category2 = findCategory2(categoryRequestDto.getCategory2Id(), categoryRequestDto.getCategory1Id());

        ItemCategoryMapping itemCategoryMapping = ItemCategoryMapping.builder()
                .item(item)
                .category1(category1)
                .category2Id(category2.getId())
                .build();
        itemCategoryMappingRepository.save(itemCategoryMapping);

        // 상품 상세 등록
        for (ItemRequestDto.ItemDetailRequestDto itemDetailRequestDto : itemRequestDto.getItemDetailRequestDtoList()) {

            ItemSize itemSize = findSizeId(itemDetailRequestDto.getSizeId());
            ItemColor itemColor = findColorId(itemDetailRequestDto.getColorId());

            ItemDetail itemDetail = ItemDetail.builder()
                    .item(item)
                    .itemColor(itemColor)
                    .itemSize(itemSize)
                    .imageId(0L)
                    .imageUrl("서브 이미지 url")
                    .name(itemDetailRequestDto.getName())
                    .price(itemDetailRequestDto.getPrice())
                    .quantity(itemDetailRequestDto.getQuantity())
                    .status(itemDetailRequestDto.getStatus())
                    .build();
            itemDetailRepository.save(itemDetail);

            Map<Long, String> subImage = uploadImageApi(itemDetailRequestDto.getImageFileName(), itemDetail.getId(), ReferenceTypeEnum.ITEM, ImageTypeEnum.DESCRIPTION);

            Long subImageId = subImage.keySet().iterator().next();
            String subImageUrl = subImage.get(subImageId);

            itemDetail.updateImageId(subImageId);
            itemDetail.updateImageUrl(subImageUrl);

            itemDetailRepository.save(itemDetail);

            item.getItemDetails().add(itemDetail);
        }

        return ItemResponseDto.from(item, imageUrl);
    }

    @Override
    public PageInfoResponseDto<AdminItemResponseDto> getAdminItemList(int pageNo, int size, String itemName, Long category1, Long category2, Long workerId) {
        moduleApiUtil.confirmUserInfoApi(workerId);
        PageRequest pageRequest = PageRequest.of(pageNo -1, size);
        return itemRepository.adminItemListPageNation (pageRequest, itemName, category1, category2);
    }

    @Override
    public PageInfoResponseDto<AdminItemDetailResponseDto> getAdminItemDetailList(Long itemId, int pageNo, int size, Long workerId) {
        moduleApiUtil.confirmUserInfoApi(workerId);
        PageRequest pageRequest = PageRequest.of(pageNo -1, size);
        return itemRepository.adminItemDetailListPageNation (itemId, pageRequest);
    }

    private ItemColor findColorId(Long colorId) {
        return itemColorRepository.findById(colorId).orElseThrow(()-> new CustomException(ErrorResponseCode.WRONG_ITEM_ID));
    }

    private ItemSize findSizeId(Long sizeId) {
        return itemSizeRepository.findById(sizeId).orElseThrow(()-> new CustomException(ErrorResponseCode.WRONG_ITEM_ID));
    }

    private Category1 findCategory1(Long category1) {
        return category1Repository.findById (category1).orElseThrow(()-> new CustomException(ErrorResponseCode.WRONG_CATEGORY_ID));
    }

    private Category2 findCategory2(Long category2, Long category1) {
        return category2Repository.findByIdAndCategory1Id(category2, category1).orElseThrow(() -> new CustomException(ErrorResponseCode.WRONG_CATEGORY_ID));
    }

    private Map<Long, String> uploadImageApi(String fileName, Long referenceId, ReferenceTypeEnum referenceType, ImageTypeEnum imageType) {

        ImageUploadDto imageUploadDto = ImageUploadDto.builder()
                .fileName(fileName)
                .referenceId(referenceId)
                .referenceType(referenceType)
                .imageType(imageType)
                .build();
        List<ImageUploadDto> imageUploadDtoList = new ArrayList<>();
        imageUploadDtoList.add(imageUploadDto);

        Map<Long, String> response = moduleApiUtil.uploadImageApi(imageUploadDtoList);

        return response;
    }

    @Override
    @Transactional
    public ItemDiscountResponseDto createItemDiscount(ItemDiscountRequestDto itemDiscountRequestDto, Long workerId) {

        moduleApiUtil.confirmUserInfoApi(workerId);

        Item item = findByIdAndWorkerId(itemDiscountRequestDto.getId(), workerId);

        List<ItemDiscount> itemDiscounts = new ArrayList<>();

        for (ItemDiscountRequestDto.ItemDiscountDtos itemDiscountDtos : itemDiscountRequestDto.getItemDiscountRequestDtoList()) {

            validateItemDiscountValue(itemDiscountDtos.getType(), itemDiscountDtos.getValue());

            ItemDiscount itemDiscount = ItemDiscount.builder()
                    .item(item)
                    .type(itemDiscountDtos.getType())
                    .value(itemDiscountDtos.getValue())
                    .build();
            itemDiscounts.add(itemDiscountRepository.save(itemDiscount));
        }

        return ItemDiscountResponseDto.fromItemDiscounts(item.getId(), itemDiscounts);
    }

    @Override
    @Transactional
    public ItemUpdateResponseDto updateItem(Long itemId, ItemUpdateRequestDto itemUpdateRequestDto, Long workerId) {

        moduleApiUtil.confirmUserInfoApi(workerId);

        Item item = findByIdAndWorkerId(itemId, workerId);

        List<ItemCategoryMapping> updatedCategoryMappings = new ArrayList<>();
        List<ItemDetail> updatedItemDetails = new ArrayList<>();
        List<ItemDiscount> updatedItemDiscounts = new ArrayList<>();

        if (!itemUpdateRequestDto.getName().isEmpty()) {
            item.updateItemName(itemUpdateRequestDto.getName());
        }

        item.updateItemState(itemUpdateRequestDto.getState());

        if (!itemUpdateRequestDto.getImageFileName().isEmpty()) {

            moduleApiUtil.deleteImageApi(item.getImageId());

            Map<Long, String> updateImage = uploadImageApi(itemUpdateRequestDto.getImageFileName(), item.getId(), ReferenceTypeEnum.ITEM, ImageTypeEnum.MAIN);

            Long imageId = updateImage.keySet().iterator().next();
            String imageUrl = updateImage.get(imageId);

            item.updateImageId(imageId);
            item.updateImageUrl(imageUrl);
        }

        // 카테고리
        for (ItemUpdateRequestDto.CategoryRequestDto categoryDto : itemUpdateRequestDto.getCategoryRequestDtoList()) {

            if (categoryDto.getId() != null) {
                ItemCategoryMapping itemCategoryMapping = itemCategoryMappingRepository.findByIdAndItemId(categoryDto.getId(), itemId)
                        .orElseThrow(() -> new CustomException(ErrorResponseCode.WRONG_ITEM_ID));

                if (categoryDto.getCategory1Id() != null) {
                    Category1 category1 = findCategory1(categoryDto.getCategory1Id());

                    if (categoryDto.getCategory2Id() != null) {
                        Category2 category2 = findCategory2(categoryDto.getCategory2Id(), categoryDto.getCategory1Id());
                        itemCategoryMapping.updateCategories(category1, category2.getId());
                    } else {
                        itemCategoryMapping.updateCategory1(category1);
                    }
                }
                if (categoryDto.getCategory2Id() != null) {
                    Category2 category2 = findCategory2(categoryDto.getCategory2Id(), categoryDto.getCategory1Id());
                    itemCategoryMapping.updateCategory2(category2.getId());
                }
                updatedCategoryMappings.add(itemCategoryMapping);
            }
        }

        // Itemdetail
        for (ItemUpdateRequestDto.ItemDetailRequestDto itemDetailDto : itemUpdateRequestDto.getItemDetailRequestDtoList()) {

            if (itemDetailDto.getId() != null) {
                ItemDetail itemDetail = itemDetailRepository.findByIdAndItemId(itemDetailDto.getId(), itemId)
                        .orElseThrow(() -> new CustomException(ErrorResponseCode.WRONG_ITEM_ID));

                if (itemDetailDto.getName() != null) {
                    itemDetail.updateName(itemDetailDto.getName());
                }

                itemDetail.updateState(itemDetailDto.getStatus());

                if (!itemDetailDto.getImageFileName().isEmpty()) {

                    moduleApiUtil.deleteImageApi(itemDetail.getImageId());

                    Map<Long, String> updateImage = uploadImageApi(itemDetailDto.getImageFileName(), itemDetail.getImageId(), ReferenceTypeEnum.ITEM, ImageTypeEnum.DESCRIPTION);

                    Long imageId = updateImage.keySet().iterator().next();
                    String imageUrl = updateImage.get(imageId);

                    itemDetail.updateImageId(imageId);
                    itemDetail.updateImageUrl(imageUrl);
                }

                if (itemDetailDto.getPrice() > 0) {
                    itemDetail.updatePrice(itemDetailDto.getPrice());
                }

                if (itemDetailDto.getQuantity() > 0) {
                    itemDetail.updateQuantity(itemDetailDto.getQuantity());
                }

                if (itemDetailDto.getSizeId() != 0) {
                    ItemSize itemSize = findSizeId(itemDetailDto.getSizeId());
                    itemDetail.updateSize(itemSize);
                }

                if (itemDetailDto.getColorId() != 0) {
                    ItemColor itemColor = findColorId(itemDetailDto.getColorId());
                    itemDetail.updateColor(itemColor);
                }
                updatedItemDetails.add(itemDetail);
            }
        }

        // ItemDiscount
        for (ItemUpdateRequestDto.ItemDiscountRequestDto itemDiscountDto : itemUpdateRequestDto.getItemDiscountRequestDtoList()) {

            if (itemDiscountDto.getId() != null) {
                ItemDiscount itemDiscount = itemDiscountRepository.findByIdAndItemId(itemDiscountDto.getId(), itemId)
                        .orElseThrow(() -> new CustomException(ErrorResponseCode.WRONG_ITEM_DISCOUNT_ID));

                if (itemDiscountDto.getType() != null) {
                    itemDiscount.updateItemDiscountType(itemDiscountDto.getType());
                }

                if (itemDiscountDto.getValue() > 0) {
                    validateItemDiscountValue(itemDiscountDto.getType(), itemDiscountDto.getValue());
                    itemDiscount.updateValue(itemDiscountDto.getValue());
                }

                if (itemDiscountDto.getStatus() != null) {

                    int activate = 0;
                    itemDiscount.updateStatus(itemDiscountDto.getStatus());

                    for (ItemDiscount itemDiscounts : item.getItemDiscounts()) {
                        if (itemDiscounts.getStatus().equals(StatusEnum.ACTIVATED)) {
                            activate++;
                        }
                    }
                    if (activate > 1) {
                        throw new CustomException(ErrorResponseCode.DUPLICATE_DISCOUNT_STATUS);
                    }
                }
                updatedItemDiscounts.add(itemDiscount);
            }
        }
        return ItemUpdateResponseDto.from(item, updatedCategoryMappings, updatedItemDetails, updatedItemDiscounts);
    }

    @Override
    @Transactional
    public Long deleteItem(Long itemId, Long workerId) {

        moduleApiUtil.confirmUserInfoApi(workerId);

        Item item = findByIdAndWorkerId(itemId, workerId);

        moduleApiUtil.deleteImageApi(item.getImageId());

        for (ItemDetail itemDetail : item.getItemDetails()) {
            moduleApiUtil.deleteImageApi(itemDetail.getImageId());
        }

        itemRepository.deleteById(itemId);

        return workerId;
    }

    private Item findByIdAndWorkerId(Long itemId, Long workerId) {
        return itemRepository.findByIdAndWorkerId(itemId, workerId).orElseThrow(() -> new CustomException(ErrorResponseCode.WRONG_ITEM_ID));
    }

    private void validateItemDiscountValue(ItemDiscountTypeEnum type, int value) {

        if (type == ItemDiscountTypeEnum.RATE) {
            if (value < 0 || value > 100) {
                throw new CustomException(ErrorResponseCode.WRONG_RATE);
            }
        } else if (type == ItemDiscountTypeEnum.AMOUNT) {
            if (value < 0) {
                throw new CustomException(ErrorResponseCode.WRONG_AMOUNT);
            }
        }
    }

    @Override
    @Transactional
    public Map<Long, Integer> getItemStockApi(Long itemDetailId, Long workerId) {

        ItemDetail itemDetail = findByItemDetailIdAndWorkerId(itemDetailId, workerId);

        Map<Long, Integer> itemStock = new HashMap<>();
        itemStock.put(itemDetail.getId(), itemDetail.getQuantity());

        return itemStock;
    }

    @Override
    @Transactional
    public String getItemNameApi(Long itemId) {

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new CustomException(ErrorResponseCode.WRONG_ITEM_ID));

        return item.getName();
    }

    @Override
    @Transactional
    public List<ItemDetailInfoDto> getItemDetailInfoApi(List<Long> itemDetailId) {

        List<ItemDetailInfoDto> itemDetailInfoDtoList = new ArrayList<>();

        for (Long id : itemDetailId) {
            ItemDetail itemDetail = itemDetailRepository.findById(id).orElseThrow(() -> new CustomException(ErrorResponseCode.WRONG_ITEM_DETAIL_ID));
            Item item = itemRepository.findById(itemDetail.getItem().getId()).orElseThrow(() -> new CustomException(ErrorResponseCode.WRONG_ITEM_ID));
            List<ItemDiscount> itemDiscounts = item.getItemDiscounts();

            String itemDetailName = itemDetail.getName();
            int price = itemDetail.getPrice();
            String imageUrl = itemDetail.getImageUrl();

            if (itemDiscounts.isEmpty()) {
                String discountType = "none";
                int itemDiscountValue = price;
                ItemDetailInfoDto itemDetailInfoDto = new ItemDetailInfoDto(itemDetail.getId(), itemDetailName, price, itemDiscountValue, discountType, imageUrl);
                itemDetailInfoDtoList.add(itemDetailInfoDto);
            } else {
                for (ItemDiscount itemDiscount : itemDiscounts) {

                    ItemPriceNameDto itemPriceNameDto = itemRepository.getDiscountPrice(itemDetail.getId(), itemDiscount.getId());

                    int itemDiscountValue = itemPriceNameDto.getPrice();
                    String discountType = itemDiscount.getType().name();

                    ItemDetailInfoDto itemDetailInfoDto = new ItemDetailInfoDto(itemDetail.getId(), itemDetailName, price, itemDiscountValue, discountType, imageUrl);
                    itemDetailInfoDtoList.add(itemDetailInfoDto);
                }
            }
        }

        return itemDetailInfoDtoList;
    }

    @Override
    @Transactional
    public void deductItemStockApi(List<OrderItemDto> orderItemDto, Long workerId) {

        for (OrderItemDto orderItemDtoList : orderItemDto) {
            ItemDetail itemDetail = findByItemDetailIdAndWorkerId(orderItemDtoList.getItemDetailId(), workerId);

            if (itemDetail.getQuantity() >= orderItemDtoList.getQuantity()) {
                itemDetail.deductQuantity(orderItemDtoList.getQuantity());
            } else {
                throw new CustomException(ErrorResponseCode.BAD_DEDUCT_QUANTITY);
            }
        }
    }

    @Override
    @Transactional
    public void restoreItemStockApi(List<OrderItemDto> orderItemDto, Long workerId){

        for (OrderItemDto orderItemDtoList : orderItemDto) {

            ItemDetail itemDetail = findByItemDetailIdAndWorkerId(orderItemDtoList.getItemDetailId(), workerId);

            if (orderItemDtoList.getQuantity() > 0) {
                itemDetail.restoreQuantity(orderItemDtoList.getQuantity());
            } else {
                throw new CustomException(ErrorResponseCode.BAD_RESTORE_QUANTITY);
            }
        }

    }

    @Override
    @Transactional
    public ItemDetailResponseDto getItemDetailApi(Long itemDetailId, Long workerId) {

        ItemDetail itemDetail = findByItemDetailIdAndWorkerId(itemDetailId, workerId);
        Item item = itemRepository.findByItemDetails_id(itemDetail.getId()).orElseThrow(() -> new CustomException(ErrorResponseCode.WRONG_ITEM_DETAIL_ID));

        return ItemDetailResponseDto.builder()
                .name(itemDetail.getName())
                .price(itemDetail.getPrice())
                .imageId(item.getImageId())
                .build();
    }

    private ItemDetail findByItemDetailIdAndWorkerId(Long itemDetailId, Long workerId) {
        return itemDetailRepository.findByIdAndItem_WorkerId(itemDetailId, workerId)
                .orElseThrow(() -> new CustomException(ErrorResponseCode.WRONG_ITEM_DETAIL_ID));
    }

    @Override
    @Transactional
    public List<ItemPriceNameDto> getItemPriceAndNameApi(List<Long> itemDetailId, Long workerId) {

        List<ItemPriceNameDto> itemPriceNameDtos = new ArrayList<>();

        for (Long itemDetailIds : itemDetailId) {

            ItemDetail itemDetail = itemDetailRepository.findById(itemDetailIds).orElseThrow(() -> new CustomException(ErrorResponseCode.WRONG_ITEM_DETAIL_ID));
            Item item = itemRepository.findById(itemDetail.getItem().getId()).orElseThrow(() -> new CustomException(ErrorResponseCode.WRONG_ITEM_ID));
            List<ItemDiscount> itemDiscounts = item.getItemDiscounts();

            for (ItemDiscount itemDiscount : itemDiscounts) {
                ItemPriceNameDto itemPriceNameDto = itemRepository.getDiscountPrice(itemDetail.getId(), itemDiscount.getId());
                itemPriceNameDtos.add(itemPriceNameDto);
            }
        }

        return itemPriceNameDtos;
    }

}
