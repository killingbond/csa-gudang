package com.ditzweb.controllers;

import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.DependsOn;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.SpringUtil;

import com.ditzweb.beans.ItemSupplaiIn;
import com.ditzweb.beans.Items;
import com.ditzweb.beans.ItemsBasicUnit;
import com.ditzweb.beans.ItemsQuantity;
import com.ditzweb.beans.QuantityConversion;
import com.ditzweb.dao.ItemsBasicUnitDao;
import com.ditzweb.dao.ItemsDao;
import com.ditzweb.dao.ItemsQuantityDao;
import com.ditzweb.dao.QuantityConversionDao;

public class ItemSupplaiInUpdateHargaModal {

	private int key = 0;
	private int jmlQty = 0;
	private Double jmlSatuanDasar;
	private Double hargaSatuanInput=0.0;
	private Double hargaSatuanDasar=0.0;
	private Double hargaTotal=0.0;

	@WireVariable
	ItemsDao itemsDao = (ItemsDao) SpringUtil.getBean("itemsDao");

	@WireVariable
	ItemsQuantityDao itemsQuantityDao = (ItemsQuantityDao) SpringUtil.getBean("itemsQuantityDao");

	@WireVariable
	ItemsBasicUnitDao itemsBasicUnitDao = (ItemsBasicUnitDao) SpringUtil.getBean("itemsBasicUnitDao");

	@WireVariable
	QuantityConversionDao quantityConversionDao = (QuantityConversionDao) SpringUtil.getBean("quantityConversionDao");

	private Items items;
	private ItemsQuantity itemsQuantity;
	private ItemSupplaiIn itemSupplaiIn;
	private ItemsBasicUnit satuanDasar;

	private List<Items> itemList = itemsDao.getAll();
	private List<ItemsQuantity> itemsQtyList = null;

	@Init
	public void showData(@ExecutionArgParam("data") ItemSupplaiIn obj) {
		this.satuanDasar = obj.getSatuanDasar();
		this.jmlQty = obj.getQty();
		this.jmlSatuanDasar = obj.getJumlah();
		if(obj.getSatuan()!=null || obj.getItem() !=null) {
			this.items = itemsDao.getById(obj.getItem().getId());
			this.itemsQuantity = itemsQuantityDao.getById(obj.getSatuan().getId());
			this.key = obj.getId();
		}
		if(hargaSatuanInput!=0.0) {
			this.hargaSatuanInput = obj.getHargaSatuanInput();
			this.hargaSatuanDasar = obj.getHargaSatuanDasar();
			this.hargaTotal = obj.getHargaTotal();
		}
		

	}

	@Command
	public void add() {
		itemSupplaiIn = new ItemSupplaiIn();
		itemSupplaiIn.setId(this.key);
		itemSupplaiIn.setHargaTotal(this.hargaTotal);
		itemSupplaiIn.setHargaSatuanDasar(this.hargaSatuanDasar);
		itemSupplaiIn.setHargaSatuanInput(this.hargaSatuanInput);
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public ItemsQuantity getItemsQuantity() {
		return itemsQuantity;
	}

	public void setItemsQuantity(ItemsQuantity itemsQuantity) {
		this.itemsQuantity = itemsQuantity;
	}

	@DependsOn("items")
	public List<Items> getItemList() {
		return itemList;
	}

	public void setItemList(List<Items> itemList) {
		this.itemList = itemList;
	}

	@DependsOn("items")
	public List<ItemsQuantity> getItemsQtyList() {
		if (this.items == null) {
			return itemsQtyList;
		}
		return itemsQtyList = itemsQuantityDao.getByItems(this.items.getId());
	}

	public void setItemsQtyList(List<ItemsQuantity> itemsQtyList) {
		this.itemsQtyList = itemsQtyList;
	}

	public Items getItems() {
		return items;
	}

	public void setItems(Items items) {
		this.items = items;
	}

	public ItemSupplaiIn getItemSupplaiIn() {
		return itemSupplaiIn;
	}

	public void setItemSupplaiIn(ItemSupplaiIn itemSupplaiIn) {
		this.itemSupplaiIn = itemSupplaiIn;
	}

	public int getJmlQty() {
		return jmlQty;
	}

	public void setJmlQty(int jmlQty) {
		this.jmlQty = jmlQty;
	}

	@DependsOn("items")
	public ItemsBasicUnit getSatuanDasar() {
		if (this.items == null) {
			return satuanDasar;
		}
		return satuanDasar = itemsBasicUnitDao.getItems(this.items.getId());
	}

	public void setSatuanDasar(ItemsBasicUnit satuanDasar) {
		this.satuanDasar = satuanDasar;
	}

	@DependsOn({ "itemsQuantity", "jmlQty" })
	public Double getJmlSatuanDasar() {
		if (this.itemsQuantity == null || this.jmlQty == 0) {
			return jmlSatuanDasar;
		}
		if (satuanDasar.getSatuanDasar().getId() == this.itemsQuantity.getQty().getId()) {
			return jmlSatuanDasar = 1.0 * this.jmlQty;
		}
		QuantityConversion qc = quantityConversionDao.getByName(this.itemsQuantity.getQty().getId(),
				satuanDasar.getSatuanDasar().getId());
		return jmlSatuanDasar = qc.getBesarKonversi() * this.jmlQty;
	}

	public void setJmlSatuanDasar(Double jmlSatuanDasar) {
		this.jmlSatuanDasar = jmlSatuanDasar;
	}

	public Double getHargaSatuanInput() {
		return hargaSatuanInput;
	}

	public void setHargaSatuanInput(Double hargaSatuanInput) {
		this.hargaSatuanInput = hargaSatuanInput;
	}
	
	@DependsOn({ "itemsQuantity", "jmlQty","hargaSatuanInput" })
	public Double getHargaSatuanDasar() {
		if (this.itemsQuantity == null || this.jmlQty == 0) {
			return hargaSatuanDasar;
		}
		if (satuanDasar.getSatuanDasar().getId() == this.itemsQuantity.getQty().getId()) {
			return hargaSatuanDasar = 1.0 * this.hargaSatuanInput;
		}
		QuantityConversion qc = quantityConversionDao.getByName(this.itemsQuantity.getQty().getId(),
				satuanDasar.getSatuanDasar().getId());
		return hargaSatuanDasar = qc.getBesarKonversi() * this.hargaSatuanInput;
	}

	public void setHargaSatuanDasar(Double hargaSatuanDasar) {
		this.hargaSatuanDasar = hargaSatuanDasar;
	}

	@DependsOn({"hargaSatuanInput"})
	public Double getHargaTotal() {
		if(this.hargaSatuanInput!=null) {
			return hargaTotal = hargaSatuanInput * jmlQty;
		}
		return hargaTotal ;
	}

	public void setHargaTotal(Double hargaTotal) {
		this.hargaTotal = hargaTotal;
	}

	
	
	

}
