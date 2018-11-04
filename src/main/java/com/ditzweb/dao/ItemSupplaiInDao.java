package com.ditzweb.dao;

import java.util.List;

import com.ditzweb.beans.ItemSupplaiIn;



public interface ItemSupplaiInDao {

	public List<ItemSupplaiIn> getAll(int id);
	public ItemSupplaiIn getById(int id);
	public void save(ItemSupplaiIn itemSupplaiIn);
	public void update(ItemSupplaiIn itemSupplaiIn);
	public void delete(int id);
	public Double countStock(int id);
	public List<ItemSupplaiIn> getAllById(int id);
	public void updateHarga(ItemSupplaiIn itemSup);
}
