package com.example.flightbooking.controller;

import com.example.flightbooking.dto.request.FlightSearchRequest;
import com.example.flightbooking.dto.response.ApiResponse;
import com.example.flightbooking.dto.response.FlightResponse;
import com.example.flightbooking.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flights")
@RequiredArgsConstructor
public class FlightController {

    private final FlightService flightService;

    @PostMapping("/search")
    public ResponseEntity<ApiResponse<List<FlightResponse>>> search(
            @RequestBody FlightSearchRequest request) {
        List<FlightResponse> flights = flightService.searchFlights(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Uçuşlar tapıldı", flights));
    }
}