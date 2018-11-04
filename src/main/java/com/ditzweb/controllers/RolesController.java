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

import com.ditzweb.beans.Roles;
import com.ditzweb.dao.RolesDao;

public class RolesController extends SelectorComposer<Component> {

	@WireVariable
	RolesDao rolesDao = (RolesDao) SpringUtil.getBean("rolesDao");
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Window window;

	private String username = "";
	private String nama = "";
	private String roles = "";
	private String status = "";
	private String addDisabled = "false";
	private String visible = "true";

	private List<Roles> allReordsInDB = null;

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
		allReordsInDB = rolesDao.getAll();
	}

	public List<Roles> getAllReordsInDB() {
		return allReordsInDB;
	}

	public void setAllReordsInDB(List<Roles> allReordsInDB) {
		this.allReordsInDB = allReordsInDB;
	}

	@Command
	public void add() {
		Roles obj = new Roles();
		HashMap<String, Roles> dt = new HashMap<String, Roles>();
		dt.put("data", obj);
		window = (Window) Executions.createComponents("/rolesadd.zul", null, dt);
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
		allReordsInDB = rolesDao.getlAllSearch(username, nama, roles, stats);
	}

	@Command
	public void updateModal(@BindingParam("obj") Roles obj) {
		HashMap<String, Roles> dt = new HashMap<String, Roles>();
		dt.put("data", obj);
		window = (Window) Executions.createComponents("/rolesupdate.zul", null, dt);
		window.doModal();

	}

	@GlobalCommand
	@NotifyChange("allReordsInDB")
	public void updateRoles(@BindingParam("obj") Roles obj) {
		rolesDao.update(obj);
		window.detach();
		Executions.sendRedirect("roles.zul");
	}

	@GlobalCommand
	@NotifyChange("allReordsInDB")
	public void addRoles(@BindingParam("obj") Roles obj) {
		rolesDao.save(obj);
		window.detach();
		this.initSetup();
	}

	@Command
	@NotifyChange("allReordsInDB")
	public void delete(@BindingParam("obj") Roles obj) {
		try {
			rolesDao.delete(obj.getId());
			this.initSetup();
		} catch (Exception e) {
			Messagebox.show("Tidak Dapat di Hapus!!!", "Error", Messagebox.OK, Messagebox.ERROR);
		}

	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
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
