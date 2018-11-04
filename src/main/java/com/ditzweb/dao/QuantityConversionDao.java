package com.ditzweb.dao;

import java.util.List;

import com.ditzweb.beans.QuantityConversion;


public interface QuantityConversionDao {
	public List<QuantityConversion> getAll();
	public QuantityConversion getById(int id);
	public void save(QuantityConversion qc);
	public void update(QuantityConversion qc);
	public void delete(int id);
	public QuantityConversion getByName(int dari,int ke);
}
