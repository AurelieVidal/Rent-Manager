package com.epf.rentmanager.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.persistence.ConnectionManager;

public class ReservationDao {

	private static ReservationDao instance = null;
	public ReservationDao() {}
	public static ReservationDao getInstance() {
		if(instance == null) {
			instance = new ReservationDao();
		}
		return instance;
	}
	
	private static final String CREATE_RESERVATION_QUERY = "INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(?, ?, ?, ?);";
	private static final String DELETE_RESERVATION_QUERY = "DELETE FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATIONS_BY_CLIENT_QUERY = "SELECT id, vehicle_id, debut, fin FROM Reservation WHERE client_id=?;";
	private static final String FIND_RESERVATIONS_BY_VEHICLE_QUERY = "SELECT id, client_id, debut, fin FROM Reservation WHERE vehicle_id=?;";
	private static final String FIND_RESERVATIONS_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation;";
	private static final String COUNT_RESERVATIONS_QUERY = "SELECT COUNT(*) FROM Reservation;";
		
	public long create(Reservation reservation) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(CREATE_RESERVATION_QUERY);
			stmt.setLong(1, reservation.getClient().getId());
			stmt.setLong(2, reservation.getVehicle().getId());
			stmt.setDate(3, Date.valueOf(reservation.getDebut()));
			stmt.setDate(4, Date.valueOf(reservation.getFin()));

			stmt.execute();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return 0;
	}
	
	public long delete(Reservation reservation) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(DELETE_RESERVATION_QUERY);
			stmt.setLong(1, reservation.getId());
			stmt.execute();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return 0;
	}

	
	public List<Reservation> findResaByClientId(long clientId) throws DaoException {
		List<Reservation> reservations = new ArrayList<Reservation>();
		try {

			Connection connection = ConnectionManager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(FIND_RESERVATIONS_BY_CLIENT_QUERY);
			stmt.setLong(1, clientId);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()){
				int id = rs.getInt("id");
				int id_client = rs.getInt("client_id");
				Client client = ClientDao.getInstance().findById(id_client);
				int id_vehicle = rs.getInt("vehicle_id");
				Vehicle vehicle = VehicleDao.getInstance().findById(id_vehicle);
				LocalDate debut = rs.getDate("debut").toLocalDate();
				LocalDate fin = rs.getDate("fin").toLocalDate();
				reservations.add(new Reservation(id, client,vehicle,debut,fin));
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return reservations;
	}
	
	public List<Reservation> findResaByVehicleId(long vehicleId) throws DaoException {
		List<Reservation> reservations = new ArrayList<Reservation>();
		try {

			Connection connection = ConnectionManager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(FIND_RESERVATIONS_BY_VEHICLE_QUERY);
			stmt.setLong(1, vehicleId);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()){
				int id = rs.getInt("id");
				int id_client = rs.getInt("client_id");
				Client client = ClientDao.getInstance().findById(id_client);
				int id_vehicle = rs.getInt("vehicle_id");
				Vehicle vehicle = VehicleDao.getInstance().findById(id_vehicle);
				LocalDate debut = rs.getDate("debut").toLocalDate();
				LocalDate fin = rs.getDate("fin").toLocalDate();
				reservations.add(new Reservation(id, client,vehicle,debut,fin));
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return reservations;
	}

	public List<Reservation> findAll() throws DaoException {
		List<Reservation> reservations = new ArrayList<Reservation>();
		try {

			Connection connection = ConnectionManager.getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(FIND_RESERVATIONS_QUERY);

			while (rs.next()){
				int id = rs.getInt("id");
				int id_client = rs.getInt("client_id");
				Client client = ClientDao.getInstance().findById(id_client);
				int id_vehicle = rs.getInt("vehicle_id");
				Vehicle vehicle = VehicleDao.getInstance().findById(id_vehicle);
				LocalDate debut = rs.getDate("debut").toLocalDate();
				LocalDate fin = rs.getDate("fin").toLocalDate();
				reservations.add(new Reservation(id, client,vehicle,debut,fin));
			}


		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}





		return reservations;
	}


	public int count() throws DaoException {

		try {

			Connection connection = ConnectionManager.getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(COUNT_RESERVATIONS_QUERY);
			rs.next();
			return rs.getInt(1);


		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}

	public Reservation findById (long id){
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(FIND_RESERVATIONS_QUERY);
			stmt.setLong(1, id);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {

				int id_client = rs.getInt("client_id");
				Client client = ClientDao.getInstance().findById(id_client);
				int id_vehicle = rs.getInt("vehicle_id");
				Vehicle vehicle = VehicleDao.getInstance().findById(id_vehicle);
				LocalDate debut = rs.getDate("debut").toLocalDate();
				LocalDate fin = rs.getDate("fin").toLocalDate();
				return new Reservation(id, client, vehicle, debut, fin);
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} catch (DaoException e) {
			throw new RuntimeException(e);
		}


		return new Reservation();
	}




}
