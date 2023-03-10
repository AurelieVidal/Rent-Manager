package com.epf.rentmanager.servlet;


import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ClientService clientservice = ClientService.getInstance();
		request.setAttribute("nbClients", clientservice.count());


		VehicleService vehicleservice = VehicleService.getInstance();
		request.setAttribute("nbVehicle", vehicleservice.count());


		long nbReservation=-2;
		request.setAttribute("nbReservation", ReservationService.getInstance().count());

		this.getServletContext().getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);

	}


	public  void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException  {
//appeler doGet
	}
}
