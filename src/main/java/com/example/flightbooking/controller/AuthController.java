package com.example.flightbooking.controller;

import com.example.flightbooking.dto.request.LoginRequest;
import com.example.flightbooking.dto.request.RegisterRequest;
import com.example.flightbooking.dto.response.ApiResponse;
import com.example.flightbooking.dto.response.AuthResponse;
import com.example.flightbooking.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(
            @Valid @RequestBody RegisterRequest request) {
        AuthResponse response = authService.register(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Qeydiyyat uğurlu oldu", response));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(
            @Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Giriş uğurlu oldu", response));
    }
}