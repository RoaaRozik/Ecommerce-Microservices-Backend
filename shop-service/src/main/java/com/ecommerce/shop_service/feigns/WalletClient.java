package com.ecommerce.shop_service.feigns;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;


@FeignClient(name = "wallet-service")
public interface WalletClient {
    @PostMapping("/wallet/deduct")
    boolean deductAmount(@RequestParam("userId") Long userId,
                      @RequestParam("amount") BigDecimal amount);
}
