package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

@WebServlet("/users/create")
public class AddUserServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private ClientService clientService = ClientService.getInstance();

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/create.jsp").forward(req,resp);


	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String last_name = req.getParameter("last_name");
		String first_name = req.getParameter("first_name");
		String email = req.getParameter("email");
		LocalDate naissance = LocalDate.parse(req.getParameter("naissance"));

		Client client = new Client (last_name, first_name, email, naissance);
		try {
			this.clientService.create(client);
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		this.doGet(req,resp);

	}


}
