package com.ditzweb.controllers;





import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Window;

import com.ditzweb.beans.ItemSupplaiIn;
import com.ditzweb.beans.Stock;
import com.ditzweb.beans.Supplier;
import com.ditzweb.beans.TransaksiIn;
import com.ditzweb.dao.ItemSupplaiInDao;
import com.ditzweb.dao.QuantityConversionDao;
import com.ditzweb.dao.StockDao;
import com.ditzweb.dao.SupplierDao;
import com.ditzweb.dao.TransaksiInDao;




public class TransaksiInRedirectController extends SelectorComposer<Component> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Window window;
	private String noFaktur;
	private String noKendaraan;
	private Date date;
	Supplier sup;
	private String addDisabled="false";
	private String editDisabled="false";

	@WireVariable
	SupplierDao supplierDao = (SupplierDao) SpringUtil.getBean("supplierDao");
	
	@WireVariable
	TransaksiInDao transaksiInDao = (TransaksiInDao) SpringUtil.getBean("transaksiInDao");
	
	@WireVariable
	ItemSupplaiInDao itemSupplaiInDao = (ItemSupplaiInDao) SpringUtil.getBean("itemSupplaiInDao");
	
	@WireVariable
	StockDao stockDao = (StockDao) SpringUtil.getBean("stockDao");
	
	TransaksiIn trans;
	
	
	
	@WireVariable
	QuantityConversionDao quantityConversionDao = (QuantityConversionDao) SpringUtil.getBean("quantityConversionDao");
	
	HashMap<Integer,ItemSupplaiIn> itemSupplai = new HashMap<Integer,ItemSupplaiIn>();
	
	
	private List<Supplier> supplier = supplierDao.getAll();
	
	
	
	@GlobalCommand
	@NotifyChange("itemSupplai")
	public void updateTransIn(@BindingParam("obj2") TransaksiIn obj)
	{
		obj.getId();
		obj.getNamaSupplier();
		obj.getNoKendaraan();
	}
	
	
	
	@Command
    public void add()
    {
		trans = new TransaksiIn();
		trans.setNamaSupplier(this.sup);
		trans.setNoFaktur(this.noFaktur);
		trans.setNoKendaraan(this.noKendaraan);
		trans.setTanggal(new Date());
		transaksiInDao.save(trans);
		Map<Integer, Double> arrayStock = new HashMap<Integer, Double>();
		if(itemSupplai.size()>0) {
			for(ItemSupplaiIn is:itemSupplai.values()) {
				is.setTransIn(trans);
				itemSupplaiInDao.save(is);
				Stock st = new Stock();
				if(stockDao.getStock(is.getItem().getId())==null) {
					st.setItem(is.getItem());
					st.setItemsBasicUnit(is.getSatuanDasar());
					st.setJumlahStock(is.getJumlah());
					stockDao.save(st);
				}else if(stockDao.getStock(is.getItem().getId())!=null){
					Double hasil = is.getJumlah();
					if(arrayStock.get(is.getItem().getId())!=null) {
						Double value = arrayStock.get(is.getItem().getId());
						 hasil = hasil + value;
					}
					arrayStock.put(is.getItem().getId(), hasil);
				}
			}
		}
		
		for (Entry<Integer, Double> entry : arrayStock.entrySet()) {
		    Stock stockUpdate = stockDao.getStock(entry.getKey());
		    stockUpdate.setJumlahStock(stockUpdate.getJumlahStock()+entry.getValue());
		    stockDao.updateStock(stockUpdate);
		}
		
		Executions.sendRedirect("transaksiin.zul");
    }
	
	@Command
	public void addBarang(){
		ItemSupplaiIn obj = new ItemSupplaiIn();
		HashMap<String, ItemSupplaiIn> dt = new HashMap<String, ItemSupplaiIn>();
		dt.put("data", obj);
		window = (Window) Executions.createComponents("/itemsupplaiinadd.zul", null, dt);
		window.doModal();
	}
	
	@Command
	public void updateModal(@BindingParam("obj") ItemSupplaiIn obj,@BindingParam("key") int key){
		ItemSupplaiIn Ikey = new ItemSupplaiIn();
		Ikey.setQty(key);
		HashMap<String, ItemSupplaiIn> dt = new HashMap<String, ItemSupplaiIn>();
		dt.put("data", obj);
		dt.put("key", Ikey);
		window = (Window) Executions.createComponents("/itemsupplaiinupdate.zul", null, dt);
		window.doModal();
	}
	
	@GlobalCommand
	@NotifyChange("itemSupplai")
	public void addItemsSupplaiIn(@BindingParam("obj") ItemSupplaiIn obj) {
		itemSupplai.put(itemSupplai.size()+1, obj);
		window.detach();
	}
	
	@GlobalCommand
	@NotifyChange("itemSupplai")
	public void updateItemSupplaiIn(@BindingParam("obj") ItemSupplaiIn obj, @BindingParam("obj2") int key) {
		itemSupplai.put(key, obj);
		window.detach();
	}
	
	@Command
	public void cancel() {
		Executions.sendRedirect("transaksiin.zul");
	}
	

	
	public List<Supplier> getSupplier() {
		return supplier;
	}

	public void setSupplier(List<Supplier> supplier) {
		this.supplier = supplier;
	}

	public Supplier getSup() {
		return sup;
	}

	public void setSup(Supplier sup) {
		this.sup = sup;
	}

	public String getNoFaktur() {
		return noFaktur;
	}

	public void setNoFaktur(String noFaktur) {
		this.noFaktur = noFaktur;
	}

	public String getNoKendaraan() {
		return noKendaraan;
	}

	public void setNoKendaraan(String noKendaraan) {
		this.noKendaraan = noKendaraan;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public HashMap<Integer, ItemSupplaiIn> getItemSupplai() {
		return itemSupplai;
	}

	public void setItemSupplai(HashMap<Integer, ItemSupplaiIn> itemSupplai) {
		this.itemSupplai = itemSupplai;
	}



	public String getAddDisabled() {
		return addDisabled;
	}



	public void setAddDisabled(String addDisabled) {
		this.addDisabled = addDisabled;
	}



	public String getEditDisabled() {
		return editDisabled;
	}



	public void setEditDisabled(String editDisabled) {
		this.editDisabled = editDisabled;
	}



	public TransaksiIn getTrans() {
		return trans;
	}



	public void setTrans(TransaksiIn trans) {
		this.trans = trans;
	}

	

	
	

	

	
	
	
	
	
	
	

	
	
}
