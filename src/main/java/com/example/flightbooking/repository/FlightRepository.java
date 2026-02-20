package com.example.flightbooking.repository;

import com.example.flightbooking.entity.Flight;
import com.example.flightbooking.enums.FlightStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    List<Flight> findByDepartureAirport_CodeAndArrivalAirport_CodeAndDepartureTimeBetweenAndStatus(
            String departureCode,
            String arrivalCode,
            LocalDateTime from,
            LocalDateTime to,
            FlightStatus status
    );
}