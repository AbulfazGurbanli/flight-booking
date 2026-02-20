package com.example.flightbooking.service;

import com.example.flightbooking.dto.request.LoginRequest;
import com.example.flightbooking.dto.request.RegisterRequest;
import com.example.flightbooking.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}