package com.example.flightbooking.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PassengerRequest {
    private String firstName;
    private String lastName;
    private String passportNumber;
    private LocalDate dateOfBirth;
}