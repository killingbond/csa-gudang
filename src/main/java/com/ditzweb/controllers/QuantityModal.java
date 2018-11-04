package com.ditzweb.controllers;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;

import com.ditzweb.beans.Quantity;
import com.ditzweb.beans.Status;

public class QuantityModal {
	
	
	private int id = 0;
	private String nama = "";
	private String deskripsi = "";
	private Status status = Status.valueOf("Aktif");
	private Quantity quantity;
	

	@Init
	public void showData(@ExecutionArgParam("data") Quantity obj) {
		this.id = obj.getId();
		this.nama = obj.getNama();
		this.deskripsi = obj.getDeskripsi();
		if (obj.getStatus() != null) {
			this.status = obj.getStatus();
		}

	}

	@Command
	public void add() {
		quantity = new Quantity();
		quantity.setId(this.id);
		quantity.setNama(this.nama.toUpperCase());
		quantity.setDeskripsi(this.deskripsi);
		quantity.setStatus(this.status);
		Map<String, Object> dt = new HashMap<String, Object>();
		dt.put("obj", quantity);
		BindUtils.postGlobalCommand(null, null, "addQuantity", dt);

	}
	
	@Command
	public void update() {
		quantity = new Quantity();
		quantity.setId(this.id);
		quantity.setNama(this.nama.toUpperCase());
		quantity.setDeskripsi(this.deskripsi);
		quantity.setStatus(this.status);
		Map<String, Object> dt = new HashMap<String, Object>();
		dt.put("obj", quantity);
		BindUtils.postGlobalCommand(null, null, "updateQuantity", dt);

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

	public Quantity getQuantity() {
		return quantity;
	}

	public void setQuantity(Quantity quantity) {
		this.quantity = quantity;
	}

}
