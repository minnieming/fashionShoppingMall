package com.fashionmall.item.repository;

import com.fashionmall.item.entity.ItemDiscount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemDiscountRepository extends JpaRepository <ItemDiscount, Long> {
    Optional<ItemDiscount> findByIdAndItemId(Long id, Long itemId);
}
