package com.ditzweb.beans;


import java.util.Date;


import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {

	@Id
	private String username;
	private String password;
	private String nama;
	private String deskripsi;
	private String npk;
	private Status status;
	private String alamat;
	private String noKontak;
	private Date date;
	private String foto;

	public User() {}

	public User(String username, String password, String nama, String deskripsi, String npk, Status status,
			String alamat, String noKontak, Date date, String foto) {
		super();
		this.username = username;
		this.password = password;
		this.nama = nama;
		this.deskripsi = deskripsi;
		this.npk = npk;
		this.status = status;
		this.alamat = alamat;
		this.noKontak = noKontak;
		this.date = date;
		this.foto = foto;
	}








	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}






	public void setPassword(String password) {
		this.password = password;
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

	public String getNpk() {
		return npk;
	}

	public void setNpk(String npk) {
		this.npk = npk;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getAlamat() {
		return alamat;
	}

	public void setAlamat(String alamat) {
		this.alamat = alamat;
	}

	public String getNoKontak() {
		return noKontak;
	}

	public void setNoKontak(String noKontak) {
		this.noKontak = noKontak;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	
	
	
}
