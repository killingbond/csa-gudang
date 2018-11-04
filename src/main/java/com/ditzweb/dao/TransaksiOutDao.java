package com.ditzweb.dao;

import java.util.Date;
import java.util.List;

import com.ditzweb.beans.TransaksiOut;


public interface TransaksiOutDao {
	public List<TransaksiOut> getAll();
	public TransaksiOut getById(int id);
	public void save(TransaksiOut transaksiOut);
	public void update(TransaksiOut transaksiOut);
	public void delete(int id);
	public List<TransaksiOut> getSearch(String id, String noBanPenjualan, String namaClient);
	public List<TransaksiOut> getAllSearch(Date tanggalDari, Date tanggalKe, String id, String noBanPenjualan,
			String namaClient);
}
