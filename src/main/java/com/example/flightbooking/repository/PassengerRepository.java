package com.example.flightbooking.repository;

import com.example.flightbooking.entity.Booking;
import com.example.flightbooking.entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {
    List<Passenger> findByBooking(Booking booking);
}