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

import com.ditzweb.beans.Items;
import com.ditzweb.dao.ItemsDao;

public class ItemsController extends SelectorComposer<Component> {

	@WireVariable
	ItemsDao itemsDao = (ItemsDao) SpringUtil.getBean("itemsDao");
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Window window;

	private String id_items = "";
	private String nama_items = "";
	private String deskripsi_items = "";
	private String status_items = "";
	private String addDisabled = "false";
	private String visible = "true";

	private List<Items> allReordsInDB = null;

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
		allReordsInDB = itemsDao.getAll();
	}

	public List<Items> getAllReordsInDB() {
		return allReordsInDB;
	}

	public void setAllReordsInDB(List<Items> allReordsInDB) {
		this.allReordsInDB = allReordsInDB;
	}

	@Command
	public void add() {
		Items obj = new Items();
		HashMap<String, Items> dt = new HashMap<String, Items>();
		dt.put("data", obj);
		window = (Window) Executions.createComponents("/itemsadd.zul", null, dt);
		window.doModal();
	}

	@Command
	@NotifyChange("allReordsInDB")
	public void cari() {
		String status = "";
		if (status_items.equals("Aktif")) {
			status = "0";
		} else if (status_items.equals("UnAktif")) {
			status = "1";
		}
		allReordsInDB = itemsDao.getAllSearch(id_items, nama_items, deskripsi_items, status);
	}

	@Command
	public void updateModal(@BindingParam("obj") Items obj) {
		HashMap<String, Items> dt = new HashMap<String, Items>();
		dt.put("data", obj);
		window = (Window) Executions.createComponents("/itemsupdate.zul", null, dt);
		window.doModal();

	}

	@GlobalCommand
	@NotifyChange("allReordsInDB")
	public void updateItems(@BindingParam("obj") Items obj) {
		itemsDao.update(obj);
		window.detach();
		this.initSetup();
	}

	@GlobalCommand
	@NotifyChange("allReordsInDB")
	public void addItems(@BindingParam("obj") Items obj) {
		itemsDao.save(obj);
		window.detach();
		this.initSetup();
	}

	@Command
	@NotifyChange("allReordsInDB")
	public void delete(@BindingParam("obj") Items obj) {
		try {
			itemsDao.delete(obj.getId());
			this.initSetup();
		} catch (Exception e) {
			Messagebox.show("Tidak Dapat di Hapus!!!", "Error", Messagebox.OK, Messagebox.ERROR);
		}

	}

	public String getId_items() {
		return id_items;
	}

	public void setId_items(String id_items) {
		this.id_items = id_items;
	}

	public String getNama_items() {
		return nama_items;
	}

	public void setNama_items(String nama_items) {
		this.nama_items = nama_items;
	}

	public String getDeskripsi_items() {
		return deskripsi_items;
	}

	public void setDeskripsi_items(String deskripsi_items) {
		this.deskripsi_items = deskripsi_items;
	}

	public String getStatus_items() {
		return status_items;
	}

	public void setStatus_items(String status_items) {
		this.status_items = status_items;
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
