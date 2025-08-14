package com.ecommerce.shop_service.repositories;

import com.ecommerce.shop_service.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<CartItem,Long> {
    List<CartItem> findByUserId(Long userId);
    void deleteByUserId(Long userId);

}
