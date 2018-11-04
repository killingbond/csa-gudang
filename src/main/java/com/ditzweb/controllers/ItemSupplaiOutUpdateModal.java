package com.ditzweb.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.DependsOn;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Messagebox;

import com.ditzweb.beans.ItemSupplaiOut;
import com.ditzweb.beans.Items;
import com.ditzweb.beans.ItemsBasicUnit;
import com.ditzweb.beans.ItemsQuantity;
import com.ditzweb.beans.QuantityConversion;
import com.ditzweb.dao.ItemsBasicUnitDao;
import com.ditzweb.dao.ItemsDao;
import com.ditzweb.dao.ItemsQuantityDao;
import com.ditzweb.dao.QuantityConversionDao;

public class ItemSupplaiOutUpdateModal {

	private int key = 0;
	private int jmlQty = 0;
	private Double jmlSatuanDasar;

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
	private ItemSupplaiOut itemSupplaiOut;
	private ItemsBasicUnit satuanDasar;

	private List<Items> itemList = itemsDao.getAll();
	private List<ItemsQuantity> itemsQtyList = null;

	@Init
	public void showData(@ExecutionArgParam("data") ItemSupplaiOut obj) {
		this.satuanDasar = obj.getSatuanDasar();
		this.jmlQty = obj.getQty();
		this.jmlSatuanDasar = obj.getJumlah();
		if (obj.getSatuan() != null || obj.getItem() != null) {
			this.items = itemsDao.getById(obj.getItem().getId());
			this.itemsQuantity = itemsQuantityDao.getById(obj.getSatuan().getId());
			this.key = obj.getId();
		}

	}

	@Command
	public void add() {
		itemSupplaiOut = new ItemSupplaiOut();
		itemSupplaiOut.setId(this.key);
		itemSupplaiOut.setItem(this.items);
		itemSupplaiOut.setSatuan(this.itemsQuantity);
		ItemsBasicUnit ibs = itemsBasicUnitDao.getItems(this.items.getId());
		itemSupplaiOut.setSatuanDasar(ibs);
		itemSupplaiOut.setQty(this.jmlQty);
		itemSupplaiOut.setJumlah(this.jmlSatuanDasar);
		Map<String, Object> dt = new HashMap<String, Object>();
		dt.put("obj", itemSupplaiOut);
		dt.put("obj2", this.key);
		BindUtils.postGlobalCommand(null, null, "addUpdateItemsSupplaiOut", dt);
	}

	@Command
	public void update() {
		itemSupplaiOut = new ItemSupplaiOut();
		itemSupplaiOut.setId(this.key);
		itemSupplaiOut.setItem(this.items);
		itemSupplaiOut.setSatuan(this.itemsQuantity);
		ItemsBasicUnit ibs = itemsBasicUnitDao.getItems(this.items.getId());
		itemSupplaiOut.setSatuanDasar(ibs);
		itemSupplaiOut.setQty(this.jmlQty);
		itemSupplaiOut.setJumlah(this.jmlSatuanDasar);
		Map<String, Object> dt = new HashMap<String, Object>();
		dt.put("obj", itemSupplaiOut);
		dt.put("obj2", this.key);
		BindUtils.postGlobalCommand(null, null, "updateupdateItemSupplaiOut", dt);
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

	public ItemSupplaiOut getItemSupplaiOut() {
		return itemSupplaiOut;
	}

	public void setItemSupplaiOut(ItemSupplaiOut itemSupplaiOut) {
		this.itemSupplaiOut = itemSupplaiOut;
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
		try {
			if (this.itemsQuantity == null || this.jmlQty == 0) {
				return jmlSatuanDasar;
			}
			if (satuanDasar.getSatuanDasar().getId() == this.itemsQuantity.getQty().getId()) {
				return jmlSatuanDasar = 1.0 * this.jmlQty;
			}
			QuantityConversion qc = quantityConversionDao.getByName(this.itemsQuantity.getQty().getId(),
					satuanDasar.getSatuanDasar().getId());
			return jmlSatuanDasar = qc.getBesarKonversi() * this.jmlQty;
		} catch (Exception e) {
			Messagebox.show("Satuan Belum di Konversi !!!", "Error", Messagebox.OK, Messagebox.ERROR);
			return null;
		}

	}

	public void setJmlSatuanDasar(Double jmlSatuanDasar) {
		this.jmlSatuanDasar = jmlSatuanDasar;
	}

}
