package com.fashionmall.image.repository;


import com.fashionmall.image.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository <Image, Long> {

}
