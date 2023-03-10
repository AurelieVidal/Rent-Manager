package com.epf.rentmanager.service;

import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;

public class ClientService {

	private ClientDao clientDao;
	public static ClientService instance;
	
	private ClientService() {
		this.clientDao = ClientDao.getInstance();
	}
	
	public static ClientService getInstance() {
		if (instance == null) {
			instance = new ClientService();
		}
		
		return instance;
	}
	
	
	public long create(Client client) throws ServiceException {
		try {
			return clientDao.create(client);
		} catch (DaoException e) {
			throw new RuntimeException(e);
		}
	}

	public long delete(Client client) throws ServiceException {
		try {
			return clientDao.delete(client);
		} catch (DaoException e) {
			throw new RuntimeException(e);
		}


	}
	public Client findById(long id) throws ServiceException, DaoException {
		try {
			return ClientDao.getInstance().findById(id);
		} catch (DaoException e) {
			throw new DaoException();
		}
	}

	public List<Client> findAll() throws ServiceException, DaoException {
		try {
			return ClientDao.getInstance().findAll();
		} catch (DaoException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}


	public int count(){
		try {
			return ClientDao.getInstance().count();
		} catch (DaoException e) {
			throw new RuntimeException(e);
		}

	}

	
}
