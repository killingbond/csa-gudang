package com.ditzweb.dao;

import java.util.List;

import com.ditzweb.beans.Client;


public interface ClientDao {
	public List<Client> getAll();
	public Client getById(String id);
	public void save(Client client);
	public void update(Client client);
	public void delete(String id);
	public List<Client> getAllSearch(String id, String nama, String deskripsi, String status);
	public List<Client> getAllByName(String id);
}
