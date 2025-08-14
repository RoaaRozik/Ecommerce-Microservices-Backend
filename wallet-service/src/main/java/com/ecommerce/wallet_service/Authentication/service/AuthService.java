package com.ecommerce.wallet_service.Authentication.service;

import com.ecommerce.wallet_service.Authentication.model.AuthResponse;
import com.ecommerce.wallet_service.entities.LoginRequest;
import com.ecommerce.wallet_service.entities.User;
import com.ecommerce.wallet_service.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        UserDetails userDetails = userService.loadUserByUsername(request.getEmail());
        User user = userService.getUserByEmail(request.getEmail()); // fetch full user
        String token = jwtUtil.generateToken(userDetails, user.getId()); // pass userId

        return new AuthResponse(token);
    }
}

