package com.epf.rentmanager.service;


import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.InvalidReservationException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ReservationService {

	private ReservationDao reservationDao;
	public static ReservationService instance;


public ReservationService(ReservationDao reservationDao){
		this.reservationDao = reservationDao;
	}

	public long create(Reservation reservation) throws ServiceException {
		try {
			return reservationDao.create(reservation);
		} catch (DaoException e) {
			throw new ServiceException();
		}
	}

	public long delete(Reservation reservation) throws ServiceException {
		try {
			return reservationDao.delete(reservation);
		} catch (DaoException e) {
			throw new ServiceException();
		}


	}
	public Reservation findById(long id) throws ServiceException {
		try {
			return reservationDao.findById(id);
		} catch (DaoException e) {
			throw new ServiceException();
		}
	}



	public List<Reservation> findAll() throws ServiceException, DaoException {
		try {
			return reservationDao.findAll();
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}
	public List<Reservation> findReservationsByClientId(long id) throws ServiceException, DaoException {
		try {
			return reservationDao.findByClientId(id);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	public List<Reservation> findReservationsByVehicleId(long id) throws ServiceException, DaoException {
		try {
			return reservationDao.findResaByVehicleId(id);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	public int count() throws ServiceException {
		try {
			return reservationDao.count();
		} catch (DaoException e) {
			throw new ServiceException();
		}

	}

	public long edit(Reservation reservation, long id) throws ServiceException {
		try {
			return reservationDao.edit(reservation, id);
		} catch (DaoException e) {
			throw new ServiceException();
		}
	}

	public void checkFree (Reservation reservation) throws InvalidReservationException, ServiceException {
		try {
			reservationDao.CheckFree(reservation);
		} catch (DaoException e) {
			throw new ServiceException();
		}
	}

	public void checkFree (Reservation reservation, long id) throws InvalidReservationException, ServiceException {
		try {
			reservationDao.CheckFree(reservation, id);
		} catch (DaoException e) {
			throw new ServiceException();
		}
	}
	public void Check30Days (Reservation reservation) throws InvalidReservationException {
		try {
			reservationDao.Check30Days(reservation);
		} catch (DaoException e) {
			throw new RuntimeException(e);
		}
	}

	public void Check30Days (Reservation reservation, long id) throws InvalidReservationException {
		try {
			reservationDao.Check30Days(reservation, id);
		} catch (DaoException e) {
			throw new RuntimeException(e);
		}
	}

	
}
