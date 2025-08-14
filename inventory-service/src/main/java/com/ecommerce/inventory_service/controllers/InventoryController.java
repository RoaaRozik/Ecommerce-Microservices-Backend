package com.ecommerce.inventory_service.controllers;

import com.ecommerce.inventory_service.entities.Inventory;
import com.ecommerce.inventory_service.services.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;

    @PostMapping("/create")
    public ResponseEntity<Inventory> createInventory(@RequestParam Long productId, @RequestParam Integer quantity) {
        Inventory inventory = inventoryService.createInventory(productId, quantity);
        return ResponseEntity.ok(inventory);
    }

    @GetMapping
    public ResponseEntity<List<Inventory>> getAllInventory() {
        return ResponseEntity.ok(inventoryService.getAllInventories());
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<Inventory> getInventoryByProductId(@PathVariable Long productId) {
        return ResponseEntity.ok(inventoryService.getInventoryByProductId(productId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventory(@PathVariable Long id) {
        inventoryService.deleteInventory(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/is-in-stock")
    public ResponseEntity<Boolean> isInStock(@RequestParam Long productId, @RequestParam int quantity) {
        boolean available = inventoryService.isInStock(productId, quantity);
        return ResponseEntity.ok(available);
    }
    @PostMapping("/reduce")
    public ResponseEntity<Void> reduceStock(@RequestParam Long productId, @RequestParam int quantity) {
        inventoryService.reduceStock(productId, quantity);
        return ResponseEntity.ok().build();
    }


}
