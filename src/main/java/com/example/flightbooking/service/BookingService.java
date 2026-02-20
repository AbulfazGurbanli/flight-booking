package com.example.flightbooking.service;

import com.example.flightbooking.dto.request.BookingRequest;
import com.example.flightbooking.dto.response.BookingResponse;

import java.util.List;

public interface BookingService {
    BookingResponse createBooking(BookingRequest request, String email);
    List<BookingResponse> getMyBookings(String email);
    BookingResponse cancelBooking(String bookingCode, String email);
}