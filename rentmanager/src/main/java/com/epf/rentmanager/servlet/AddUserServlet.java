package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.InvalidClientException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/users/create")
public class AddUserServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	@Autowired
	ClientService clientService;
	@Override
	public void init() throws ServletException {
		super.init();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {


		this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/create.jsp").forward(req,resp);


	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		req.setAttribute("message", "");
		String last_name = req.getParameter("last_name");
		String first_name = req.getParameter("first_name");
		String email = req.getParameter("email");
		LocalDate naissance = LocalDate.parse(req.getParameter("naissance"));

		Client client = new Client (last_name, first_name, email, naissance);
		try {
			Client.ValidClient(client);
			this.clientService.checkEmail(client);
			this.clientService.create(client);
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (InvalidClientException e) {
			req.setAttribute("message", "Erreur, veuillez saisir un client valide");
		}

		this.doGet(req,resp);

	}


}
