package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
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

@WebServlet("/users/details")
public class InfoUserServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    @Autowired
    ClientService clientService;

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
            Client client = this.clientService.findById(id);
            req.setAttribute("nom", client.getNom());
            req.setAttribute("prenom", client.getPrenom());
            req.setAttribute("email", client.getEmail());

            req.setAttribute("reservations", reservationService.findReservationsByClientId(id));
            req.setAttribute("nbReservation", reservationService.findReservationsByClientId(id).size());

            Iterator<Reservation> it = reservationService.findReservationsByClientId(id).iterator();
            ArrayList <Vehicle> vehicles = new ArrayList<>();
            while (it.hasNext()) {
                Reservation r = it.next();
                Vehicle v = r.getVehicle();
                if (!vehicles.contains(v)){
                    vehicles.add(v);
                }
            }

            req.setAttribute("vehicles", vehicles);
            req.setAttribute("nbVehicles", vehicles.size());

        } catch (ServiceException e) {
            throw new RuntimeException(e);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }




        this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/details.jsp").forward(req,resp);
    }


}

