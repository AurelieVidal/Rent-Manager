package com.epf.rentmanager.service;


import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;

import java.util.List;

public class ReservationService {

	private ReservationDao reservationDao;
	public static ReservationService instance;

	private ReservationService() {
		this.reservationDao = ReservationDao.getInstance();
	}
	
	public static ReservationService getInstance() {
		if (instance == null) {
			instance = new ReservationService();
		}
		
		return instance;
	}
	
	
	public long create(Reservation reservation) throws ServiceException {
		try {
			return reservationDao.create(reservation);
		} catch (DaoException e) {
			throw new RuntimeException(e);
		}
	}

	public long delete(Reservation reservation) throws ServiceException {
		try {
			return reservationDao.delete(reservation);
		} catch (DaoException e) {
			throw new RuntimeException(e);
		}


	}
	public Reservation findById(long id) throws ServiceException, DaoException {
		return ReservationDao.getInstance().findById(id);
	}

	public List<Reservation> findAll() throws ServiceException, DaoException {
		try {
			return ReservationDao.getInstance().findAll();
		} catch (DaoException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}


	public int count(){
		try {
			return ReservationDao.getInstance().count();
		} catch (DaoException e) {
			throw new RuntimeException(e);
		}

	}

	
}
