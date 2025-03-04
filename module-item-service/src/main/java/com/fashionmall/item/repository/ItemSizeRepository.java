package com.fashionmall.item.repository;

import com.fashionmall.item.entity.ItemSize;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemSizeRepository extends JpaRepository <ItemSize, Long> {
    boolean existsBySize(String size);
}
