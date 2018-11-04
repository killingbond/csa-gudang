package com.ditzweb.dao;

import java.util.List;

import com.ditzweb.beans.Items;


public interface ItemsDao {
	public List<Items> getAll();
	public Items getById(int id);
	public void save(Items items);
	public void update(Items items);
	public void delete(int id);
	public List<Items> getAllSearch(String id,String nama, String deskripsi,String status);
}
