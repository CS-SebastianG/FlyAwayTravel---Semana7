package org.example.Service;

import org.example.Domain.Booking;
import org.example.Domain.Flight;
import org.example.Domain.User;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;

@Service
public class EmailService {

    private static final String EMAIL_DIRECTORY = "emails";

    public void sendBookingConfirmation(User user, Flight flight, Booking booking) {
        try {
            Path dir = Paths.get(EMAIL_DIRECTORY);
            if (!Files.exists(dir)) {
                Files.createDirectories(dir);
            }
            String filename = EMAIL_DIRECTORY + "/flight_booking_email_" + booking.getId() + ".txt";

            String content = buildEmailContent(user, flight, booking);

            try (FileWriter writer = new FileWriter(filename)) {
                writer.write(content);
            }

            System.out.println(" Email guardado en archivo: " + filename);

        } catch (IOException e) {
            System.err.println(" Error al guardar el email: " + e.getMessage());
        }
    }

    private String buildEmailContent(User user, Flight flight, Booking booking) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        return "========================================\n" +
                "       CONFIRMACION DE RESERVA\n" +
                "========================================\n\n" +
                "Hola " + user.getFullName() + ",\n\n" +
                "Tu reserva ha sido confirmada exitosamente.\n\n" +
                "---------- DETALLES DEL VUELO ----------\n" +
                "Numero de vuelo: " + flight.getFlightNumber() + "\n" +
                "Aerolínea: " + flight.getAirline() + "\n" +
                "Fecha de salida: " + flight.getDepartureTime().format(formatter) + "\n" +
                "Fecha de llegada: " + flight.getArrivalTime().format(formatter) + "\n" +
                "Numero de reserva: #" + booking.getId() + "\n" +
                "----------------------------------------\n\n";
    }
}

