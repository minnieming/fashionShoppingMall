package com.fashionmall.order.repository;

import com.fashionmall.order.entity.BillingKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BillingKeyRepository extends JpaRepository<BillingKey, Long>, BillingKeyRepositoryCustom {

    List<BillingKey> findByUserId(Long userId);
}
