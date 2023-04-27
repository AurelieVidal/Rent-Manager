package com.epf.rentmanager.main;

import com.epf.rentmanager.config.AppConfiguration;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;
import java.util.List;

public class main {

    public static void main( String[] args ) throws ServiceException, DaoException {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
        ClientService clientService = context.getBean(ClientService.class);
        VehicleService vehicleService = context.getBean(VehicleService.class);
        ReservationService reservationService = context.getBean(ReservationService.class);

        List<Client> clients = null;

        try {
            clients = clientService.findAll();
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException();
        }

        System.out.println(clients);

        clientService.create(new Client(1,"Aurélie", "Vidal", "@@", LocalDate.of(2001,04,27)));

        try {
            clients = clientService.findAll();
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException();
        }

        clientService.delete(new Client(5,"Aurélie", "Vidal", "@@", LocalDate.of(2001,04,27)));

        System.out.println(clients);

        try {
            clients = clientService.findAll();
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException();
        }

        System.out.println(clients);

        List<Vehicle> vehicles = null;
        try {
            vehicles = vehicleService.findAll();
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException();
        }

        System.out.println(vehicles);

        vehicleService.create(new Vehicle(1, "Skoda", "Fabia", 4));

        try {
            vehicles = vehicleService.findAll();
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException();
        }

        System.out.println(vehicles);

        vehicleService.delete(new Vehicle(5, "Skoda", "Fabia", 4));

        try {
            vehicles = vehicleService.findAll();
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException();
        }

        System.out.println(vehicles);


        List<Reservation> reservations = null;
        try {
            reservations = reservationService.findAll();
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException();
        }

        System.out.println(reservations);


        reservationService.create(new Reservation(1, clients.get(1), vehicles.get(1), LocalDate.of(2022, 11, 15), LocalDate.of(2022, 11, 18)));
        reservationService.create(new Reservation(2, clients.get(0), vehicles.get(3), LocalDate.of(2023, 1, 8), LocalDate.of(2023, 1, 11)));

        reservations = null;
        try {
            reservations = reservationService.findAll();
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException();
        }

        System.out.println(reservations);


    }

}
