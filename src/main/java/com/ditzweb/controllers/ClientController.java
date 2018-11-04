package com.ditzweb.controllers;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.ditzweb.beans.Client;
import com.ditzweb.dao.ClientDao;

public class ClientController extends SelectorComposer<Component> {

	@WireVariable
	ClientDao clientDao = (ClientDao) SpringUtil.getBean("clientDao");
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Window window;

	private String id_client = "";
	private String nama_client = "";
	private String deskripsi_client = "";
	private String status_client = "";
	private String addDisabled = "false";
	private String visible = "true";

	private List<Client> allReordsInDB = null;

	@AfterCompose
	public void initSetup() {

		Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) SecurityContextHolder.getContext()
				.getAuthentication().getAuthorities();
		boolean hasRoleAdmin = false;
		boolean hasRoleOwner = false;
		boolean hasRoleMarketing = false;
		for (GrantedAuthority authority : authorities) {
			hasRoleAdmin = authority.getAuthority().equals("ROLE_ADMIN");
			hasRoleOwner = authority.getAuthority().equals("ROLE_OWNER");
			hasRoleMarketing = authority.getAuthority().equals("ROLE_MARKETING");
			if (hasRoleOwner || hasRoleMarketing) {
				this.addDisabled = "true";
				this.visible = "false";
			}
			if (hasRoleAdmin) {
				this.addDisabled = "false";
				this.visible = "true";
				break;
			}
		}

		allReordsInDB = clientDao.getAll();
	}

	public List<Client> getAllReordsInDB() {
		return allReordsInDB;
	}

	public void setAllReordsInDB(List<Client> allReordsInDB) {
		this.allReordsInDB = allReordsInDB;
	}

	@Command
	public void add() {
		Client obj = new Client();
		HashMap<String, Client> dt = new HashMap<String, Client>();
		dt.put("data", obj);
		window = (Window) Executions.createComponents("/clientadd.zul", null, dt);
		window.doModal();
	}

	@Command
	@NotifyChange("allReordsInDB")
	public void cari() {
		String status = "";
		if (status_client.equals("Aktif")) {
			status = "0";
		} else if (status_client.equals("UnAktif")) {
			status = "1";
		}
		allReordsInDB = clientDao.getAllSearch(id_client, nama_client, deskripsi_client, status);
	}

	@Command
	public void updateModal(@BindingParam("obj") Client obj) {
		HashMap<String, Client> dt = new HashMap<String, Client>();
		dt.put("data", obj);
		window = (Window) Executions.createComponents("/clientupdate.zul", null, dt);
		window.doModal();
	}

	@GlobalCommand
	@NotifyChange("allReordsInDB")
	public void updateClient(@BindingParam("obj") Client obj) {
		clientDao.update(obj);
		window.detach();
		this.initSetup();
	}

	@GlobalCommand
	@NotifyChange("allReordsInDB")
	public void addClient(@BindingParam("obj") Client obj) {
		clientDao.save(obj);
		window.detach();
		this.initSetup();
	}

	@Command
	@NotifyChange("allReordsInDB")
	public void delete(@BindingParam("obj") Client obj) {
		try {
			clientDao.delete(obj.getId());
			this.initSetup();
		} catch (Exception e) {
			Messagebox.show("Tidak Dapat di Hapus!!!", "Error", Messagebox.OK, Messagebox.ERROR);
		}

	}

	public String getId_client() {
		return id_client;
	}

	public void setId_client(String id_client) {
		this.id_client = id_client;
	}

	public String getNama_client() {
		return nama_client;
	}

	public void setNama_client(String nama_client) {
		this.nama_client = nama_client;
	}

	public String getDeskripsi_client() {
		return deskripsi_client;
	}

	public void setDeskripsi_client(String deskripsi_client) {
		this.deskripsi_client = deskripsi_client;
	}

	public String getStatus_client() {
		return status_client;
	}

	public void setStatus_client(String status_client) {
		this.status_client = status_client;
	}

	public String getAddDisabled() {
		return addDisabled;
	}

	public void setAddDisabled(String addDisabled) {
		this.addDisabled = addDisabled;
	}

	public String getVisible() {
		return visible;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}

}
