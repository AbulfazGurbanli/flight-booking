package com.example.flightbooking.controller;

import com.example.flightbooking.dto.response.ApiResponse;
import com.example.flightbooking.dto.response.FlightResponse;
import com.example.flightbooking.entity.Flight;
import com.example.flightbooking.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminController {

    private final FlightService flightService;

    @PostMapping("/flights")
    public ResponseEntity<ApiResponse<FlightResponse>> addFlight(
            @RequestBody Flight flight) {
        FlightResponse response = flightService.addFlight(flight);
        return ResponseEntity.ok(new ApiResponse<>(true, "Uçuş əlavə edildi", response));
    }
}