package com.epf.rentmanager.main;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;

import java.time.LocalDate;
import java.util.List;

public class main {

    public static void main( String[] args ) throws ServiceException, DaoException {


        List<Client> clients = null;

        try {
            clients = ClientService.getInstance().findAll();
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException();
        }

        System.out.println(clients);

        //ClientService.getInstance().create(new Client(1,"Aurélie", "Vidal", "@@", LocalDate.of(2001,04,27)));

        try {
            clients = ClientService.getInstance().findAll();
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException();
        }

        System.out.println(clients);

        try {
            clients = ClientService.getInstance().findAll();
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException();
        }

        System.out.println(clients);

        List<Vehicle> vehicles = null;
        try {
            vehicles = VehicleService.getInstance().findAll();
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException();
        }

        System.out.println(vehicles);

        VehicleService.getInstance().create(new Vehicle(1, "Skoda", "Fabia", 4));

        try {
            vehicles = VehicleService.getInstance().findAll();
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException();
        }

        System.out.println(vehicles);

        VehicleService.getInstance().delete(new Vehicle(5, "Skoda", "Fabia", 4));

        try {
            vehicles = VehicleService.getInstance().findAll();
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException();
        }

        System.out.println(vehicles);


        List<Reservation> reservations = null;
        try {
            reservations = ReservationService.getInstance().findAll();
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException();
        }

        System.out.println(reservations);


        ReservationService.getInstance().create(new Reservation(1, clients.get(1), vehicles.get(1), LocalDate.of(2022, 11, 15), LocalDate.of(2023, 4, 3)));
        ReservationService.getInstance().create(new Reservation(2, clients.get(0), vehicles.get(3), LocalDate.of(2023, 1, 8), LocalDate.of(2023, 11, 17)));

        reservations = null;
        try {
            reservations = ReservationService.getInstance().findAll();
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException();
        }

        System.out.println(reservations);


    }

}
