package com.example.flightbooking.service;

import com.example.flightbooking.dto.request.FlightSearchRequest;
import com.example.flightbooking.dto.response.FlightResponse;
import com.example.flightbooking.entity.Flight;

import java.util.List;

public interface FlightService {
    List<FlightResponse> searchFlights(FlightSearchRequest request);
    FlightResponse addFlight(Flight flight);
}