package com.epf.rentmanager.services;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.InvalidClientException;
import com.epf.rentmanager.exception.InvalidReservationException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class ReservationServiceTest {
    @BeforeEach
    void setUp(){
        reservationDao = mock (ReservationDao.class);
        reservationService = new ReservationService(reservationDao);
    }
    private ReservationService reservationService;

    private ReservationDao reservationDao;


    @Test
    void findAll_should_fail_when_dao_throws_exception() throws DaoException {
        // When
        when(reservationDao.findAll()).thenThrow(DaoException.class);

        // Then
        assertThrows(ServiceException.class, () -> reservationService.findAll());
    }

    @Test
    void create_should_fail_when_dao_throws_exception() throws DaoException {
        // When
        Client client = new Client("John", "Doe", "john.doe@email.com", LocalDate.of(2001,02,15));
        Vehicle vehicle = new Vehicle("Renault", "Clio", 4);
        Reservation reservation = new Reservation (client, vehicle, LocalDate.of(2022, 11, 15), LocalDate.of(2023, 4, 3));
        when(reservationDao.create(reservation)).thenThrow(DaoException.class);

        // Then
        assertThrows(ServiceException.class, () -> reservationService.create(reservation));
    }

    @Test
    void edit_should_fail_when_dao_throws_exception() throws DaoException {
        // When
        Client client = new Client("John", "Doe", "john.doe@email.com", LocalDate.of(2001,02,15));
        Vehicle vehicle = new Vehicle("Renault", "Clio", 4);
        Reservation reservation = new Reservation (client, vehicle, LocalDate.of(2022, 11, 15), LocalDate.of(2023, 4, 3));
        when(reservationDao.edit(reservation, 3)).thenThrow(DaoException.class);

        // Then
        assertThrows(ServiceException.class, () -> reservationService.edit(reservation, 3));
    }

    @Test
    void delete_should_fail_when_dao_throws_exception() throws DaoException {
        // When
        Client client = new Client("John", "Doe", "john.doe@email.com", LocalDate.of(2001,02,15));
        Vehicle vehicle = new Vehicle("Renault", "Clio", 4);
        Reservation reservation = new Reservation (client, vehicle, LocalDate.of(2022, 11, 15), LocalDate.of(2023, 4, 3));
        when(reservationDao.delete(reservation)).thenThrow(DaoException.class);

        // Then
        assertThrows(ServiceException.class, () -> reservationService.delete(reservation));
    }

    @Test
    void findById_should_fail_when_dao_throws_exception() throws DaoException {
        // When
        when(reservationDao.findById(3)).thenThrow(DaoException.class);

        // Then
        assertThrows(ServiceException.class, () -> reservationService.findById(3));
    }

    @Test
    void findByClientId_should_fail_when_dao_throws_exception() throws DaoException {
        // When
        when(reservationDao.findByClientId(3)).thenThrow(DaoException.class);

        // Then
        assertThrows(ServiceException.class, () -> reservationService.findReservationsByClientId(3));
    }

    @Test
    void findByVehicleId_should_fail_when_dao_throws_exception() throws DaoException {
        // When
        when(reservationDao.findResaByVehicleId(3)).thenThrow(DaoException.class);

        // Then
        assertThrows(ServiceException.class, () -> reservationService.findReservationsByVehicleId(3));
    }
    @Test
    void count_should_fail_when_dao_throws_exception() throws DaoException {
        // When
        when(reservationDao.count()).thenThrow(DaoException.class);

        // Then
        assertThrows(ServiceException.class, () -> reservationService.count());
    }

    @Test
    void checkFree_should_fail_when_dao_throws_exception() throws DaoException, InvalidReservationException {
        // When
        Client client = new Client("John", "Doe", "john.doe@email.com", LocalDate.of(2001,02,15));
        Vehicle vehicle = new Vehicle("Renault", "Clio", 4);
        Reservation reservation = new Reservation (client, vehicle, LocalDate.of(2022, 11, 15), LocalDate.of(2023, 4, 3));
        when(reservationDao.CheckFree(reservation)).thenThrow(DaoException.class);


        // Then
        assertThrows(ServiceException.class, () -> reservationService.checkFree(reservation));
    }

    @Test
    void checkFree_should_fail_when_invalidReservation_throws_exception() throws InvalidReservationException, DaoException {
        // When
        Client client = new Client("John", "Doe", "john.doe@email.com", LocalDate.of(2001,02,15));
        Vehicle vehicle = new Vehicle("Renault", "Clio", 4);
        Reservation reservation = new Reservation (client, vehicle, LocalDate.of(2022, 11, 15), LocalDate.of(2023, 4, 3));

        when(reservationDao.CheckFree(reservation)).thenThrow(InvalidReservationException.class);


        // Then
        assertThrows(InvalidReservationException.class, () -> reservationService.checkFree(reservation));
    }


}
