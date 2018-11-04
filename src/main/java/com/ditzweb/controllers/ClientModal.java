package com.ditzweb.controllers;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;

import com.ditzweb.beans.Client;
import com.ditzweb.beans.Status;

public class ClientModal {

	private String id = "";
	private String nama = "";
	private String deskripsi = "";
	private String namaKontak = "";
	private String telpKontak = "";
	private Status status = Status.valueOf("Aktif");
	private Client client;

	@Init
	public void showData(@ExecutionArgParam("data") Client obj) {
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
		client = new Client(this.id.toUpperCase(), this.nama, this.deskripsi, this.namaKontak, this.telpKontak, this.status);
		Map<String, Object> dt = new HashMap<String, Object>();
		dt.put("obj", client);
		BindUtils.postGlobalCommand(null, null, "addClient", dt);
	}
	
	@Command
	public void update() {
		client = new Client(this.id.toUpperCase(), this.nama, this.deskripsi, this.namaKontak, this.telpKontak, this.status);
		Map<String, Object> dt = new HashMap<String, Object>();
		dt.put("obj", client);
		BindUtils.postGlobalCommand(null, null, "updateClient", dt);
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
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
