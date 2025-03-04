package com.fashionmall.user.repository;

import com.fashionmall.user.entity.Favorite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FavoriteRepository extends JpaRepository <Favorite, Long> {
    Favorite findByItemId(Long itemId);

   int countByUserId(Long userId);

}
