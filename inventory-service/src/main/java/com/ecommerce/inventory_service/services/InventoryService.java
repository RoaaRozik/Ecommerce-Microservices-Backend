package com.ecommerce.inventory_service.services;

import com.ecommerce.inventory_service.entities.Inventory;
import com.ecommerce.inventory_service.repositories.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;

    public Inventory createInventory(Long productId, int quantity) {
        Inventory inventory = new Inventory(productId, quantity);
        return inventoryRepository.save(inventory);
    }

    public List<Inventory> getAllInventories() {
        return inventoryRepository.findAll();
    }

    public Inventory getInventoryByProductId(Long productId) {
        return inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Inventory not found for this productId"));
    }

    public void deleteInventory(Long id) {
        inventoryRepository.deleteById(id);
    }

    public boolean isInStock(Long productId, int quantity) {
        Optional<Inventory> optionalInventory = inventoryRepository.findByProductId(productId);
        if (optionalInventory.isPresent()) {
            Inventory inventory = optionalInventory.get();
            return inventory.getQuantity() >= quantity;
        }
        return false;
    }
    public void reduceStock(Long productId, int quantity) {
        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Product not found in inventory"));

        if (inventory.getQuantity() < quantity) {
            throw new RuntimeException("Not enough stock to fulfill the order");
        }

        inventory.setQuantity(inventory.getQuantity() - quantity);
        inventoryRepository.save(inventory);
    }
}
