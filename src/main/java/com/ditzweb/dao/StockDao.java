package com.ditzweb.dao;

import java.util.List;

import com.ditzweb.beans.Stock;

public interface StockDao {
	public List<Stock> getAll();
	public void save(Stock stock);
	public Stock getStock(int id);
	public void updateStock(Stock stock);
	public List<Stock> getAllSearch(String id, String namaBarang);
}
