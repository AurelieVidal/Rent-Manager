package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.DaoException;
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

@WebServlet("/users/edit")
public class EditUserServlet extends HttpServlet {

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


		long id = Long.parseLong(req.getParameter("id"));
		try {
			Client client = this.clientService.findById(id);
			req.setAttribute("nom", client.getNom());
			req.setAttribute("prenom", client.getPrenom());
			req.setAttribute("email", client.getEmail());
			req.setAttribute("naissance", client.getNaissance());
			req.setAttribute("id", id);
		} catch (ServiceException e) {
			throw new RuntimeException(e);
		} catch (DaoException e) {
			throw new RuntimeException(e);
		}

		this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/edit.jsp").forward(req,resp);


	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("message", "");
		long id = Long.parseLong(req.getParameter("id"));
		String last_name = req.getParameter("last_name");
		String first_name = req.getParameter("first_name");
		String email = req.getParameter("email");
		LocalDate naissance = LocalDate.parse(req.getParameter("naissance"));

		Client client = new Client (last_name, first_name, email, naissance);
		try {
			Client.ValidClient(client);
			this.clientService.checkEmail(client);
			this.clientService.edit(client, id);
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (InvalidClientException e) {
			req.setAttribute("message", "Erreur, veuillez saisir un client valide");
		}

		this.doGet(req,resp);

	}


}
