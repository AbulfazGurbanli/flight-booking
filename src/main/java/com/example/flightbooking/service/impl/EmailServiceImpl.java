package com.example.flightbooking.service.impl;

import com.example.flightbooking.entity.Booking;
import com.example.flightbooking.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Override
    public void sendBookingConfirmation(Booking booking) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(booking.getUser().getEmail());
            message.setSubject("Bilet Təsdiqi - " + booking.getBookingCode());
            message.setText(
                    "Hörmətli " + booking.getUser().getFirstName() + ",\n\n" +
                            "Biletiniz uğurla alındı!\n\n" +
                            "Booking kodu: " + booking.getBookingCode() + "\n" +
                            "Uçuş: " + booking.getFlight().getFlightNumber() + "\n" +
                            "Kalkış: " + booking.getFlight().getDepartureAirport().getCode() + "\n" +
                            "Eniş: " + booking.getFlight().getArrivalAirport().getCode() + "\n" +
                            "Tarix: " + booking.getFlight().getDepartureTime() + "\n" +
                            "Məbləğ: " + booking.getTotalPrice() + " AZN\n\n" +
                            "Təşəkkür edirik!"
            );
            mailSender.send(message);
            System.out.println("✅ Mail göndərildi: " + booking.getUser().getEmail());
        } catch (Exception e) {
            System.out.println("❌ Mail xətası: " + e.getMessage());
        }
    }
}