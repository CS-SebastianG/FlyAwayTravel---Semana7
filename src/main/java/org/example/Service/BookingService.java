package org.example.Service;

import org.example.Domain.Booking;
import org.example.Domain.Flight;
import org.example.Domain.User;
import org.example.DTO.BookingRequestDTO;
import org.example.DTO.BookingResponseDTO;
import org.example.Repository.BookingRepository;
import org.example.Repository.FlightRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final FlightRepository flightRepository;
    private final UserService userService;
    private final EmailService emailService;

    public BookingService(BookingRepository bookingRepository,
                          FlightRepository flightRepository,
                          UserService userService,
                          EmailService emailService) {
        this.bookingRepository = bookingRepository;
        this.flightRepository = flightRepository;
        this.userService = userService;
        this.emailService = emailService;
    }

    public BookingResponseDTO createBooking(BookingRequestDTO request, Long userId) {

        Flight flight = flightRepository.findById(request.getFlightId())
                .orElseThrow(() -> new RuntimeException("Vuelo no encontrado"));

        if (bookingRepository.existsByFlightIdAndUserId(request.getFlightId(), userId)) {
            throw new RuntimeException("Ya tienes una reserva para este vuelo");
        }

        if (flight.getAvailableSeats() <= 0) {
            throw new RuntimeException("No hay asientos disponibles para este vuelo");
        }

        if (flight.getDepartureTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("No se puede reservar un vuelo que ya ha salido");
        }

        int updated = flightRepository.decrementAvailableSeats(request.getFlightId());
        if (updated == 0) {
            throw new RuntimeException("No se pudo reservar el vuelo. Intenta de nuevo.");
        }

        Booking booking = new Booking();
        booking.setFlightId(request.getFlightId());
        booking.setUserId(userId);
        booking.setBookingDate(LocalDateTime.now());
        booking.setStatus("CONFIRMED");

        Booking savedBooking = bookingRepository.save(booking);

        try {
            User user = userService.getUserById(userId);
            emailService.sendBookingConfirmation(user, flight, savedBooking);
        } catch (Exception e) {
            System.err.println("No se pudo enviar el email: " + e.getMessage());
        }

        return new BookingResponseDTO(
                savedBooking.getId(),
                savedBooking.getFlightId(),
                savedBooking.getUserId(),
                savedBooking.getBookingDate(),
                savedBooking.getStatus(),
                flight.getFlightNumber(),
                flight.getAirline()
        );
    }
}