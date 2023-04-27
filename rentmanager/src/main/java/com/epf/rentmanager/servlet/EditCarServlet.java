package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.DaoException;
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

@WebServlet("/cars/edit")
public class EditCarServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	@Autowired
	VehicleService vehicleService;
	@Override
	public void init() throws ServletException {
		super.init();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {


		long id = Long.parseLong(req.getParameter("id"));
		try {
			Vehicle vehicle = this.vehicleService.findById(id);
			req.setAttribute("constructeur", vehicle.getConstructeur());
			req.setAttribute("modele", vehicle.getModele());
			req.setAttribute("nbPlaces", vehicle.getNb_places());
			req.setAttribute("id", id);
		} catch (ServiceException e) {
			throw new RuntimeException(e);
		}

		this.getServletContext().getRequestDispatcher("/WEB-INF/views/vehicles/edit.jsp").forward(req,resp);


	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("message", "");
		long id = Long.parseLong(req.getParameter("id"));
		String constructeur = req.getParameter("manufacturer");
		String modele = req.getParameter("modele");
		int nbPlaces = Integer.parseInt(req.getParameter("seats"));

		Vehicle vehicle =  new Vehicle (constructeur, modele, nbPlaces);
		try {
			Vehicle.ValidVehicle(vehicle);
			this.vehicleService.edit(vehicle, id);
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (InvalidVehicleException e) {
			req.setAttribute("message", "Erreur, veuillez saisir un véhicule valide");
		}

		this.doGet(req,resp);

	}


}
