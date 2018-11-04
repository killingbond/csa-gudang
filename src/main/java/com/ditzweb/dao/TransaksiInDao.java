package com.ditzweb.dao;

import java.util.Date;
import java.util.List;

import com.ditzweb.beans.TransaksiIn;



public interface TransaksiInDao {
	public List<TransaksiIn> getAll();
	public TransaksiIn getById(int id);
	public void save(TransaksiIn transaksiIn);
	public void update(TransaksiIn transaksiIn);
	public void delete(int id);
	public List<TransaksiIn> getAllSearch(Date tanggalDari, Date tanggalKe, String idTransaksi, String noFaktur, String namaSupplier);
	public List<TransaksiIn> getSearch(String id, String noFaktur, String namaSupplier);
}
