package com.ditzweb.beans;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Items {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String nama;
	private String deskripsi;
	private Status status;
	private Long file;
	
	public Items() {}

	
	
	public Items(int id, String nama, String deskripsi, Status status, Long file) {
		super();
		this.id = id;
		this.nama = nama;
		this.deskripsi = deskripsi;
		this.status = status;
		this.file = file;
	}



	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
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



	public Long getFile() {
		return file;
	}

	public void setFile(Long file) {
		this.file = file;
	}
	


}
