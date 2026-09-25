package com.example.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ecommerce.model.OrderItems;

public interface OrderItemRepository
        extends JpaRepository<OrderItems, Long> {

    List<OrderItems> findByOrderId(Long orderId);
}