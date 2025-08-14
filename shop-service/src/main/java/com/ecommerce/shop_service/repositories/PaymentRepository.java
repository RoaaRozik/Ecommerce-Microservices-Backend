package com.ecommerce.shop_service.repositories;

import com.ecommerce.shop_service.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,String> {
    List<Payment> findByUserId(Long userId);
    Optional<Payment> findByOrderId(Long orderId);
}
