package com.example.flightbooking.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BookingResponse {
    private String bookingCode;
    private String flightNumber;
    private String status;
    private BigDecimal totalPrice;
    private int passengerCount;
}