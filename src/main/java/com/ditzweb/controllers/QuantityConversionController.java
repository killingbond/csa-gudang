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

import com.ditzweb.beans.QuantityConversion;
import com.ditzweb.dao.QuantityConversionDao;

public class QuantityConversionController extends SelectorComposer<Component> {

	@WireVariable
	QuantityConversionDao quantityConversionDao = (QuantityConversionDao) SpringUtil.getBean("quantityConversionDao");
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Window window;

	private String id = "";
	private String namaBarang = "";
	private String namaSatuan = "";
	private String status = "";
	private String addDisabled = "false";
	private String visible = "true";

	private List<QuantityConversion> allReordsInDB = null;

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
		allReordsInDB = quantityConversionDao.getAll();
	}

	public List<QuantityConversion> getAllReordsInDB() {
		return allReordsInDB;
	}

	public void setAllReordsInDB(List<QuantityConversion> allReordsInDB) {
		this.allReordsInDB = allReordsInDB;
	}

	@Command
	public void add() {
		QuantityConversion obj = new QuantityConversion();
		HashMap<String, QuantityConversion> dt = new HashMap<String, QuantityConversion>();
		dt.put("data", obj);
		window = (Window) Executions.createComponents("/quantityconversionadd.zul", null, dt);
		window.doModal();
	}

	@Command
	@NotifyChange("allReordsInDB")
	public void cari() {
		/*
		 * String stats = ""; if (status.equals("Aktif")) { stats = "0"; } else if
		 * (status.equals("UnAktif")) { stats = "1"; } allReordsInDB =
		 * quantityConversionDao.getAllSearch(id, namaBarang, namaSatuan, stats);
		 */
	}

	@Command
	public void updateModal(@BindingParam("obj") QuantityConversion obj) {
		HashMap<String, QuantityConversion> dt = new HashMap<String, QuantityConversion>();
		dt.put("data", obj);
		window = (Window) Executions.createComponents("/quantityconversionupdate.zul", null, dt);
		window.doModal();

	}

	@GlobalCommand
	@NotifyChange("allReordsInDB")
	public void updateQuantityConversion(@BindingParam("obj") QuantityConversion obj) {
		quantityConversionDao.update(obj);
		window.detach();
		this.initSetup();
	}

	@GlobalCommand
	@NotifyChange("allReordsInDB")
	public void addQuantityConversion(@BindingParam("obj") QuantityConversion obj) {
		quantityConversionDao.save(obj);
		window.detach();
		this.initSetup();
	}

	
	@Command
	@NotifyChange("allReordsInDB")
	public void delete(@BindingParam("obj")  QuantityConversion obj) {
		try {
			quantityConversionDao.delete(obj.getId());
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

	public String getNamaBarang() {
		return namaBarang;
	}

	public void setNamaBarang(String namaBarang) {
		this.namaBarang = namaBarang;
	}

	public String getNamaSatuan() {
		return namaSatuan;
	}

	public void setNamaSatuan(String namaSatuan) {
		this.namaSatuan = namaSatuan;
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
