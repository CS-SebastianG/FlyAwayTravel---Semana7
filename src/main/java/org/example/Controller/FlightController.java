package org.example.Controller;

import org.example.Domain.Flight;
import org.example.DTO.FlightCreateDTO;
import org.example.DTO.FlightDTO;
import org.example.Service.FlightService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/flights")
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createFlight(@RequestBody FlightCreateDTO dto) {
        try {
            Flight savedFlight = flightService.createFlight(dto);

            Map<String, Long> response = new HashMap<>();
            response.put("id", savedFlight.getId());

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("Error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchFlights(
            @RequestParam(required = false) String flightNumber,
            @RequestParam(required = false) String airline,
            @RequestParam(required = false) LocalDateTime from,
            @RequestParam(required = false) LocalDateTime to) {

        List<Flight> flights = flightService.searchFlights(flightNumber, airline, from, to);

        List<FlightDTO> flightDTOs = flights.stream()
                .map(this::convertToDTO)
                .toList();

        return ResponseEntity.ok(flightDTOs);
    }

    private FlightDTO convertToDTO(Flight flight) {
        FlightDTO dto = new FlightDTO();
        dto.setId(flight.getId());
        dto.setFlightNumber(flight.getFlightNumber());
        dto.setAirline(flight.getAirline());
        dto.setDepartureTime(flight.getDepartureTime());
        dto.setArrivalTime(flight.getArrivalTime());
        dto.setAvailableSeats(flight.getAvailableSeats());
        return dto;
    }
}
