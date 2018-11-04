package com.ditzweb.dao;

import java.util.List;

import com.ditzweb.beans.Supplier;

public interface SupplierDao {
	public List<Supplier> getAll();
	public Supplier getById(String id);
	public void save(Supplier supplier);
	public void update(Supplier supplier);
	public void delete(String id);
	public List<Supplier> getAllSearch(String id, String nama, String deskripsi,String status);
	public List<Supplier> getAllByName(String id);
}
