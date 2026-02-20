package com.example.flightbooking.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class BookingRequest {
    private Long flightId;
    private List<PassengerRequest> passengers;
}