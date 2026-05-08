package org.example.DTO;

import java.time.LocalDateTime;


public class FlightDTO {

    private String flightNumber;
    private String airline;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private Integer availableSeats;
    private Long id;


    public String getFlightNumber() { return flightNumber; }
    public void setFlightNumber(String flightNumber) { this.flightNumber = flightNumber; }

    public String getAirline() { return airline; }
    public void setAirline(String airline) { this.airline = airline; }

    public LocalDateTime getDepartureTime() { return departureTime; }
    public void setDepartureTime(LocalDateTime departureTime) { this.departureTime = departureTime; }

    public LocalDateTime getArrivalTime() { return arrivalTime; }
    public void setArrivalTime(LocalDateTime arrivalTime) { this.arrivalTime = arrivalTime;}

    public Integer getAvailableSeats() { return availableSeats; }
    public void setAvailableSeats(Integer availableSeats) { this.availableSeats = availableSeats; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public FlightDTO() {}

    public FlightDTO(Long id, String flightNumber, String airline,
                     LocalDateTime departureTime, LocalDateTime arrivalTime,
                     Integer availableSeats) {
        this.id = id;
        this.flightNumber = flightNumber;
        this.airline = airline;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.availableSeats = availableSeats;
    }
}
