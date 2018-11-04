package com.ditzweb.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.zkoss.bind.annotation.AfterCompose;

public class HeaderController {
	private String adit;
	
	@AfterCompose
	public void initSetup() {
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		this.adit = user.getUsername();
	}

	public String getAdit() {
		return adit;
	}

	public void setAdit(String adit) {
		this.adit = adit;
	}
	
	
	
}
