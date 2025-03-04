package com.fashionmall.user.repository;

import com.fashionmall.user.entity.DeliveryAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeliveryAddressRepository extends JpaRepository <DeliveryAddress, Long> {
    List<DeliveryAddress> findAllByUserId(Long userId);
}
