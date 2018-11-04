package com.ditzweb.controllers;

import java.util.Collection;
import java.util.Date;
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

import com.ditzweb.beans.Status;
import com.ditzweb.beans.User;
import com.ditzweb.dao.UserDao;

public class UserController extends SelectorComposer<Component> {

	@WireVariable
	UserDao userDao = (UserDao) SpringUtil.getBean("userDao");
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Window window;

	private String id_user = "";
	private String nama_user = "";
	private String deskripsi_user = "";
	private String status_user = "";
	private String npk = "";
	private String addDisabled="false";
	private String visible="true";


	private List<User> allReordsInDB = null;

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
			if(hasRoleOwner || hasRoleMarketing) {
				this.addDisabled = "true";
				this.visible = "false";
			}
			if (hasRoleAdmin) {
				this.addDisabled = "false";
				this.visible = "true";
				break;
			}
		}
		allReordsInDB = userDao.getAll();
	}

	public List<User> getAllReordsInDB() {
		return allReordsInDB;
	}

	public void setAllReordsInDB(List<User> allReordsInDB) {
		this.allReordsInDB = allReordsInDB;
	}

	@Command
	public void add() {
		User obj = new User();
		HashMap<String, User> dt = new HashMap<String, User>();
		dt.put("data", obj);
		window = (Window) Executions.createComponents("/useradd.zul", null, dt);
		window.doModal();
	}

	@Command
	@NotifyChange("allReordsInDB")
	public void cari() {
		String status = "";
		if (status_user.equals("Aktif")) {
			status = "0";
		} else if (status_user.equals("UnAktif")) {
			status = "1";
		}
		allReordsInDB = userDao.getAllSearch(id_user, nama_user, deskripsi_user, status, npk);
	}

	@Command
	public void updateModal(@BindingParam("obj") User obj) {
		HashMap<String, User> dt = new HashMap<String, User>();
		dt.put("data", obj);
		window = (Window) Executions.createComponents("/userupdate.zul", null, dt);
		window.doModal();

	}

	@GlobalCommand
	@NotifyChange("allReordsInDB")
	public void updateUser(@BindingParam("obj") User obj) {
		userDao.update(obj);
		window.detach();
		this.initSetup();
	}

	@GlobalCommand
	@NotifyChange("allReordsInDB")
	public void addUser(@BindingParam("obj") User obj) {
		obj.setDate(new Date());
		obj.setStatus(Status.Aktif);
		userDao.save(obj);
		window.detach();
		this.initSetup();
	}

	@Command
	@NotifyChange("allReordsInDB")
	public void delete(@BindingParam("obj") User obj) {
		try {
			userDao.delete(obj.getUsername());
			this.initSetup();
		} catch (Exception e) {
			Messagebox.show("Tidak Dapat di Hapus!!!","Error", Messagebox.OK, Messagebox.ERROR);
		}
		
	}

	public String getId_user() {
		return id_user;
	}

	public void setId_user(String id_user) {
		this.id_user = id_user;
	}

	public String getNama_user() {
		return nama_user;
	}

	public void setNama_user(String nama_user) {
		this.nama_user = nama_user;
	}

	public String getDeskripsi_user() {
		return deskripsi_user;
	}

	public void setDeskripsi_user(String deskripsi_user) {
		this.deskripsi_user = deskripsi_user;
	}

	public String getNpk() {
		return npk;
	}

	public void setNpk(String npk) {
		this.npk = npk;
	}

	public String getStatus_user() {
		return status_user;
	}

	public void setStatus_user(String status_user) {
		this.status_user = status_user;
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
