package com.ditzweb.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class QuantityConversion {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@ManyToOne
	private Quantity konversiDari;
	@ManyToOne
	private Quantity konversiKe;
	private Double besarKonversi;
	private Status status;
	
	public QuantityConversion() {};
	
	public QuantityConversion(int id, Quantity konversiDari, Quantity konversiKe, Double besarKonversi, Status status) {
		super();
		this.id = id;
		this.konversiDari = konversiDari;
		this.konversiKe = konversiKe;
		this.besarKonversi = besarKonversi;
		this.status = status;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Quantity getKonversiDari() {
		return konversiDari;
	}
	public void setKonversiDari(Quantity konversiDari) {
		this.konversiDari = konversiDari;
	}
	public Quantity getKonversiKe() {
		return konversiKe;
	}
	public void setKonversiKe(Quantity konversiKe) {
		this.konversiKe = konversiKe;
	}
	public Double getBesarKonversi() {
		return besarKonversi;
	}
	public void setBesarKonversi(Double besarKonversi) {
		this.besarKonversi = besarKonversi;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	
	
	
}
