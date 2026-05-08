package org.example.Repository;

import org.example.Domain.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FlightRepository extends JpaRepository<Flight, Long> {

    Optional<Flight> findByFlightNumber(String flightNumber);

    boolean existsByFlightNumber(String flightNumber);

    @Query("SELECT f FROM Flight f WHERE " +
            "(:flightNumber IS NULL OR LOWER(f.flightNumber) LIKE LOWER(CONCAT('%', :flightNumber, '%'))) AND " +
            "(:airline IS NULL OR LOWER(f.airline) LIKE LOWER(CONCAT('%', :airline, '%'))) AND " +
            "(:from IS NULL OR f.departureTime >= :from) AND " +
            "(:to IS NULL OR f.departureTime <= :to)")
    List<Flight> searchFlights(@Param("flightNumber") String flightNumber,
                               @Param("airline") String airline,
                               @Param("from") LocalDateTime from,
                               @Param("to") LocalDateTime to);


    @Modifying
    @Transactional
    @Query("UPDATE Flight f SET f.availableSeats = f.availableSeats - 1 WHERE f.id = :flightId AND f.availableSeats > 0")
    int decrementAvailableSeats(@Param("flightId") Long flightId);
}