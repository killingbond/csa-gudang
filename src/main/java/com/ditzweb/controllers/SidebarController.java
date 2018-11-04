package com.ditzweb.controllers;

import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;

public class SidebarController {

	
	
	private String user = "false";
	private String roles = "false";
	private String supplier = "false";
	private String client = "false";
	private String quantity = "false";
	private String items = "false";
	private String itemsquantity = "false";
	private String quantityconversion = "false";
	private String stock = "false";
	private String transaksiin = "false";
	private String transaksiout = "false";
	private String dashboard = "false";
	
	
	
	@Init
	public void init() {
		String sidebarurl = Executions.getCurrent().getDesktop().getRequestPath();
		if(sidebarurl.equals("/index.zul")||sidebarurl.equals("/")) {
			this.dashboard = "true";
		}else if(sidebarurl.equals("/user.zul")) {
			this.user = "true";
		}else if (sidebarurl.equals("/roles.zul")) {
			this.roles ="true";
		}else if (sidebarurl.equals("/supplier.zul")) {
			this.supplier = "true";
		}else if (sidebarurl.equals("/client.zul")) {
			this.client = "true";
		}else if (sidebarurl.equals("/quantity.zul")) {
			this.quantity = "true";
		}else if (sidebarurl.equals("/items.zul")) {
			this.items = "true";
		}else if (sidebarurl.equals("/itemsquantity.zul")) {
			this.itemsquantity = "true";
		}else if (sidebarurl.equals("/quantityconversion.zul")) {
			this.quantityconversion = "true";
		}else if (sidebarurl.equals("/stock.zul")) {
			this.stock = "true";
		}else if (sidebarurl.equals("/transaksiin.zul") || sidebarurl.equals("/transaksiinadd.zul") || sidebarurl.equals("/transaksiinupdate.zul") ){
			this.transaksiin = "true";
		}else if (sidebarurl.equals("/transaksiout.zul") || sidebarurl.equals("/transaksioutadd.zul") || sidebarurl.equals("/transaksioutupdate.zul")) {
			this.transaksiout = "true";
		}
	}
	
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getRoles() {
		return roles;
	}
	public void setRoles(String roles) {
		this.roles = roles;
	}
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getItems() {
		return items;
	}
	public void setItems(String items) {
		this.items = items;
	}
	public String getItemsquantity() {
		return itemsquantity;
	}
	public void setItemsquantity(String itemsquantity) {
		this.itemsquantity = itemsquantity;
	}
	public String getQuantityconversion() {
		return quantityconversion;
	}
	public void setQuantityconversion(String quantityconversion) {
		this.quantityconversion = quantityconversion;
	}
	public String getStock() {
		return stock;
	}
	public void setStock(String stock) {
		this.stock = stock;
	}
	public String getTransaksiin() {
		return transaksiin;
	}
	public void setTransaksiin(String transaksiin) {
		this.transaksiin = transaksiin;
	}
	public String getTransaksiout() {
		return transaksiout;
	}
	public void setTransaksiout(String transaksiout) {
		this.transaksiout = transaksiout;
	}


	public String getDashboard() {
		return dashboard;
	}


	public void setDashboard(String dashboard) {
		this.dashboard = dashboard;
	}
	
	
	
}
