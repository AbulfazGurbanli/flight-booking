package com.example.flightbooking.service;

import com.example.flightbooking.entity.Booking;

public interface EmailService {
    void sendBookingConfirmation(Booking booking);
}