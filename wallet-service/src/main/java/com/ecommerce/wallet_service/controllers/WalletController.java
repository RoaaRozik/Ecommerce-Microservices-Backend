package com.ecommerce.wallet_service.controllers;


import com.ecommerce.wallet_service.entities.User;
import com.ecommerce.wallet_service.entities.Wallet;
import com.ecommerce.wallet_service.repositories.WalletRepository;
import com.ecommerce.wallet_service.services.WalletService;
import jakarta.persistence.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/wallets")
public class WalletController {

    @Autowired
    private WalletService walletService;


    @GetMapping("/balance")
    public ResponseEntity<BigDecimal> getWalletBalance(@AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails) {
        BigDecimal balance = walletService.getWalletBalance(userDetails.getUsername());
        return ResponseEntity.ok(balance);
    }

    @PostMapping("/deposit")
    public ResponseEntity<BigDecimal> deposit(@AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails,
                                              @RequestBody BigDecimal amount) {
        BigDecimal balance = walletService.deposit(userDetails.getUsername(), amount);
        return ResponseEntity.ok(balance);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<BigDecimal> withdraw(@AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails,
                                               @RequestBody BigDecimal amount) {
        BigDecimal balance = walletService.withdraw(userDetails.getUsername(), amount);
        return ResponseEntity.ok(balance);
    }

    @PostMapping("/deduct")
    public ResponseEntity<String> deductAmount(@AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails,
                                               @RequestParam BigDecimal amount) {
        boolean success = walletService.deductAmount(userDetails.getUsername(), amount);
        if (success) return ResponseEntity.ok("Amount deducted successfully");
        return ResponseEntity.badRequest().body("Insufficient balance or wallet not found");
    }
}