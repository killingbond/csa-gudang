package com.ditzweb.controllers;



import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;


import com.ditzweb.beans.Status;
import com.ditzweb.beans.User;



public class UserModal {

	private Date date;
	private Status status=Status.valueOf("Aktif"); ;
	private User user = new User();
	
	private String nama;
	
	@Init
    public void showData(@ExecutionArgParam("data") User obj)
    {
		
        if(obj!=null) {
        	user = obj;
        }
    }
	
	@Command
    public void add()
    {
        Map<String, Object> dt = new HashMap<String, Object>();
		dt.put("obj", user);
        BindUtils.postGlobalCommand(null, null, "addUser",dt);
    }
	
	@Command
    public void update()
    {
		user.getAlamat();
		user.getNama();
		user.getDeskripsi();
        Map<String, Object> dt = new HashMap<String, Object>();
		dt.put("obj", user);
        BindUtils.postGlobalCommand(null, null, "updateUser",dt);
    }

	

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}
	
	
	
	
	
}
