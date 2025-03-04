package com.fashionmall.item.repository;

import com.fashionmall.item.entity.ItemColor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemColorRepository extends JpaRepository <ItemColor, Long> {
    boolean existsByColor(String color);
}
