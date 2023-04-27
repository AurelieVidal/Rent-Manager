package com.epf.rentmanager.services;

import com.epf.rentmanager.exception.InvalidClientException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class ClientServiceTest {
    @BeforeEach
    void setUp(){
        clientDao = mock (ClientDao.class);
        clientService = new ClientService(clientDao);
    }
    private ClientService clientService;

    private ClientDao clientDao;


    @Test
    void findAll_should_fail_when_dao_throws_exception() throws DaoException {
        // When
        when(clientDao.findAll()).thenThrow(DaoException.class);

        // Then
        assertThrows(ServiceException.class, () -> clientService.findAll());
    }

    @Test
    void create_should_fail_when_dao_throws_exception() throws DaoException {
        // When
        Client client = new Client("John", "Doe", "john.doe@email.com", LocalDate.of(2001,02,15));
        when(clientDao.create(client)).thenThrow(DaoException.class);

        // Then
        assertThrows(ServiceException.class, () -> clientService.create(client));
    }

    @Test
    void edit_should_fail_when_dao_throws_exception() throws DaoException {
        // When
        Client client = new Client("John", "Doe", "john.doe@email.com", LocalDate.of(2001,02,15));
        when(clientDao.edit(client, 3)).thenThrow(DaoException.class);

        // Then
        assertThrows(ServiceException.class, () -> clientService.edit(client, 3));
    }

    @Test
    void delete_should_fail_when_dao_throws_exception() throws DaoException {
        // When
        Client client = new Client("John", "Doe", "john.doe@email.com", LocalDate.of(2001,02,15));
        when(clientDao.delete(client)).thenThrow(DaoException.class);

        // Then
        assertThrows(ServiceException.class, () -> clientService.delete(client));
    }

    @Test
    void findById_should_fail_when_dao_throws_exception() throws DaoException {
        // When
        when(clientDao.findById(3)).thenThrow(DaoException.class);

        // Then
        assertThrows(ServiceException.class, () -> clientService.findById(3));
    }

    @Test
    void count_should_fail_when_dao_throws_exception() throws DaoException {
        // When
        when(clientDao.count()).thenThrow(DaoException.class);

        // Then
        assertThrows(ServiceException.class, () -> clientService.count());
    }

    @Test
    void checkEmail_should_fail_when_dao_throws_exception() throws DaoException, InvalidClientException {
        // When
        Client client = new Client("John", "Doe", "john.doe@email.com", LocalDate.of(2001,02,15));

        when(clientDao.checkEmail(client)).thenThrow(DaoException.class);


        // Then
        assertThrows(ServiceException.class, () -> clientService.checkEmail(client));
    }

    @Test
    void checkEmail_should_fail_when_invalidClient_throws_exception() throws DaoException, InvalidClientException {
        // When
        Client client = new Client("John", "Doe", "john.doe@email.com", LocalDate.of(2001,02,15));
        when(clientDao.checkEmail(client)).thenThrow(InvalidClientException.class);


        // Then
        assertThrows(InvalidClientException.class, () -> clientService.checkEmail(client));
    }
}
