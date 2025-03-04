package com.fashionmall.item.repository;

import com.fashionmall.item.entity.ItemCategoryMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemCategoryMappingRepository extends JpaRepository <ItemCategoryMapping, Long> {
    Optional<ItemCategoryMapping> findByIdAndItemId(Long id, Long itemId);
}
