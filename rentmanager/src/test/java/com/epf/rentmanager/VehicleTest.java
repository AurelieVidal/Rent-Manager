package com.epf.rentmanager;


import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class VehicleTest {
    @Test
    public void CheckNb_Places_should_return_true_when_between_2_and_9() {
        // Given
        Vehicle legalVehicle = new Vehicle("Renault", "Clio", 4);

        // Then
        assertTrue(Vehicle.checkNbPlaces(legalVehicle));
    }

    @Test
    public void CheckNb_Places_should_return_false_when_under_2() {
        // Given
        Vehicle illegalVehicle = new Vehicle("Renault", "Clio", 1);

        // Then
        assertFalse(Vehicle.checkNbPlaces(illegalVehicle));
    }

    @Test
    public void CheckNb_Places_should_return_false_when_above_9() {
        // Given
        Vehicle illegalVehicle = new Vehicle("Renault", "Clio", 20);

        // Then
        assertFalse(Vehicle.checkNbPlaces(illegalVehicle));
    }



}

