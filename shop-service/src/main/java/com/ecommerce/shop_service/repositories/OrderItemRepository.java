package com.ecommerce.shop_service.repositories;

import com.ecommerce.shop_service.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
}
