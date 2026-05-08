package org.example.Service;

import org.example.Domain.Flight;
import org.example.Repository.FlightRepository;
import org.example.DTO.FlightCreateDTO;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class FlightService {

    private final FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public Flight createFlight(FlightCreateDTO dto) {

        if (dto.getFlightNumber() == null || dto.getFlightNumber().isEmpty()) {
            throw new RuntimeException("El numero de vuelo es obligatorio");
        }

        if (dto.getAirline() == null || dto.getAirline().isEmpty()) {
            throw new RuntimeException("La aerolinea es obligatoria");
        }

        if (dto.getDepartureTime() == null) {
            throw new RuntimeException("La hora de salida es obligatoria");
        }

        if (dto.getArrivalTime() == null) {
            throw new RuntimeException("La hora de llegada es obligatoria");
        }

        if (dto.getAvailableSeats() == null) {
            throw new RuntimeException("Los asientos disponibles son obligatoria");
        }

        String flightNumber = dto.getFlightNumber();
        if (!flightNumber.matches("^[A-Z0-9]{1,6}$")){
            throw  new RuntimeException("El numero es invalido. Solo letras mayusculas y numero, maximo 6 caracteres");
        }

        if (flightRepository.existsByFlightNumber(flightNumber)) {
            throw new RuntimeException("El vuelo ya existe con el numero: " + flightNumber);
        }

        if (dto.getDepartureTime().isAfter(dto.getArrivalTime())) {
            throw new RuntimeException("La hora de salida debe ser antes que la hora de llegada");
        }
        if (dto.getDepartureTime().isEqual(dto.getArrivalTime())) {
            throw new RuntimeException("La hora de salida no puede ser igual a la hora de llegada");
        }

        if (dto.getAvailableSeats() <= 0) {
            throw new RuntimeException("Los asientos disponibles deben ser mayores a 0");
        }

        Flight flight = new Flight();
        flight.setFlightNumber(flightNumber);
        flight.setAirline(dto.getAirline());
        flight.setDepartureTime(dto.getDepartureTime());
        flight.setArrivalTime(dto.getArrivalTime());
        flight.setAvailableSeats(dto.getAvailableSeats());

        return flightRepository.save(flight);
    }

    public List<Flight> searchFlights(String flightNumber, String airline,
                                      LocalDateTime from, LocalDateTime to) {
        if ((flightNumber == null || flightNumber.isBlank()) &&
                (airline == null || airline.isBlank()) &&
                from == null && to == null) {
            return flightRepository.findAll();
        }

        if (flightNumber != null && flightNumber.isBlank()) flightNumber = null;
        if (airline != null && airline.isBlank()) airline = null;

        return flightRepository.searchFlights(flightNumber, airline, from, to);
    }
}
