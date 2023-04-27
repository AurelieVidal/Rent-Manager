package com.epf.rentmanager.services;


import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.VehicleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class VehicleServiceTest {
    @BeforeEach
    void setUp(){
        vehicleDao = mock (VehicleDao.class);
        vehicleService = new VehicleService(vehicleDao);
    }
    private VehicleService vehicleService;

    private VehicleDao vehicleDao;


    @Test
    void findAll_should_fail_when_dao_throws_exception() throws DaoException {
        // When
        when(vehicleDao.findAll()).thenThrow(DaoException.class);

        // Then
        assertThrows(ServiceException.class, () ->vehicleService.findAll());
    }

    @Test
    void create_should_fail_when_dao_throws_exception() throws DaoException {
        // When
        Vehicle vehicle = new Vehicle("Renault", "Clio", 4);
        when(vehicleDao.create(vehicle)).thenThrow(DaoException.class);

        // Then
        assertThrows(ServiceException.class, () -> vehicleService.create(vehicle));
    }

    @Test
    void edit_should_fail_when_dao_throws_exception() throws DaoException {
        // When
        Vehicle vehicle = new Vehicle("Renault", "Clio", 4);
        when(vehicleDao.edit(vehicle, 3)).thenThrow(DaoException.class);

        // Then
        assertThrows(ServiceException.class, () -> vehicleService.edit(vehicle, 3));
    }

    @Test
    void delete_should_fail_when_dao_throws_exception() throws DaoException {
        // When
        Vehicle vehicle = new Vehicle("Renault", "Clio", 4);
        when(vehicleDao.delete(vehicle)).thenThrow(DaoException.class);

        // Then
        assertThrows(ServiceException.class, () -> vehicleService.delete(vehicle));
    }

    @Test
    void findById_should_fail_when_dao_throws_exception() throws DaoException {
        // When
        when(vehicleDao.findById(3)).thenThrow(DaoException.class);

        // Then
        assertThrows(ServiceException.class, () -> vehicleService.findById(3));
    }

    @Test
    void count_should_fail_when_dao_throws_exception() throws DaoException {
        // When
        when(vehicleDao.count()).thenThrow(DaoException.class);

        // Then
        assertThrows(ServiceException.class, () -> vehicleService.count());
    }


}
