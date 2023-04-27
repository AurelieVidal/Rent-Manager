package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

@WebServlet("/rents/details")
public class InfoReservationServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    @Autowired
    //ClientService clientService;
    VehicleService vehicleService;

    @Autowired
    ReservationService reservationService;
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {



        long id = Long.parseLong(req.getParameter("id"));

        try {
            Reservation reservation = this.reservationService.findById(id);
            req.setAttribute("vehicle", reservation.getVehicle());
            req.setAttribute("client", reservation.getClient());
            req.setAttribute("debut", reservation.getDebut());
            req.setAttribute("fin", reservation.getFin());

            //req.setAttribute("reservations", reservationService.findReservationsByVehicleId(id));
            //req.setAttribute("nbReservation", reservationService.findReservationsByVehicleId(id).size());
/*
            Iterator<Reservation> it = reservationService.findReservationsByVehicleId(id).iterator();
            //toutes les reservations qui correspondent au véhicule
            System.out.println(reservationService.findReservationsByVehicleId(id));
            ArrayList <Client> clients = new ArrayList<>();
            //la liste des clients
            while (it.hasNext()) {
                Reservation r = it.next();
                System.out.println(r.getClient());
                Client c = r.getClient();
                if (!clients.contains(c)){
                    clients.add(c);
                }
            }

            req.setAttribute("clients", clients);
            req.setAttribute("nbClients", clients.size());*/



        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }


        this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/details.jsp").forward(req,resp);
    }


}

