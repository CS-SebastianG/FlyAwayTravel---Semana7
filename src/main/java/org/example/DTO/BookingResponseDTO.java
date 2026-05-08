package org.example.DTO;

import java.time.LocalDateTime;

public class BookingResponseDTO {
    private Long id;
    private Long flightId;
    private Long userId;
    private LocalDateTime bookingDate;
    private String status;
    private String flightNumber;
    private String airline;

    public BookingResponseDTO() {}

    public BookingResponseDTO(Long id, Long flightId, Long userId, LocalDateTime bookingDate,
                              String status, String flightNumber, String airline) {
        this.id = id;
        this.flightId = flightId;
        this.userId = userId;
        this.bookingDate = bookingDate;
        this.status = status;
        this.flightNumber = flightNumber;
        this.airline = airline;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getFlightId() { return flightId; }
    public void setFlightId(Long flightId) { this.flightId = flightId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public LocalDateTime getBookingDate() { return bookingDate; }
    public void setBookingDate(LocalDateTime bookingDate) { this.bookingDate = bookingDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getFlightNumber() { return flightNumber; }
    public void setFlightNumber(String flightNumber) { this.flightNumber = flightNumber; }

    public String getAirline() { return airline; }
    public void setAirline(String airline) { this.airline = airline; }
}