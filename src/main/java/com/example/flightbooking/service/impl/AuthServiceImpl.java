package com.example.flightbooking.service.impl;

import com.example.flightbooking.dto.request.LoginRequest;
import com.example.flightbooking.dto.request.RegisterRequest;
import com.example.flightbooking.dto.response.AuthResponse;
import com.example.flightbooking.entity.User;
import com.example.flightbooking.enums.RoleName;
import com.example.flightbooking.exception.UserAlreadyExistsException;
import com.example.flightbooking.repository.UserRepository;
import com.example.flightbooking.security.JwtTokenProvider;
import com.example.flightbooking.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Bu email artıq mövcuddur: " + request.getEmail());
        }

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhone(request.getPhone());
        user.setRoles(Set.of(RoleName.ROLE_USER));

        userRepository.save(user);

        String token = jwtTokenProvider.generateToken(user.getEmail());
        return new AuthResponse(token, user.getEmail(), "ROLE_USER");
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();

        String token = jwtTokenProvider.generateToken(user.getEmail());
        String role = user.getRoles().iterator().next().name();
        return new AuthResponse(token, user.getEmail(), role);
    }
}