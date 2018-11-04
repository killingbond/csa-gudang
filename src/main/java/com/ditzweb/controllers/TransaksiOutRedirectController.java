package com.ditzweb.controllers;




import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Window;

import com.ditzweb.beans.Client;
import com.ditzweb.beans.ItemSupplaiOut;
import com.ditzweb.beans.Stock;
import com.ditzweb.beans.TransaksiOut;
import com.ditzweb.dao.ClientDao;
import com.ditzweb.dao.ItemSupplaiOutDao;
import com.ditzweb.dao.StockDao;
import com.ditzweb.dao.TransaksiOutDao;




public class TransaksiOutRedirectController {

	private Window window;
	private String noBanPenjualan;
	private String noKendaraan;
	private Date date;
	Client cl;

	TransaksiOut trans;
	@WireVariable
	ClientDao clientDao = (ClientDao) SpringUtil.getBean("clientDao");
	
	@WireVariable
	TransaksiOutDao transaksiOutDao = (TransaksiOutDao) SpringUtil.getBean("transaksiOutDao");
	
	@WireVariable
	ItemSupplaiOutDao itemSupplaiOutDao = (ItemSupplaiOutDao) SpringUtil.getBean("itemSupplaiOutDao");
	
	@WireVariable
	StockDao stockDao = (StockDao) SpringUtil.getBean("stockDao");
	
	HashMap<Integer,ItemSupplaiOut> itemSupplai = new HashMap<Integer,ItemSupplaiOut>();
	
	
	
	private List<Client> client = clientDao.getAll();
	
	@AfterCompose
    public void initSetup()
    {
		
    }
	
	@GlobalCommand
	@NotifyChange("itemSupplai")
	public void updateTransIn(@BindingParam("obj2") TransaksiOut obj)
	{
		obj.getId();
		obj.getNamaClient();
		obj.getNoKendaraan();
	}
	
	@Command
    public void add()
    {
		trans = new TransaksiOut();
		trans.setNamaClient(this.cl);
		trans.setNoBanPenjualan(this.noBanPenjualan);
		trans.setNoKendaraan(this.noKendaraan);
		trans.setTanggal(new Date());
		transaksiOutDao.save(trans);
		Map<Integer, Double> arrayStock = new HashMap<Integer, Double>();
		if(itemSupplai.size()>0) {
			for(ItemSupplaiOut is:itemSupplai.values()) {
				is.setTransOut(trans);
				itemSupplaiOutDao.save(is);
				if(stockDao.getStock(is.getItem().getId())!=null){
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
		    stockUpdate.setJumlahStock(stockUpdate.getJumlahStock()-entry.getValue());
		    stockDao.updateStock(stockUpdate);
		}
		Executions.sendRedirect("transaksiout.zul");
    }
	
	@Command
	public void addBarang(){
		ItemSupplaiOut obj = new ItemSupplaiOut();
		HashMap<String, ItemSupplaiOut> dt = new HashMap<String, ItemSupplaiOut>();
		dt.put("data", obj);
		window = (Window) Executions.createComponents("/itemsupplaioutadd.zul", null, dt);
		window.doModal();
	}
	
	@Command
	public void updateModal(@BindingParam("obj") ItemSupplaiOut obj,@BindingParam("key") int key){
		ItemSupplaiOut Ikey = new ItemSupplaiOut();
		Ikey.setQty(key);
		HashMap<String, ItemSupplaiOut> dt = new HashMap<String, ItemSupplaiOut>();
		dt.put("data", obj);
		dt.put("key", Ikey);
		window = (Window) Executions.createComponents("/itemsupplaioutupdate.zul", null, dt);
		window.doModal();
	}
	
	@GlobalCommand
	@NotifyChange("itemSupplai")
	public void addItemsSupplaiOut(@BindingParam("obj") ItemSupplaiOut obj) {
		itemSupplai.put(itemSupplai.size()+1, obj);
		window.detach();
	}
	
	@GlobalCommand
	@NotifyChange("itemSupplai")
	public void updateItemSupplaiOut(@BindingParam("obj") ItemSupplaiOut obj, @BindingParam("obj2") int key) {
		itemSupplai.put(key, obj);
		window.detach();
	}
	
	@Command
	public void cancel() {
		Executions.sendRedirect("transaksiin.zul");
	}
	

	public List<Client> getClient() {
		return client;
	}

	public void setClient(List<Client> client) {
		this.client = client;
	}

	public Client getCl() {
		return cl;
	}

	public void setCl(Client cl) {
		this.cl = cl;
	}

	public String getNoBanPenjualan() {
		return noBanPenjualan;
	}

	public void setNoBanPenjualan(String noBanPenjualan) {
		this.noBanPenjualan = noBanPenjualan;
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

	public HashMap<Integer, ItemSupplaiOut> getItemSupplai() {
		return itemSupplai;
	}

	public void setItemSupplai(HashMap<Integer, ItemSupplaiOut> itemSupplai) {
		this.itemSupplai = itemSupplai;
	}

	public TransaksiOut getTrans() {
		return trans;
	}

	public void setTrans(TransaksiOut trans) {
		this.trans = trans;
	}
	
	

	

	
	
	
	
	
	
	

	
	
}
