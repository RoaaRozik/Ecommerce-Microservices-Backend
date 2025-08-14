package com.ecommerce.wallet_service.services;

import com.ecommerce.wallet_service.entities.User;
import com.ecommerce.wallet_service.entities.Wallet;
import com.ecommerce.wallet_service.repositories.TransactionRepository;
import com.ecommerce.wallet_service.repositories.UserRepository;
import com.ecommerce.wallet_service.repositories.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionService transactionService;

    public BigDecimal getWalletBalance(String email){
        Wallet wallet = getWalletByEmail(email);
        return wallet.getBalance();
    }

    public BigDecimal deposit(String email, BigDecimal amount){
        Wallet wallet = getWalletByEmail(email);
        wallet.setBalance(wallet.getBalance().add(amount));
        transactionService.createTransaction(wallet.getUser().getId(), amount, "DEPOSIT");
        walletRepository.save(wallet);
        return wallet.getBalance();
    }

    public BigDecimal withdraw(String email, BigDecimal amount){
        Wallet wallet = getWalletByEmail(email);
        if(wallet.getBalance().compareTo(amount) < 0){
            throw new RuntimeException("Insufficient balance");
        }
        wallet.setBalance(wallet.getBalance().subtract(amount));
        transactionService.createTransaction(wallet.getUser().getId(), amount, "WITHDRAW");
        walletRepository.save(wallet);
        return wallet.getBalance();
    }

    public boolean deductAmount(String email, BigDecimal amount){
        Wallet wallet = getWalletByEmail(email);
        if(wallet.getBalance().compareTo(amount) >= 0){
            wallet.setBalance(wallet.getBalance().subtract(amount));
            walletRepository.save(wallet);
            return true;
        }
        return false;
    }

    private Wallet getWalletByEmail(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Wallet wallet = user.getWallet();
        if(wallet == null) throw new RuntimeException("Wallet not found");
        return wallet;
    }
}
