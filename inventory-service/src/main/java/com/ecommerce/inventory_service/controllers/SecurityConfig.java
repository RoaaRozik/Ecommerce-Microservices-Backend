package com.ecommerce.inventory_service.controllers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())        // modern lambda style
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()   // allow all endpoints
                )
                .build();
    }
}

