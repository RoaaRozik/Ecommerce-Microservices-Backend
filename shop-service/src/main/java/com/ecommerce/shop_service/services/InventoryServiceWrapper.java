package com.ecommerce.shop_service.services;

import com.ecommerce.shop_service.feigns.InventoryClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class InventoryServiceWrapper {

    @Autowired
    private InventoryClient inventoryClient;

    @CircuitBreaker(name = "inventoryService", fallbackMethod = "handleInStockFallback")
    public boolean isInStock(Long productId, int quantity) {
        return inventoryClient.isInStock(productId, quantity);
    }

    @CircuitBreaker(name = "inventoryService", fallbackMethod = "handleReduceStockFallback")
    public void reduceStock(Long productId, int quantity) {
        inventoryClient.reduceStock(productId, quantity);
    }

    // Fallbacks
    public boolean handleInStockFallback(Long productId, int quantity, Throwable throwable) {
        System.out.println("[InventoryService] Failed isInStock call for productId " + productId + ": " + throwable.getMessage());
        return false;
    }

    public void handleReduceStockFallback(Long productId, int quantity, Throwable throwable) {
        System.out.println("[InventoryService] Failed reduceStock call for productId " + productId + ": " + throwable.getMessage());
    }
}
