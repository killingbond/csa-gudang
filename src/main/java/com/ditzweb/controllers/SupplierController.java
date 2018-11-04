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

import com.ditzweb.beans.Supplier;
import com.ditzweb.dao.SupplierDao;

public class SupplierController extends SelectorComposer<Component> {

	@WireVariable
	SupplierDao supplierDao = (SupplierDao) SpringUtil.getBean("supplierDao");
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Window window;

	private String id_supplier = "";
	private String nama_supplier = "";
	private String deskripsi_supplier = "";
	private String status_supplier = "";
	private String addDisabled = "false";
	private String visible = "true";

	private List<Supplier> allReordsInDB = null;

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
		allReordsInDB = supplierDao.getAll();
	}

	public List<Supplier> getAllReordsInDB() {
		return allReordsInDB;
	}

	public void setAllReordsInDB(List<Supplier> allReordsInDB) {
		this.allReordsInDB = allReordsInDB;
	}

	@Command
	public void add() {
		Supplier obj = new Supplier();
		HashMap<String, Supplier> dt = new HashMap<String, Supplier>();
		dt.put("data", obj);
		window = (Window) Executions.createComponents("/supplieradd.zul", null, dt);
		window.doModal();
	}

	@Command
	@NotifyChange("allReordsInDB")
	public void cari() {
		String status = "";
		if (status_supplier.equals("Aktif")) {
			status = "0";
		} else if (status_supplier.equals("UnAktif")) {
			status = "1";
		}
		allReordsInDB = supplierDao.getAllSearch(id_supplier, nama_supplier, deskripsi_supplier, status);
	}

	@Command
	public void updateModal(@BindingParam("obj") Supplier obj) {
		HashMap<String, Supplier> dt = new HashMap<String, Supplier>();
		dt.put("data", obj);
		window = (Window) Executions.createComponents("/supplierupdate.zul", null, dt);
		window.doModal();

	}

	@GlobalCommand
	@NotifyChange("allReordsInDB")
	public void updateSupplier(@BindingParam("obj") Supplier obj) {
		supplierDao.update(obj);
		window.detach();
		this.initSetup();
	}

	@GlobalCommand
	@NotifyChange("allReordsInDB")
	public void addSupplier(@BindingParam("obj") Supplier obj) {
		supplierDao.save(obj);
		window.detach();
		this.initSetup();
	}

	@Command
	@NotifyChange("allReordsInDB")
	public void delete(@BindingParam("obj") Supplier obj) {
		try {
			supplierDao.delete(obj.getId());
			this.initSetup();
		} catch (Exception e) {
			Messagebox.show("Tidak Dapat di Hapus!!!", "Error", Messagebox.OK, Messagebox.ERROR);
		}

	}

	public String getId_supplier() {
		return id_supplier;
	}

	public void setId_supplier(String id_supplier) {
		this.id_supplier = id_supplier;
	}

	public String getNama_supplier() {
		return nama_supplier;
	}

	public void setNama_supplier(String nama_supplier) {
		this.nama_supplier = nama_supplier;
	}

	public String getDeskripsi_supplier() {
		return deskripsi_supplier;
	}

	public void setDeskripsi_supplier(String deskripsi_supplier) {
		this.deskripsi_supplier = deskripsi_supplier;
	}

	public String getStatus_supplier() {
		return status_supplier;
	}

	public void setStatus_supplier(String status_supplier) {
		this.status_supplier = status_supplier;
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
