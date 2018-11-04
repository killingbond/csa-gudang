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

import com.ditzweb.beans.Quantity;
import com.ditzweb.dao.QuantityDao;

public class QuantityController extends SelectorComposer<Component> {

	@WireVariable
	QuantityDao quantityDao = (QuantityDao) SpringUtil.getBean("quantityDao");
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Window window;

	private String id = "";
	private String nama = "";
	private String deskripsi = "";
	private String status = "";
	private String addDisabled = "false";
	private String visible = "true";

	private List<Quantity> allReordsInDB = null;

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
		allReordsInDB = quantityDao.getAll();
	}

	public List<Quantity> getAllReordsInDB() {
		return allReordsInDB;
	}

	public void setAllReordsInDB(List<Quantity> allReordsInDB) {
		this.allReordsInDB = allReordsInDB;
	}

	@Command
	public void add() {
		Quantity obj = new Quantity();
		HashMap<String, Quantity> dt = new HashMap<String, Quantity>();
		dt.put("data", obj);
		window = (Window) Executions.createComponents("/quantityadd.zul", null, dt);
		window.doModal();
	}

	@Command
	@NotifyChange("allReordsInDB")
	public void cari() {
		String stats = "";
		if (status.equals("Aktif")) {
			stats = "0";
		} else if (status.equals("UnAktif")) {
			stats = "1";
		}
		allReordsInDB = quantityDao.getAllSearch(id, nama, deskripsi, stats);
	}

	@Command
	public void updateModal(@BindingParam("obj") Quantity obj) {
		HashMap<String, Quantity> dt = new HashMap<String, Quantity>();
		dt.put("data", obj);
		window = (Window) Executions.createComponents("/quantityupdate.zul", null, dt);
		window.doModal();

	}

	@GlobalCommand
	@NotifyChange("allReordsInDB")
	public void updateQuantity(@BindingParam("obj") Quantity obj) {
		quantityDao.update(obj);
		window.detach();
		this.initSetup();
	}

	@GlobalCommand
	@NotifyChange("allReordsInDB")
	public void addQuantity(@BindingParam("obj") Quantity obj) {
		quantityDao.save(obj);
		window.detach();
		this.initSetup();
	}

	@Command
	@NotifyChange("allReordsInDB")
	public void delete(@BindingParam("obj") Quantity obj) {
		try {
			quantityDao.delete(obj.getId());
			this.initSetup();
		} catch (Exception e) {
			Messagebox.show("Tidak Dapat di Hapus!!!", "Error", Messagebox.OK, Messagebox.ERROR);
		}

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
