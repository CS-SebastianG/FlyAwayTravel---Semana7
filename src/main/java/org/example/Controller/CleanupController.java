package org.example.Controller;

import org.example.Repository.BookingRepository;
import org.example.Repository.FlightRepository;
import org.example.Repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
public class CleanupController {

    private final FlightRepository flightRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;

    public CleanupController(FlightRepository flightRepository,
                             UserRepository userRepository,
                             BookingRepository bookingRepository) {
        this.flightRepository = flightRepository;
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
    }

    @DeleteMapping("/cleanup")
    public ResponseEntity<Map<String, String>> cleanupDatabase() {

        bookingRepository.deleteAll();
        flightRepository.deleteAll();
        userRepository.deleteAll();

        Map<String, String> response = new HashMap<>();
        response.put("message", "Base de datos limpiada exitosamente");
        response.put("status", "success");

        return ResponseEntity.ok(response);
    }
}