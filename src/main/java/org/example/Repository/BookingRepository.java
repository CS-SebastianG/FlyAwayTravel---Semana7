package org.example.Repository;

import org.example.Domain.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUserId(Long userId);
    List<Booking> findByFlightId(Long flightId);
    boolean existsByFlightIdAndUserId(Long flightId, Long userId);
}