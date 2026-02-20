package com.example.flightbooking.repository;

import com.example.flightbooking.entity.Booking;
import com.example.flightbooking.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByBooking(Booking booking);
}