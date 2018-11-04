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
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Window;


import com.ditzweb.beans.TransaksiIn;
import com.ditzweb.dao.TransaksiInDao;






public class TransaksiInController extends SelectorComposer<Component> {

	@WireVariable
	TransaksiInDao transaksiInDao = (TransaksiInDao) SpringUtil.getBean("transaksiInDao");
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Window window;

	private String id = "";
	private String noFaktur = "";
	private String namaSupplier = "";
	private Date tanggalDari;
	private Date tanggalKe;
	private String status = "";
	private String disabled = "false";

	private List<TransaksiIn> allReordsInDB = null;
	TransaksiIn transIn;

	@AfterCompose
	public void initSetup() {
		Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) SecurityContextHolder.getContext()
				.getAuthentication().getAuthorities();
		boolean hasRoleStokish = false;
		for (GrantedAuthority authority : authorities) {
			hasRoleStokish = authority.getAuthority().equals("ROLE_STOKISH");
			if (hasRoleStokish) {
				this.disabled = "true";
				break;
			}
		}
		allReordsInDB = transaksiInDao.getAll();
	}

	public List<TransaksiIn> getAllReordsInDB() {
		return allReordsInDB;
	}

	public void setAllReordsInDB(List<TransaksiIn> allReordsInDB) {
		this.allReordsInDB = allReordsInDB;
	}

	@Command
	public void add() {
		Executions.sendRedirect("transaksiinadd.zul");
	}

	@Command
	@NotifyChange("allReordsInDB")
	public void cari() {
		if(tanggalDari==null|| tanggalKe==null) {
			allReordsInDB =  transaksiInDao.getSearch( id, noFaktur, namaSupplier);
			tanggalDari = null;
			tanggalKe = null;
		}else {
			allReordsInDB =  transaksiInDao.getAllSearch(tanggalDari, tanggalKe, id, noFaktur, namaSupplier);
		}
		
		
	}

	@Command
	public void updateModal(@BindingParam("obj") TransaksiIn obj) {
		HashMap<String, TransaksiIn> dt = new HashMap<String, TransaksiIn>();
		dt.put("data", obj);
		Sessions.getCurrent().setAttribute("allmyvalues", dt);
		Executions.sendRedirect("transaksiinupdate.zul");
	}

	@GlobalCommand
	@NotifyChange("allReordsInDB")
	public void updateQuantityConversion(@BindingParam("obj") TransaksiIn obj) {
		transaksiInDao.update(obj);
		window.detach();
		this.initSetup();
	}

	@GlobalCommand
	@NotifyChange("allReordsInDB")
	public void addQuantityConversion(@BindingParam("obj") TransaksiIn obj) {
		transaksiInDao.save(obj);
		window.detach();
		this.initSetup();
	}

	@Command
	@NotifyChange("allReordsInDB")
	public void delete(@BindingParam("obj") TransaksiIn obj) {
		transaksiInDao.delete(obj.getId());
		this.initSetup();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNoFaktur() {
		return noFaktur;
	}

	public void setNoFaktur(String noFaktur) {
		this.noFaktur = noFaktur;
	}

	public String getNamaSupplier() {
		return namaSupplier;
	}

	public void setNamaSupplier(String namaSupplier) {
		this.namaSupplier = namaSupplier;
	}

	public Date getTanggalDari() {
		return tanggalDari;
	}

	public void setTanggalDari(Date tanggalDari) {
		this.tanggalDari = tanggalDari;
	}

	public Date getTanggalKe() {
		return tanggalKe;
	}

	public void setTanggalKe(Date tanggalKe) {
		this.tanggalKe = tanggalKe;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}
	
	
	

	

}
