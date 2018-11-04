package com.ditzweb.controllers;



import java.util.List;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.SpringUtil;


import com.ditzweb.beans.Stock;
import com.ditzweb.dao.StockDao;




public class StockController extends SelectorComposer<Component> {

	@WireVariable
	StockDao stockDao = (StockDao) SpringUtil.getBean("stockDao");
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id="";
	private String namaBarang="";
	
	

	private List<Stock> allReordsInDB = stockDao.getAll();

	@AfterCompose
	public void initSetup() {
		allReordsInDB = stockDao.getAll();
	}

	public List<Stock> getAllReordsInDB() {
		return allReordsInDB;
	}

	public void setAllReordsInDB(List<Stock> allReordsInDB) {
		this.allReordsInDB = allReordsInDB;
	}

	@Command
	@NotifyChange("allReordsInDB")
	public void cari() {
		allReordsInDB = stockDao.getAllSearch(id, namaBarang);
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
	
	
	 
	
	
	

	
	
	

	

}
