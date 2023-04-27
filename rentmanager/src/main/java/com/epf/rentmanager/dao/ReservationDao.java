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
import com.epf.rentmanager.exception.InvalidReservationException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationDao {

	private static ReservationDao instance = null;

	private ClientDao clientDao;
	private VehicleDao vehicleDao;

	public ReservationDao(ClientDao clientDao, VehicleDao vehicleDao){
		this.clientDao = clientDao;
		this.vehicleDao = vehicleDao;
	}
	private static final String CREATE_RESERVATION_QUERY = "INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(?, ?, ?, ?);";
	private static final String DELETE_RESERVATION_QUERY = "DELETE FROM Reservation WHERE id=?;";
	private static final String EDIT_RESERVATION_QUERY = "UPDATE Reservation SET client_id=?, vehicle_id=?, debut=?, fin=? WHERE id=?;";
	private static final String FIND_RESERVATIONS_BY_CLIENT_QUERY = "SELECT id, vehicle_id, debut, fin FROM Reservation WHERE client_id=?;";
	private static final String FIND_RESERVATIONS_BY_VEHICLE_QUERY = "SELECT id, client_id, debut, fin FROM Reservation WHERE vehicle_id=?;";
	private static final String FIND_RESERVATIONS_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation;";
	private static final String FIND_RESERVATIONS_BY_ID_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation WHERE id=?;";
	private static final String COUNT_RESERVATIONS_QUERY = "SELECT COUNT(*) FROM Reservation;";
	private static final String CHECK_RESERVATION_QUERY = "SELECT COUNT(*) AS count FROM Reservation WHERE vehicle_id = ? AND ((debut >= ? AND debut <= ?) OR (fin >= ? AND fin <= ?))";

	private static final String EDIT_CHECK_RESERVATION_QUERY = "SELECT COUNT(*) AS count FROM Reservation WHERE vehicle_id = ? AND ((debut >= ? AND debut <= ?) OR (fin >= ? AND fin <= ?)) AND id!=?";
	private static final String CHECK_AVAILABILITY_RESERVATION_QUERY = "SELECT COUNT(*) FROM Reservation WHERE vehicle_id = ? AND ? BETWEEN debut AND fin";
	private static final String EDIT_CHECK_AVAILABILITY_RESERVATION_QUERY = "SELECT COUNT(*) FROM Reservation WHERE vehicle_id = ? AND ? BETWEEN debut AND fin AND id!=?";
	public long create(Reservation reservation) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(CREATE_RESERVATION_QUERY);
			stmt.setLong(1, reservation.getClient().getId());
			stmt.setLong(2, reservation.getVehicle().getId());
			stmt.setDate(3, Date.valueOf(reservation.getDebut()));
			stmt.setDate(4, Date.valueOf(reservation.getFin()));

			stmt.execute();

			stmt.close();
			connection.close();

		} catch (SQLException e) {
			throw new DaoException();
		}

		return 0;
	}
	
	public long delete(Reservation reservation) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(DELETE_RESERVATION_QUERY);
			stmt.setLong(1, reservation.getId());
			stmt.execute();

			stmt.close();
			connection.close();

		} catch (SQLException e) {
			throw new DaoException();
		}

		return 0;
	}

	
	public List<Reservation> findByClientId(long clientId) throws DaoException {
		List<Reservation> reservations = new ArrayList<Reservation>();
		try {

			Connection connection = ConnectionManager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(FIND_RESERVATIONS_BY_CLIENT_QUERY);
			stmt.setLong(1, clientId);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()){
				int id = rs.getInt("id");

				Client client = this.clientDao.findById(clientId);
				int id_vehicle = rs.getInt("vehicle_id");
				Vehicle vehicle = this.vehicleDao.findById(id_vehicle);
				LocalDate debut = rs.getDate("debut").toLocalDate();
				LocalDate fin = rs.getDate("fin").toLocalDate();
				reservations.add(new Reservation(id, client,vehicle,debut,fin));
			}


			rs.close();
			stmt.close();
			connection.close();

		} catch (SQLException e) {
			throw new DaoException();
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
				Client client = this.clientDao.findById(id_client);
				Vehicle vehicle = this.vehicleDao.findById(vehicleId);
				LocalDate debut = rs.getDate("debut").toLocalDate();
				LocalDate fin = rs.getDate("fin").toLocalDate();
				reservations.add(new Reservation(id, client,vehicle,debut,fin));
			}

			rs.close();
			stmt.close();
			connection.close();

		} catch (SQLException e) {
			throw new DaoException();
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
				Client client = this.clientDao.findById(id_client);
				int id_vehicle = rs.getInt("vehicle_id");
				Vehicle vehicle = this.vehicleDao.findById(id_vehicle);
				LocalDate debut = rs.getDate("debut").toLocalDate();
				LocalDate fin = rs.getDate("fin").toLocalDate();
				reservations.add(new Reservation(id, client,vehicle,debut,fin));
			}

			rs.close();
			statement.close();
			connection.close();

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

	public Reservation findById (long id) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(FIND_RESERVATIONS_BY_ID_QUERY);
			stmt.setLong(1, id);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {

				int id_client = rs.getInt("client_id");
				Client client = this.clientDao.findById(id_client);
				int id_vehicle = rs.getInt("vehicle_id");
				Vehicle vehicle = this.vehicleDao.findById(id_vehicle);
				LocalDate debut = rs.getDate("debut").toLocalDate();
				LocalDate fin = rs.getDate("fin").toLocalDate();
				return new Reservation(id, client, vehicle, debut, fin);
			}

			rs.close();
			stmt.close();
			connection.close();

		} catch (SQLException e) {
			throw new DaoException();
		}


		return new Reservation();
	}

	public long edit (Reservation reservation, long id) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(EDIT_RESERVATION_QUERY);
			stmt.setLong(1, reservation.getClient().getId());
			stmt.setLong(2, reservation.getVehicle().getId());
			stmt.setDate(3, Date.valueOf(reservation.getDebut()));
			stmt.setDate(4, Date.valueOf(reservation.getFin()));
			stmt.setLong(5, id);

			stmt.execute();

			stmt.close();
			connection.close();

		} catch (SQLException e) {
			throw new DaoException();
		}

		return 0;
	}

	public long CheckFree (Reservation reservation) throws DaoException, InvalidReservationException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(CHECK_RESERVATION_QUERY);
			stmt.setLong(1, reservation.getVehicle().getId());
			stmt.setObject(2, reservation.getDebut());
			stmt.setObject(3, reservation.getFin());
			stmt.setObject(4, reservation.getDebut());
			stmt.setObject(5, reservation.getFin());
			ResultSet rs = stmt.executeQuery();

			rs.next();
			int number = rs.getInt(1);
			if (number>0){
				throw new InvalidReservationException();
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

	public long CheckFree (Reservation reservation, long id) throws DaoException, InvalidReservationException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(EDIT_CHECK_RESERVATION_QUERY);
			stmt.setLong(1, reservation.getVehicle().getId());
			stmt.setObject(2, reservation.getDebut());
			stmt.setObject(3, reservation.getFin());
			stmt.setObject(4, reservation.getDebut());
			stmt.setObject(5, reservation.getFin());
			stmt.setLong(6, id);
			ResultSet rs = stmt.executeQuery();

			rs.next();
			int number = rs.getInt(1);
			if (number>0){
				throw new InvalidReservationException();
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

	public void Check30Days (Reservation reservation) throws InvalidReservationException, DaoException {
		LocalDate[] periodes = new LocalDate[2];
		periodes[0] = reservation.getFin().minusDays(30);
		periodes[1] = reservation.getDebut();


		for (int i=0;i<periodes.length;i++){
			boolean isAvailable = false;
			for (LocalDate date = periodes[i]; date.isBefore(periodes[i].plusDays(30)); date = date.plusDays(1)) {

				if(date.isBefore(reservation.getDebut()) || date.isAfter(reservation.getFin())){

					if (CheckAvailability(reservation.getVehicle().getId(), date)){
						isAvailable = true;
						break;
					}

				}

			}
			if (!isAvailable){
				throw new InvalidReservationException();
			}

		}

	}

	public void Check30Days (Reservation reservation, long id) throws InvalidReservationException, DaoException {
		LocalDate[] periodes = new LocalDate[2];
		periodes[0] = reservation.getFin().minusDays(30);
		periodes[1] = reservation.getDebut();


		for (int i=0;i<periodes.length;i++){
			boolean isAvailable = false;
			for (LocalDate date = periodes[i]; date.isBefore(periodes[i].plusDays(30)); date = date.plusDays(1)) {

				if(date.isBefore(reservation.getDebut()) || date.isAfter(reservation.getFin())){

					if (CheckAvailability(reservation.getVehicle().getId(), date, id)){
						isAvailable = true;
						break;
					}

				}

			}
			if (!isAvailable){
				throw new InvalidReservationException();
			}

		}

	}
	private boolean CheckAvailability(long id, LocalDate jour) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(CHECK_AVAILABILITY_RESERVATION_QUERY);
			stmt.setLong(1, id);
			stmt.setObject(2, jour);
			ResultSet rs = stmt.executeQuery();

			rs.next();
			int number = rs.getInt(1);

			rs.close();
			stmt.close();
			connection.close();

			if (number>0) {
				return false;
			}else{
				return true;
			}


		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}

	private boolean CheckAvailability(long id, LocalDate jour, long idres) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(EDIT_CHECK_AVAILABILITY_RESERVATION_QUERY);
			stmt.setLong(1, id);
			stmt.setObject(2, jour);
			stmt.setLong(3, idres);
			ResultSet rs = stmt.executeQuery();

			rs.next();
			int number = rs.getInt(1);

			rs.close();
			stmt.close();
			connection.close();

			if (number>0) {
				return false;
			}else{
				return true;
			}


		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}
}
