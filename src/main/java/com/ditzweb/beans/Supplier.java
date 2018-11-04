package com.ditzweb.beans;




import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class Supplier {
	
	@Id
	private String id;
	private String nama;
	private String deskripsi;
	private String namaKontak;
	private String telpKontak;
	private Status status;
	
	public Supplier() {}
	
	
	public Supplier(String id, String nama, String deskripsi, String namaKontak,String telpKontak, Status status) {
		super();
		this.id = id;
		this.nama = nama;
		this.deskripsi = deskripsi;
		this.namaKontak = namaKontak;
		this.telpKontak = telpKontak;
		this.status = status;
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNama() {
		return nama;
	}
	public void setNama(String nama) {
		this.nama = nama;
	}
	public String getDeskripsi() {
		return deskripsi;
	}
	public void setDeskripsi(String deskripsi) {
		this.deskripsi = deskripsi;
	}
	public String getNamaKontak() {
		return namaKontak;
	}
	public void setNamaKontak(String namaKontak) {
		this.namaKontak = namaKontak;
	}
	public String getTelpKontak() {
		return telpKontak;
	}
	public void setTelpKontak(String telpKontak) {
		this.telpKontak = telpKontak;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	
	
}
