package com.example.order.repository;

import com.example.order.model.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<CartItems, Long> {
    CartItems findByUserIdAndProductId(String userId , String productId);

    List<CartItems> findByUserId(String userId);

    void deleteByUserId(String userId);
}
