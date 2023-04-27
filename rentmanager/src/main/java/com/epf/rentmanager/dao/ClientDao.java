package com.epf.rentmanager.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.epf.rentmanager.config.AppConfiguration;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.InvalidClientException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.persistence.ConnectionManager;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Repository;

@Repository
public class ClientDao {
	
	private static ClientDao instance = null;



	private ClientDao() {}
	
	private static final String CREATE_CLIENT_QUERY = "INSERT INTO Client(nom, prenom, email, naissance) VALUES(?, ?, ?, ?);";
	private static final String EDIT_CLIENT_QUERY = "UPDATE Client SET nom=?, prenom=?, email=?, naissance=? WHERE id=?;";
	private static final String DELETE_CLIENT_QUERY = "DELETE FROM Client WHERE id=?;";
	private static final String FIND_CLIENT_QUERY = "SELECT nom, prenom, email, naissance FROM Client WHERE id=?;";
	private static final String FIND_CLIENTS_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client;";
	private static final String COUNT_CLIENTS_QUERY = "SELECT COUNT(*) FROM Client;";
	private static final String CHECK_EMAIL_QUERY = "SELECT * FROM Client WHERE email=?;";
	public long create(Client client) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(CREATE_CLIENT_QUERY);
			stmt.setString(1, client.getNom());
			stmt.setString(2, client.getPrenom());
			stmt.setString(3, client.getEmail());
			stmt.setDate(4, Date.valueOf(client.getNaissance()));

			stmt.execute();

			stmt.close();
			connection.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return 0;
	}

	public long edit (Client client, long id) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(EDIT_CLIENT_QUERY);
			stmt.setString(1, client.getNom());
			stmt.setString(2, client.getPrenom());
			stmt.setString(3, client.getEmail());
			stmt.setDate(4, Date.valueOf(client.getNaissance()));
			stmt.setLong(5, id);

			stmt.execute();

			stmt.close();
			connection.close();

		} catch (SQLException e) {
			throw new DaoException();
		}

		return 0;
	}
	
	public long delete(Client client) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
			ReservationService reservationService = context.getBean(ReservationService.class);
			List<Reservation> reservations = reservationService.findAll();
			Iterator<Reservation> it = reservations.iterator();
			while (it.hasNext()){

				Reservation r = it.next();
				if (r.getClient().getId()==client.getId()){
					reservationService.delete(r);
				}
			}
			PreparedStatement stmt = connection.prepareStatement(DELETE_CLIENT_QUERY);
			stmt.setLong(1, client.getId());
			stmt.execute();


			stmt.close();
			connection.close();

		} catch (SQLException e) {
			throw new DaoException();
		} catch (ServiceException e) {
			throw new RuntimeException(e);
		}
		return 0;
	}

	public Client findById(long id) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(FIND_CLIENT_QUERY);
			stmt.setLong(1, id);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()){
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom");
				String email = rs.getString("email");
				LocalDate naissance = rs.getDate("naissance").toLocalDate();
				return new Client(id, nom, prenom, email, naissance);
			}

			rs.close();
			stmt.close();
			connection.close();

		} catch (SQLException e) {
			throw new DaoException();
		}


		return new Client();
	}

	public List<Client> findAll() throws DaoException {
		List<Client> clients = new ArrayList<Client>();
		try {

			Connection connection = ConnectionManager.getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(FIND_CLIENTS_QUERY);

			while (rs.next()){
				int id = rs.getInt("id");
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom");
				String email = rs.getString("email");
				LocalDate naissance = rs.getDate("naissance").toLocalDate();
				clients.add(new Client(id, nom, prenom, email, naissance));
			}

			rs.close();
			statement.close();
			connection.close();



		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}





		return clients;
	}

	public int count() throws DaoException {

		try {

			Connection connection = ConnectionManager.getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(COUNT_CLIENTS_QUERY);
			rs.next();
			int number = rs.getInt(1);

			rs.close();
			statement.close();
			connection.close();

			return number;





		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}


	}


	public long checkEmail (Client client) throws DaoException, InvalidClientException {
		try {

			Connection connection = ConnectionManager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(CHECK_EMAIL_QUERY);
			stmt.setString(1, client.getEmail());
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				throw new InvalidClientException();
			}


			rs.close();
			stmt.close();
			connection.close();

			return 0;



		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}

}
