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

import com.ditzweb.beans.Quantity;
import com.ditzweb.beans.QuantityConversion;
import com.ditzweb.beans.Status;
import com.ditzweb.dao.QuantityDao;




public class QuantityConversionModal {

	private int id = 0;
	private Quantity qtyDari;
	private Quantity qtyKe;
	private Double besarKonversi;
	private Status status= Status.valueOf("Aktif");
	private QuantityConversion quantityConversion;

	@WireVariable
	QuantityDao quantityDao = (QuantityDao) SpringUtil.getBean("quantityDao");
	
	private List<Quantity> qty = quantityDao.getAll();
	
	@Init
    public void showData(@ExecutionArgParam("data") QuantityConversion obj)
    {
		if(obj.getId()!=0) {
			this.id = obj.getId();
	        this.status = obj.getStatus();
	        this.besarKonversi = obj.getBesarKonversi();
	        
	        this.qtyDari = quantityDao.getById(obj.getKonversiDari().getId())  ;
	        this.qtyKe = quantityDao.getById(obj.getKonversiKe().getId())  ;
		}
       
        
    }
	
	@Command
    public void add()
    {
		quantityConversion = new QuantityConversion();
		quantityConversion.setId(this.id);
		quantityConversion.setKonversiDari(this.qtyDari);
		quantityConversion.setKonversiKe(this.qtyKe);
		quantityConversion.setBesarKonversi(this.besarKonversi);
		quantityConversion.setStatus(this.status);
		Map<String, Object> dt = new HashMap<String, Object>();
		dt.put("obj", quantityConversion);
		BindUtils.postGlobalCommand(null, null, "addQuantityConversion", dt);
    }
	
	@Command
    public void update()
    {
		quantityConversion = new QuantityConversion();
		quantityConversion.setId(this.id);
		quantityConversion.setBesarKonversi(this.besarKonversi);
		quantityConversion.setStatus(this.status);
		Map<String, Object> dt = new HashMap<String, Object>();
		dt.put("obj", quantityConversion);
		BindUtils.postGlobalCommand(null, null, "updateQuantityConversion", dt);
    }
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public List<Quantity> getQty() {
		return qty;
	}

	public void setQty(List<Quantity> qty) {
		this.qty = qty;
	}

	public Quantity getQtyDari() {
		return qtyDari;
	}

	public void setQtyDari(Quantity qtyDari) {
		this.qtyDari = qtyDari;
	}

	public Quantity getQtyKe() {
		return qtyKe;
	}

	public void setQtyKe(Quantity qtyKe) {
		this.qtyKe = qtyKe;
	}

	public Double getBesarKonversi() {
		return besarKonversi;
	}

	public void setBesarKonversi(Double besarKonversi) {
		this.besarKonversi = besarKonversi;
	}

	public QuantityConversion getQuantityConversion() {
		return quantityConversion;
	}

	public void setQuantityConversion(QuantityConversion quantityConversion) {
		this.quantityConversion = quantityConversion;
	}
	
	
	
	
	
	

	
	
	
	
	
}
