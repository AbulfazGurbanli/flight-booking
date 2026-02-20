package com.example.flightbooking.service.impl;

import com.example.flightbooking.dto.request.FlightSearchRequest;
import com.example.flightbooking.dto.response.FlightResponse;
import com.example.flightbooking.entity.Flight;
import com.example.flightbooking.enums.FlightStatus;
import com.example.flightbooking.exception.FlightNotFoundException;
import com.example.flightbooking.repository.FlightRepository;
import com.example.flightbooking.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;

    @Override
    public List<FlightResponse> searchFlights(FlightSearchRequest request) {
        LocalDateTime from = request.getDate().atStartOfDay();
        LocalDateTime to = request.getDate().atTime(23, 59, 59);

        List<Flight> flights = flightRepository
                .findByDepartureAirport_CodeAndArrivalAirport_CodeAndDepartureTimeBetweenAndStatus(
                        request.getDepartureCode(),
                        request.getArrivalCode(),
                        from,
                        to,
                        FlightStatus.SCHEDULED
                );

        if (flights.isEmpty()) {
            throw new FlightNotFoundException("Uyğun uçuş tapılmadı");
        }

        return flights.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public FlightResponse addFlight(Flight flight) {
        Flight saved = flightRepository.save(flight);
        return mapToResponse(saved);
    }

    private FlightResponse mapToResponse(Flight flight) {
        FlightResponse response = new FlightResponse();
        response.setId(flight.getId());
        response.setFlightNumber(flight.getFlightNumber());
        response.setDepartureAirport(flight.getDepartureAirport().getCode());
        response.setArrivalAirport(flight.getArrivalAirport().getCode());
        response.setDepartureTime(flight.getDepartureTime());
        response.setArrivalTime(flight.getArrivalTime());
        response.setAvailableSeats(flight.getAvailableSeats());
        response.setPrice(flight.getPrice());
        response.setStatus(flight.getStatus().name());
        return response;
    }
}