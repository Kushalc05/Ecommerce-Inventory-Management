package com.example.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ecommerce.model.OrderItems;

public interface OrderItemRepository extends JpaRepository<OrderItems, Long> {
}