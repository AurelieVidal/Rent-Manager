package com.epf.rentmanager;


import com.epf.rentmanager.model.Client;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


import java.time.LocalDate;

public class ClientTest {
    @Test
    public void isLegal_should_return_true_when_age_is_over_18() {
        // Given
        Client legalUser = new Client("John", "Doe", "john.doe@ensta.fr", LocalDate.of(2001,02,15));

        // Then
        assertTrue(Client.isLegal(legalUser));
    }

    @Test
    public void isLegal_should_return_false_when_age_is_under_18() {
        // Given
        Client illegaUser = new Client("John", "Doe", "john.doe@ensta.fr", LocalDate.of(2020,02,15));

        // Then
        assertFalse(Client.isLegal(illegaUser));
    }

    @Test
    public void checkNames_should_return_true_when_names_length_is_over_18() {
        // Given
        Client legalUser = new Client("John", "Doe", "john.doe@ensta.fr", LocalDate.of(2001,02,15));

        // Then
        assertTrue(Client.checkNames(legalUser));
    }

    @Test
    public void checkNames_should_return_fales_when_last_name_length_is_under_18() {
        // Given
        Client illegalUser = new Client("John", "Do", "john.doe@ensta.fr", LocalDate.of(2001,02,15));

        // Then
        assertFalse(Client.checkNames(illegalUser));
    }

    @Test
    public void checkNames_should_return_fales_when_first_name_length_is_under_18() {
        // Given
        Client illegalUser = new Client("Jo", "Doe", "john.doe@ensta.fr", LocalDate.of(2001,02,15));

        // Then
        assertFalse(Client.checkNames(illegalUser));
    }

}

