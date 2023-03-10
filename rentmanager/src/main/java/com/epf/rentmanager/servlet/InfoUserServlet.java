package com.epf.rentmanager.servlet;

import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.VehicleService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/users/details")
public class InfoUserServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;


    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {



        long id = Long.parseLong(req.getParameter("id"));



            //recup : nombre de reservations faites par l'utilisateur
        //nombre de voitures
        //liste des réservation faites par les client
        //relever dans cette liste la liste des voitures et garder seulement

        //recup l'ID pour trouver le nom , prenom et mail de l'utilisateur



        this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/details.jsp").forward(req,resp);
    }


}

