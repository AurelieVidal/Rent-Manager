package com.epf.rentmanager.service;

import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.VehicleDao;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {

	private VehicleDao vehicleDao;
	public static VehicleService instance;
	
	private VehicleService() {
		this.vehicleDao = VehicleDao.getInstance();
	}
	private VehicleService(VehicleDao vehicleDao){
		this.vehicleDao =vehicleDao;
	}

	
	//public static VehicleService getInstance() {
	//	if (instance == null) {
	//		instance = new VehicleService();
	//	}
		
	//	return instance;
	//}
	
	
	public long create(Vehicle vehicle) throws ServiceException {
		try {
			return vehicleDao.create(vehicle);
		} catch (DaoException e) {
			throw new RuntimeException(e);
		}
	}

	public Vehicle findById(long id) throws ServiceException {
		try {
			return VehicleDao.getInstance().findById(id);
		} catch (DaoException e) {
			throw new RuntimeException(e);
		}
	}

	public long delete(Vehicle vehicle) throws ServiceException {
		try {
			return vehicleDao.delete(vehicle);
		} catch (DaoException e) {
			throw new RuntimeException(e);
		}


	}
	public List<Vehicle> findAll() throws ServiceException, DaoException {
		try {
			return VehicleDao.getInstance().findAll();
		} catch (DaoException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}


	public int count(){
		try {
			return VehicleDao.getInstance().count();
		} catch (DaoException e) {
			throw new RuntimeException(e);
		}

	}


}
