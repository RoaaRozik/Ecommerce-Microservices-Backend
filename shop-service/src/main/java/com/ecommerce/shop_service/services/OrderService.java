package com.ecommerce.shop_service.services;

import com.ecommerce.shop_service.entities.CartItem;
import com.ecommerce.shop_service.entities.Order;
import com.ecommerce.shop_service.entities.OrderItem;
import com.ecommerce.shop_service.entities.OrderStatus;
import com.ecommerce.shop_service.feigns.InventoryClient;
import com.ecommerce.shop_service.feigns.WalletClient;
import com.ecommerce.shop_service.repositories.CartRepository;
import com.ecommerce.shop_service.repositories.OrderItemRepository;
import com.ecommerce.shop_service.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;


    @Autowired
    private WalletServiceWrapper walletServiceWrapper;
    @Autowired
    private InventoryServiceWrapper inventoryServiceWrapper;


    public Order placeOrder(Long userId, List<OrderItem> items) {
        for (OrderItem item : items) {
            boolean inStock = inventoryServiceWrapper.isInStock(item.getProductId(), item.getQuantity());
            if (!inStock) {
                throw new RuntimeException("Product with ID " + item.getProductId() + " is out of stock");
            }
        }


        BigDecimal total = BigDecimal.ZERO;
        for (OrderItem item : items) {
            BigDecimal itemTotal = item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            total = total.add(itemTotal);
        }

        String result = walletServiceWrapper.deductAmount(userId, total);
        if (!"Amount deducted successfully".equals(result)) {
            throw new RuntimeException("Insufficient wallet balance for user");
        }

        for (OrderItem item : items) {
            inventoryServiceWrapper.reduceStock(item.getProductId(), item.getQuantity());
        }

        Order order = new Order(userId, items, total, OrderStatus.PLACED);
        for (OrderItem item : items) {
            item.setOrder(order);
        }

        return orderRepository.save(order);
    }

    public List<Order> getOrdersByUser(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }
}

