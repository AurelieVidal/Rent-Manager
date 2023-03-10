package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.VehicleService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/cars")
public class VehicleServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private VehicleService vehicleService = VehicleService.getInstance();

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		try {
			req.setAttribute("vehicles",this.vehicleService.findAll());
		} catch (DaoException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			throw new RuntimeException(e);
		}


		this.getServletContext().getRequestDispatcher("/WEB-INF/views/vehicles/list.jsp").forward(req,resp);
	}


}
