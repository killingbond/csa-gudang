package com.ditzweb.dao;

import java.util.List;

import com.ditzweb.beans.ItemSupplaiOut;



public interface ItemSupplaiOutDao {
	public List<ItemSupplaiOut> getAll(int id);
	public ItemSupplaiOut getById(int id);
	public void save(ItemSupplaiOut itemSupplaiOut);
	public void update(ItemSupplaiOut itemSupplaiOut);
	public void delete(int id);
	public List<ItemSupplaiOut> getAllById(int id);
	public void updateHarga(ItemSupplaiOut itemSup);
}
