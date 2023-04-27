package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.InvalidVehicleException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/cars/create")
public class AddCarServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    @Autowired
    //ClientService clientService;
    VehicleService vehicleService;
    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        this.getServletContext().getRequestDispatcher("/WEB-INF/views/vehicles/create.jsp").forward(req,resp);


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        req.setAttribute("message", "");
        String marque = req.getParameter("manufacturer");
        String modele = req.getParameter("modele");
        int nbPlaces = Integer.parseInt(req.getParameter("seats"));

        Vehicle vehicle = new Vehicle (marque, modele, nbPlaces);
        try {
            Vehicle.ValidVehicle(vehicle);
            this.vehicleService.create(vehicle);
        } catch (ServiceException e) {
            e.printStackTrace();
        } catch (InvalidVehicleException e) {
            req.setAttribute("message", "Erreur, veuillez saisir un véhicule valide");
        }

        this.doGet(req,resp);

    }


}
