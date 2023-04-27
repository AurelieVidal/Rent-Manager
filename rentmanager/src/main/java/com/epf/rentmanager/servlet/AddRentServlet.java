package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.InvalidReservationException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
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
import java.time.LocalDate;
import java.util.ArrayList;

@WebServlet("/rents/create")
public class AddRentServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	@Autowired
	ClientService clientService;
	@Autowired
	VehicleService vehicleService;
	@Autowired
	ReservationService reservationService;
	@Override
	public void init() throws ServletException {
		super.init();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			req.setAttribute("clients", this.clientService.findAll());
			req.setAttribute("vehicles", this.vehicleService.findAll());

		} catch (ServiceException e) {
			throw new RuntimeException(e);
		} catch (DaoException e) {
			throw new RuntimeException(e);
		}


		this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/create.jsp").forward(req,resp);


	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		req.setAttribute("message", "");
		int vehicleId = Integer.parseInt(req.getParameter("car"));
		int clientId = Integer.parseInt(req.getParameter("client"));
		Vehicle vehicle = null;
		Client client = null;
		try {
			vehicle = this.vehicleService.findById(vehicleId);
			client = this.clientService.findById(clientId);
		} catch (ServiceException e) {
			throw new RuntimeException(e);
		} catch (DaoException e) {
			throw new RuntimeException(e);
		}

		LocalDate debut = LocalDate.parse(req.getParameter("begin"));
		LocalDate fin = LocalDate.parse(req.getParameter("end"));

		Reservation reservation = new Reservation(client, vehicle, debut, fin);
		try {
			Reservation.ValidReservation(reservation);
			this.reservationService.checkFree(reservation);
			this.reservationService.Check30Days(reservation);
			this.reservationService.create(reservation);
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (InvalidReservationException e) {
			req.setAttribute("message", "Erreur, veuillez saisir des dates valides");
		}

		this.doGet(req,resp);

	}


}
