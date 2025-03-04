package com.fashionmall.item.repository;

import com.fashionmall.item.entity.Item;
import com.fashionmall.item.entity.ItemDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemDetailRepository extends JpaRepository <ItemDetail, Long> {
    Optional<ItemDetail> findByIdAndItemId(Long id, Long itemId);
    Optional<ItemDetail> findByIdAndItem_WorkerId(Long itemDetailId, Long workerId);

}
