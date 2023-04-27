package com.epf.rentmanager;


import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReservationTest {
    @Test
    public void CheckDuration_should_return_true_when_duration_under_7() {
        // Given
        Reservation legalReservation = new Reservation(1, new Client("John", "Doe", "john.doe@ensta.fr", LocalDate.of(2001,02,15)), new Vehicle("Renault", "Clio", 20), LocalDate.of(2023, 04, 20), LocalDate.of(2023, 04, 24));

        // Then
        assertTrue(Reservation.checkDuration(legalReservation));
    }

    @Test
    public void CheckDuration_should_return_false_when_duration_above_7() {
        // Given
        Reservation illegalReservation = new Reservation(1, new Client("John", "Doe", "john.doe@ensta.fr", LocalDate.of(2001,02,15)), new Vehicle("Renault", "Clio", 20), LocalDate.of(2023, 04, 20), LocalDate.of(2023, 04, 30));

        // Then
        assertFalse(Reservation.checkDuration(illegalReservation));
    }




}

