package com.ecommerce.shop_service.services;

import com.ecommerce.shop_service.feigns.WalletClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class WalletServiceWrapper {

    @Autowired
    private WalletClient walletClient;
    @CircuitBreaker(name = "walletService", fallbackMethod = "walletFallback")
    public String deductAmount(Long userId, BigDecimal amount) {
        boolean result = walletClient.deductAmount(userId, amount);
        if (result) {
            return "Amount deducted successfully";
        } else {
            return "Deduction failed";
        }
    }
    public String walletFallback(Long id, BigDecimal amount, Throwable throwable) {
        return "Wallet Service is currently unavailable. Please try again later.";
    }
}
