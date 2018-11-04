package com.ditzweb.beans;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class TransaksiOut {
	@Id
	@GeneratedValue
	private int id;
	private String noBanPenjualan;
	@ManyToOne
	private Client namaClient;
	private String noKendaraan;
	private Date tanggal;
	@Transient	
	private List<ItemSupplaiOut> itemSupOut;
	
	public TransaksiOut() {}

	public TransaksiOut(int id, String noBanPenjualan, Client namaClient, String noKendaraan, Date tanggal,
			List<ItemSupplaiOut> itemSupOut) {
		super();
		this.id = id;
		this.noBanPenjualan = noBanPenjualan;
		this.namaClient = namaClient;
		this.noKendaraan = noKendaraan;
		this.tanggal = tanggal;
		this.itemSupOut = itemSupOut;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNoBanPenjualan() {
		return noBanPenjualan;
	}

	public void setNoBanPenjualan(String noBanPenjualan) {
		this.noBanPenjualan = noBanPenjualan;
	}

	public Client getNamaClient() {
		return namaClient;
	}

	public void setNamaClient(Client namaClient) {
		this.namaClient = namaClient;
	}

	public String getNoKendaraan() {
		return noKendaraan;
	}

	public void setNoKendaraan(String noKendaraan) {
		this.noKendaraan = noKendaraan;
	}

	public Date getTanggal() {
		return tanggal;
	}

	public void setTanggal(Date tanggal) {
		this.tanggal = tanggal;
	}

	public List<ItemSupplaiOut> getItemSupOut() {
		return itemSupOut;
	}

	public void setItemSupOut(List<ItemSupplaiOut> itemSupOut) {
		this.itemSupOut = itemSupOut;
	}
	
	
	
	
	
	
}
