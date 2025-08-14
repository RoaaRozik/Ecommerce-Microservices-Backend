package com.ecommerce.shop_service.feigns;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "inventory-service")
public interface InventoryClient {
    @GetMapping("/inventory/is-in-stock")
    boolean isInStock(@RequestParam("productId") Long productId,
                      @RequestParam("quantity") int quantity);
    @PutMapping("/inventory/reduce")
    void reduceStock(@RequestParam("productId") Long productId,
                     @RequestParam("quantity") int quantity);
}
