package com.fashionmall.order.repository;

import com.fashionmall.order.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders, Long>, OrdersRepositoryCustom {
}