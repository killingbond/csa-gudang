package com.ditzweb.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Quantity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String nama;
	private String deskripsi;
	private Status status;
	
	
	public Quantity() {}

	
	public Quantity(int id, String nama, String deskripsi, Status status) {
		super();
		this.id = id;
		this.nama = nama;
		this.deskripsi = deskripsi;
		this.status = status;
	}




	public int getId() {
		return id;
	}


	public void setId(int id) {
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


	public Status getStatus() {
		return status;
	}


	public void setStatus(Status status) {
		this.status = status;
	}

	
	
	
}