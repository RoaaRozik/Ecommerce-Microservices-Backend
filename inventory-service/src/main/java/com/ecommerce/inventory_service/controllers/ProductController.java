package com.ecommerce.inventory_service.controllers;


import com.ecommerce.inventory_service.entities.Product;
import com.ecommerce.inventory_service.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        return ResponseEntity.ok(productService.createProduct(product));
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        return ResponseEntity.ok(productService.updateProduct(id, updatedProduct));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully");
    }

    @GetMapping("/stock/{productId}/{quantity}")
    public ResponseEntity<Boolean> isInStock(@PathVariable Long productId, @PathVariable int quantity) {
        return ResponseEntity.ok(productService.isInStock(productId, quantity));
    }

    @PostMapping("/reduce/{productId}/{quantity}")
    public ResponseEntity<Void> reduceStock(@PathVariable Long productId, @PathVariable int quantity) {
        productService.reduceStock(productId, quantity);
        return ResponseEntity.ok().build();
    }
}