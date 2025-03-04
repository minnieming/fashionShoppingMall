package com.fashionmall.item.repository;

import com.fashionmall.item.entity.Category1;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface Category1Repository extends JpaRepository <Category1, Long> {
    boolean existsByName(String name);
}
