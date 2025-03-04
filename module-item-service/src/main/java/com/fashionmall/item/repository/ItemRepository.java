package com.fashionmall.item.repository;

import com.fashionmall.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemRepository extends JpaRepository <Item, Long>, ItemRepositoryCustom {
    Optional<Item> findByIdAndWorkerId (Long itemId, Long workerId);

    Optional<Item> findByItemDetails_id(Long itemDetailId);
}
