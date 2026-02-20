package com.example.flightbooking.service.impl;

import com.example.flightbooking.dto.request.BookingRequest;
import com.example.flightbooking.dto.request.PassengerRequest;
import com.example.flightbooking.dto.response.BookingResponse;
import com.example.flightbooking.entity.*;
import com.example.flightbooking.enums.BookingStatus;
import com.example.flightbooking.exception.BookingNotFoundException;
import com.example.flightbooking.exception.FlightNotFoundException;
import com.example.flightbooking.exception.SeatNotAvailableException;
import com.example.flightbooking.repository.BookingRepository;
import com.example.flightbooking.repository.FlightRepository;
import com.example.flightbooking.repository.UserRepository;
import com.example.flightbooking.service.BookingService;
import com.example.flightbooking.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final FlightRepository flightRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;

    @Override
    @Transactional
    public BookingResponse createBooking(BookingRequest request, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User tapılmadı"));

        Flight flight = flightRepository.findById(request.getFlightId())
                .orElseThrow(() -> new FlightNotFoundException("Uçuş tapılmadı"));

        int passengerCount = request.getPassengers().size();

        if (flight.getAvailableSeats() < passengerCount) {
            throw new SeatNotAvailableException("Kifayət qədər boş yer yoxdur");
        }

        // Seat azalt
        flight.setAvailableSeats(flight.getAvailableSeats() - passengerCount);
        flightRepository.save(flight);

        // Booking yarat
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setFlight(flight);
        booking.setBookingCode(UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        booking.setStatus(BookingStatus.CONFIRMED);
        booking.setTotalPrice(flight.getPrice().multiply(BigDecimal.valueOf(passengerCount)));

        // Passenger-ləri əlavə et
        List<Passenger> passengers = request.getPassengers().stream()
                .map(p -> mapToPassenger(p, booking))
                .collect(Collectors.toList());
        booking.setPassengers(passengers);

        Booking saved = bookingRepository.save(booking);

        // Mail göndər
        emailService.sendBookingConfirmation(saved);

        return mapToResponse(saved);
    }

    @Override
    public List<BookingResponse> getMyBookings(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User tapılmadı"));
        return bookingRepository.findByUser(user).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public BookingResponse cancelBooking(String bookingCode, String email) {
        Booking booking = bookingRepository.findByBookingCode(bookingCode)
                .orElseThrow(() -> new BookingNotFoundException("Booking tapılmadı"));

        if (!booking.getUser().getEmail().equals(email)) {
            throw new BookingNotFoundException("Bu booking sizə aid deyil");
        }

        // Seat geri qaytar
        Flight flight = booking.getFlight();
        flight.setAvailableSeats(flight.getAvailableSeats() + booking.getPassengers().size());
        flightRepository.save(flight);

        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);

        return mapToResponse(booking);
    }

    private Passenger mapToPassenger(PassengerRequest request, Booking booking) {
        Passenger passenger = new Passenger();
        passenger.setFirstName(request.getFirstName());
        passenger.setLastName(request.getLastName());
        passenger.setPassportNumber(request.getPassportNumber());
        passenger.setDateOfBirth(request.getDateOfBirth());
        passenger.setBooking(booking);
        return passenger;
    }

    private BookingResponse mapToResponse(Booking booking) {
        BookingResponse response = new BookingResponse();
        response.setBookingCode(booking.getBookingCode());
        response.setFlightNumber(booking.getFlight().getFlightNumber());
        response.setStatus(booking.getStatus().name());
        response.setTotalPrice(booking.getTotalPrice());
        response.setPassengerCount(booking.getPassengers().size());
        return response;
    }
}