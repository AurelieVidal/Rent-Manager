package com.epf.rentmanager.servlet;

import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/rents")
public class ReservationServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private ReservationService reservationService = ReservationService.getInstance();

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {


		try {
			req.setAttribute("reservations",this.reservationService.findAll());
		} catch (ServiceException e) {
			throw new RuntimeException(e);
		} catch (DaoException e) {
			throw new RuntimeException(e);
		}

		this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/list.jsp").forward(req,resp);

	}


}
