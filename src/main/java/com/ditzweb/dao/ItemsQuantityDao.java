package com.ditzweb.dao;

import java.util.List;


import com.ditzweb.beans.ItemsQuantity;


public interface ItemsQuantityDao {
	public List<ItemsQuantity> getAll();
	public ItemsQuantity getById(int id);
	public void save(ItemsQuantity itemsQty);
	public void update(ItemsQuantity itemsQty);
	public void delete(int id);
	public List<ItemsQuantity> getAllSearch(String id, String nama, String satuan, String status);
	public List<ItemsQuantity> getByItems(int item);
	public void updateBasic(ItemsQuantity itemsQty);
	
}
