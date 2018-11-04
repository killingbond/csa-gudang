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

import com.ditzweb.beans.ItemsQuantity;
import com.ditzweb.dao.ItemsQuantityDao;

public class ItemsQuantityController extends SelectorComposer<Component> {

	@WireVariable
	ItemsQuantityDao itemsQuantityDao = (ItemsQuantityDao) SpringUtil.getBean("itemsQuantityDao");
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

	private List<ItemsQuantity> allReordsInDB = null;

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
		allReordsInDB = itemsQuantityDao.getAll();
	}

	public List<ItemsQuantity> getAllReordsInDB() {
		return allReordsInDB;
	}

	public void setAllReordsInDB(List<ItemsQuantity> allReordsInDB) {
		this.allReordsInDB = allReordsInDB;
	}

	@Command
	public void add() {
		ItemsQuantity obj = new ItemsQuantity();
		HashMap<String, ItemsQuantity> dt = new HashMap<String, ItemsQuantity>();
		dt.put("data", obj);
		window = (Window) Executions.createComponents("/itemsquantityadd.zul", null, dt);
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
		allReordsInDB = itemsQuantityDao.getAllSearch(id, namaBarang, namaSatuan, stats);
	}

	@Command
	public void updateModal(@BindingParam("obj") ItemsQuantity obj) {
		HashMap<String, ItemsQuantity> dt = new HashMap<String, ItemsQuantity>();
		dt.put("data", obj);
		window = (Window) Executions.createComponents("/itemsquantityupdate.zul", null, dt);
		window.doModal();

	}

	@GlobalCommand
	@NotifyChange("allReordsInDB")
	public void updateItemsQuantity(@BindingParam("obj") ItemsQuantity obj) {
		itemsQuantityDao.update(obj);
		window.detach();
		allReordsInDB = itemsQuantityDao.getAllSearch(Integer.toString(obj.getItem().getId()), namaBarang, namaSatuan,
				"0");
		Executions.sendRedirect("itemsquantity.zul");
	}

	@GlobalCommand
	@NotifyChange("allReordsInDB")
	public void addItemsQuantity(@BindingParam("obj") ItemsQuantity obj) {
		itemsQuantityDao.save(obj);
		window.detach();
		this.initSetup();
	}

	@Command
	@NotifyChange("allReordsInDB")
	public void delete(@BindingParam("obj") ItemsQuantity obj) {
		if (obj.getQty().getId() == obj.getSatuanDasar().getSatuanDasar().getId()) {
			Messagebox.show("Satuan Dasar Tidak Dapat di Hapus","Error", Messagebox.OK, Messagebox.ERROR);
		} else {
			try {
				itemsQuantityDao.delete(obj.getId());
				this.initSetup();
			} catch (Exception e) {
				Messagebox.show("Tidak Dapat di Hapus!!!","Error", Messagebox.OK, Messagebox.ERROR);
			}
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
