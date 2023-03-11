package com.epf.rentmanager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;

@Repository
public class VehicleDao {
	
	private static VehicleDao instance = null;
	private VehicleDao() {}
	//public static VehicleDao getInstance() {
	//	if(instance == null) {
	//		instance = new VehicleDao();
	//	}
	//	return instance;
	//}
	
	private static final String CREATE_VEHICLE_QUERY = "INSERT INTO Vehicle(constructeur, modele, nb_places) VALUES(?, ?, ?);";
	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLE_QUERY = "SELECT id, constructeur, modele, nb_places FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLES_QUERY = "SELECT id, constructeur, modele, nb_places FROM Vehicle;";
	private static final String COUNT_VEHICLES_QUERY = "SELECT COUNT(*) FROM Vehicle;";
	
	public long create(Vehicle vehicle) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(CREATE_VEHICLE_QUERY);
			stmt.setString(1, vehicle.getConstructeur());
			stmt.setString(2, vehicle.getModele());
			stmt.setInt(3, vehicle.getNb_places());
			stmt.execute();



		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return 0;
	}

	public long delete(Vehicle vehicle) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(DELETE_VEHICLE_QUERY);
			stmt.setLong(1, vehicle.getId());
			stmt.execute();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return 0;
	}

	public Vehicle findById(long id) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(FIND_VEHICLE_QUERY);
			stmt.setLong(1, id);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()){
				String constructeur = rs.getString("constructeur");
				String modele = rs.getString("modele");
				int nb_places = rs.getInt("nb_places");
				return new Vehicle(id, constructeur, modele, nb_places);
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return new Vehicle();
	}

	public List<Vehicle> findAll() throws DaoException {
		List<Vehicle> vehicles = new ArrayList<Vehicle>();
		try {

			Connection connection = ConnectionManager.getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(FIND_VEHICLES_QUERY);

			while (rs.next()){
				int id = rs.getInt("id");
				String constructeur = rs.getString("constructeur");
				String modele = rs.getString("modele");
				int nb_places = rs.getInt("nb_places");
				vehicles.add(new Vehicle(id, constructeur, modele, nb_places));
			}


		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}





		return vehicles;
		
	}

	public int count() throws DaoException {

		try {

			Connection connection = ConnectionManager.getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(COUNT_VEHICLES_QUERY);
			rs.next();
			return rs.getInt(1);


		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}


	}


}
