package com.ditzweb.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.SpringUtil;

import com.ditzweb.beans.Role;
import com.ditzweb.beans.Roles;
import com.ditzweb.beans.Status;
import com.ditzweb.beans.User;
import com.ditzweb.dao.UserDao;

public class RolesModal {

	private User username = new User();;
	private Roles roles = new Roles();
	private String rl = "";
	private int id;
	private String uname = "killingbond";
	private String initRoles="";

	@WireVariable
	UserDao userDao = (UserDao) SpringUtil.getBean("userDao");

	private List<User> user = userDao.getAll();

	private Status status = Status.valueOf("Aktif");

	@Init
	public void showData(@ExecutionArgParam("data") Roles obj) {
		if (obj.getUser() != null) {
			this.username = userDao.getByName(obj.getUser().getUsername());
			this.rl = obj.getRole().toString();
			this.status = obj.getStatus();
			this.id = obj.getId();
			this.initRoles = obj.getRole().toString();
			
		}
	}

	@Command
	public void add() {
		if (this.rl != "") {
			roles.setRole(Role.valueOf(this.rl));
		}
		roles.setUser(this.username);
		roles.setStatus(this.status);

		Map<String, Object> dt = new HashMap<String, Object>();
		dt.put("obj", roles);
		BindUtils.postGlobalCommand(null, null, "addRoles", dt);

	}
	
	@Command
	public void update() {
		if (this.rl != "") {
			roles.setRole(Role.valueOf(this.rl));
		}
		roles.setUser(this.username);
		roles.setStatus(this.status);
		roles.setId(this.id);

		Map<String, Object> dt = new HashMap<String, Object>();
		dt.put("obj", roles);
		BindUtils.postGlobalCommand(null, null, "updateRoles", dt);
		
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public List<User> getUser() {
		return user;
	}

	public void setUser(List<User> user) {
		this.user = user;
	}

	public User getUsername() {
		return username;
	}

	public void setUsername(User username) {
		this.username = username;
	}

	public Roles getRoles() {
		return roles;
	}

	public void setRoles(Roles roles) {
		this.roles = roles;
	}

	public String getRl() {
		return rl;
	}

	public void setRl(String rl) {
		this.rl = rl;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getInitRoles() {
		return initRoles;
	}

	public void setInitRoles(String initRoles) {
		this.initRoles = initRoles;
	}

	
	
	

}
