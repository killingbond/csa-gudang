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

import com.ditzweb.beans.TransaksiOut;
import com.ditzweb.dao.TransaksiOutDao;

public class TransaksiOutController extends SelectorComposer<Component> {

	@WireVariable
	TransaksiOutDao transaksiOutDao = (TransaksiOutDao) SpringUtil.getBean("transaksiOutDao");
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Window window;

	private String id = "";
	private String noBanPenjualan = "";
	private String namaClient = "";
	private Date tanggalDari;
	private Date tanggalKe;
	private String status = "";
	private String disabled = "false";

	private List<TransaksiOut> allReordsInDB = null;
	TransaksiOut transOut;

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
		allReordsInDB = transaksiOutDao.getAll();
	}

	public List<TransaksiOut> getAllReordsInDB() {
		return allReordsInDB;
	}

	public void setAllReordsInDB(List<TransaksiOut> allReordsInDB) {
		this.allReordsInDB = allReordsInDB;
	}

	@Command
	public void add() {
		Executions.sendRedirect("transaksioutadd.zul");
	}

	@Command
	@NotifyChange("allReordsInDB")
	public void cari() {
		if(tanggalDari==null|| tanggalKe==null) {
			allReordsInDB =  transaksiOutDao.getSearch( id, noBanPenjualan, namaClient);
			tanggalDari = null;
			tanggalKe = null;
		}else {
			allReordsInDB =  transaksiOutDao.getAllSearch(tanggalDari, tanggalKe, id, noBanPenjualan, namaClient);
		}
	}

	@Command
	public void updateModal(@BindingParam("obj") TransaksiOut obj) {
		HashMap<String, TransaksiOut> dt = new HashMap<String, TransaksiOut>();
		dt.put("data", obj);
		Sessions.getCurrent().setAttribute("alloutmyvalues", dt);
		Executions.sendRedirect("transaksioutupdate.zul");
	}

	@GlobalCommand
	@NotifyChange("allReordsInDB")
	public void updateQuantityConversion(@BindingParam("obj") TransaksiOut obj) {
		transaksiOutDao.update(obj);
		window.detach();
		this.initSetup();
	}

	@GlobalCommand
	@NotifyChange("allReordsInDB")
	public void addQuantityConversion(@BindingParam("obj") TransaksiOut obj) {
		transaksiOutDao.save(obj);
		window.detach();
		this.initSetup();
	}

	@Command
	@NotifyChange("allReordsInDB")
	public void delete(@BindingParam("obj") TransaksiOut obj) {
		transaksiOutDao.delete(obj.getId());
		this.initSetup();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	

	public String getNoBanPenjualan() {
		return noBanPenjualan;
	}

	public void setNoBanPenjualan(String noBanPenjualan) {
		this.noBanPenjualan = noBanPenjualan;
	}

	public String getNamaClient() {
		return namaClient;
	}

	public void setNamaClient(String namaClient) {
		this.namaClient = namaClient;
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

	public TransaksiOut getTransOut() {
		return transOut;
	}

	public void setTransOut(TransaksiOut transOut) {
		this.transOut = transOut;
	}

	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

}
