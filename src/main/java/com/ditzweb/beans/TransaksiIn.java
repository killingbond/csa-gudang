package com.ditzweb.beans;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;





@Entity
public class TransaksiIn {
	
	@Id
	@GeneratedValue
	private int id;
	private String noFaktur;
	@ManyToOne
	private Supplier namaSupplier;
	private String noKendaraan;
	private Date tanggal;
	@Transient	
	private List<ItemSupplaiIn> itemSupIn;
	
	public TransaksiIn() {};
	
	
	
	public TransaksiIn(int id, String noFaktur, Supplier namaSupplier, String noKendaraan, Date tanggal,
			List<ItemSupplaiIn> itemSupIn) {
		super();
		this.id = id;
		this.noFaktur = noFaktur;
		this.namaSupplier = namaSupplier;
		this.noKendaraan = noKendaraan;
		this.tanggal = tanggal;
		this.itemSupIn = itemSupIn;
	}



	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNoFaktur() {
		return noFaktur;
	}
	public void setNoFaktur(String noFaktur) {
		this.noFaktur = noFaktur;
	}
	public Supplier getNamaSupplier() {
		return namaSupplier;
	}
	public void setNamaSupplier(Supplier namaSupplier) {
		this.namaSupplier = namaSupplier;
	}
	public Date getTanggal() {
		return tanggal;
	}
	public void setTanggal(Date tanggal) {
		this.tanggal = tanggal;
	}

	public String getNoKendaraan() {
		return noKendaraan;
	}

	public void setNoKendaraan(String noKendaraan) {
		this.noKendaraan = noKendaraan;
	}

	public List<ItemSupplaiIn> getItemSupIn() {
		return itemSupIn;
	}


	public void setItemSupIn(List<ItemSupplaiIn> itemSupIn) {
		this.itemSupIn = itemSupIn;
	}
	
	
	
}
