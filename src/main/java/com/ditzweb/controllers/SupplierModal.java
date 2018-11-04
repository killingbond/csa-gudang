package com.ditzweb.controllers;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;

import com.ditzweb.beans.Status;
import com.ditzweb.beans.Supplier;

public class SupplierModal {

	private String id = "";
	private String nama = "";
	private String deskripsi = "";
	private String namaKontak = "";
	private String telpKontak = "";
	private Status status = Status.valueOf("Aktif");
	private Supplier supplier;

	@Init
	public void showData(@ExecutionArgParam("data") Supplier obj) {
		if (obj.getId() != null) {
			this.id = obj.getId();
			this.nama = obj.getNama();
			this.deskripsi = obj.getDeskripsi();
			this.namaKontak = obj.getNamaKontak();
			this.telpKontak = obj.getTelpKontak();
			this.status = obj.getStatus();
		}

	}

	@Command
	public void add() {
		supplier = new Supplier(this.id, this.nama, this.deskripsi, this.namaKontak, this.telpKontak, this.status);
		Map<String, Object> dt = new HashMap<String, Object>();
		dt.put("obj", supplier);
		BindUtils.postGlobalCommand(null, null, "addSupplier", dt);
	}
	
	@Command
	public void update() {
		supplier = new Supplier(this.id, this.nama, this.deskripsi, this.namaKontak, this.telpKontak, this.status);
		Map<String, Object> dt = new HashMap<String, Object>();
		dt.put("obj", supplier);
		BindUtils.postGlobalCommand(null, null, "updateSupplier", dt);
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
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
