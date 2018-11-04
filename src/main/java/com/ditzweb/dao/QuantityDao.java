package com.ditzweb.dao;

import java.util.List;

import com.ditzweb.beans.Quantity;


public interface QuantityDao {
	public List<Quantity> getAll();
	public Quantity getById(int id);
	public void save(Quantity qty);
	public void update(Quantity qty);
	public void delete(int id);
	public List<Quantity> getAllSearch(String id, String nama, String satuan,String status);
	public List<Quantity> getAllByName(String name);
}
