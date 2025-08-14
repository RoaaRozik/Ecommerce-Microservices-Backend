package com.ecommerce.inventory_service.repositories;


import com.ecommerce.inventory_service.entities.Inventory;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepositoryImplementation<Inventory,Long> {
    Optional<Inventory> findByProductId(Long productId);

}
