package com.fashionmall.item.repository;

import com.fashionmall.item.entity.Category1;
import com.fashionmall.item.entity.Category2;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface Category2Repository extends JpaRepository <Category2, Long> {
    Optional<Category2> findByIdAndCategory1Id(Long category2, Long category1);

    boolean existsByNameAndCategory1(String name, Category1 category1);
}
