package com.fashionmall.image.repository;

import com.fashionmall.image.entity.ImageMapping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageMappingRepository extends JpaRepository <ImageMapping, Long> {
}
