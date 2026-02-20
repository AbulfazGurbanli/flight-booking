package com.example.flightbooking.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class FlightSearchRequest {
    private String departureCode;
    private String arrivalCode;
    private LocalDate date;
    private int passengerCount;
}