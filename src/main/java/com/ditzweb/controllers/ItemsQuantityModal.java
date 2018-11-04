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

import com.ditzweb.beans.Items;
import com.ditzweb.beans.ItemsBasicUnit;
import com.ditzweb.beans.ItemsQuantity;
import com.ditzweb.beans.Quantity;
import com.ditzweb.beans.Status;
import com.ditzweb.dao.ItemsBasicUnitDao;
import com.ditzweb.dao.ItemsDao;
import com.ditzweb.dao.QuantityDao;

public class ItemsQuantityModal {

	private int id = 0;
	private Items items;
	private Quantity qtys;
	private boolean satuanDasar = false;
	private boolean disable = false;

	@WireVariable
	ItemsDao itemsDao = (ItemsDao) SpringUtil.getBean("itemsDao");

	@WireVariable
	QuantityDao quantityDao = (QuantityDao) SpringUtil.getBean("quantityDao");

	@WireVariable
	ItemsBasicUnitDao itemsBasicUnitDao = (ItemsBasicUnitDao) SpringUtil.getBean("itemsBasicUnitDao");

	private List<Items> item = itemsDao.getAll();
	private List<Quantity> qty = quantityDao.getAll();

	private Status status = Status.valueOf("Aktif");
	private ItemsQuantity itemsQuantity;

	@Init
	public void showData(@ExecutionArgParam("data") ItemsQuantity obj) {
		this.id = obj.getId();
		if (obj.getStatus() != null) {
			this.status = obj.getStatus();
		}

		if (obj.getItem() != null && obj.getQty() != null) {
			this.items = itemsDao.getById(obj.getItem().getId());
			this.qtys = quantityDao.getById(obj.getQty().getId());
			ItemsBasicUnit ibu = itemsBasicUnitDao.getItems(obj.getItem().getId());
			if (ibu.getSatuanDasar() == this.qtys) {
				this.satuanDasar = true;
				this.disable = true;
			}
		}
	}

	@Command
	public void add() {
		itemsQuantity = new ItemsQuantity();
		itemsQuantity.setId(this.id);
		itemsQuantity.setStatus(this.status);
		if (this.items != null && this.qtys != null) {
			itemsQuantity.setItem(this.items);
			itemsQuantity.setQty(this.qtys);
			Items i = this.items;
			ItemsBasicUnit ibu = new ItemsBasicUnit();
			ItemsBasicUnit ibc = itemsBasicUnitDao.getItems(i.getId());
			if (satuanDasar == true) {
				ibu.setItem(this.items);
				ibu.setSatuanDasar(this.qtys);
				if (ibc == null) {
					itemsBasicUnitDao.save(ibu);
					itemsQuantity.setSatuanDasar(ibu);
				} else if (ibc != null) {
					ibu.setId(ibc.getId());
					itemsQuantity.setSatuanDasar(ibu);
					itemsBasicUnitDao.update(ibu);
				}
			} else {
				if (ibc != null) {
					itemsQuantity.setSatuanDasar(ibc);
				} else {
					ibu.setItem(this.items);
					ibu.setSatuanDasar(this.qtys);
					itemsBasicUnitDao.save(ibu);
					itemsQuantity.setSatuanDasar(ibu);
				}
			}
		}
		Map<String, Object> dt = new HashMap<String, Object>();
		dt.put("obj", itemsQuantity);
		BindUtils.postGlobalCommand(null, null, "addItemsQuantity", dt);

	}
	
	@Command
	public void update() {
		itemsQuantity = new ItemsQuantity();
		itemsQuantity.setId(this.id);
		itemsQuantity.setStatus(this.status);
		if (this.items != null && this.qtys != null) {
			itemsQuantity.setItem(this.items);
			itemsQuantity.setQty(this.qtys);
			Items i = this.items;
			ItemsBasicUnit ibu = new ItemsBasicUnit();
			ItemsBasicUnit ibc = itemsBasicUnitDao.getItems(i.getId());
			if (satuanDasar == true) {
				ibu.setItem(this.items);
				ibu.setSatuanDasar(this.qtys);
				if (ibc == null) {
					itemsBasicUnitDao.save(ibu);
					itemsQuantity.setSatuanDasar(ibu);
				} else if (ibc != null) {
					ibu.setId(ibc.getId());
					itemsQuantity.setSatuanDasar(ibu);
					itemsBasicUnitDao.update(ibu);
				}
			} else {
				if (ibc != null) {
					itemsQuantity.setSatuanDasar(ibc);
				} else {
					ibu.setItem(this.items);
					ibu.setSatuanDasar(this.qtys);
					itemsBasicUnitDao.save(ibu);
					itemsQuantity.setSatuanDasar(ibu);
				}
			}
		}
		Map<String, Object> dt = new HashMap<String, Object>();
		dt.put("obj", itemsQuantity);
		BindUtils.postGlobalCommand(null, null, "updateItemsQuantity", dt);

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

	public ItemsQuantity getItemsQuantity() {
		return itemsQuantity;
	}

	public void setItemsQuantity(ItemsQuantity itemsQuantity) {
		this.itemsQuantity = itemsQuantity;
	}

	public List<Items> getItem() {
		return item;
	}

	public void setItem(List<Items> item) {
		this.item = item;
	}

	public List<Quantity> getQty() {
		return qty;
	}

	public void setQty(List<Quantity> qty) {
		this.qty = qty;
	}

	public Items getItems() {
		return items;
	}

	public void setItems(Items items) {
		this.items = items;
	}

	public Quantity getQtys() {
		return qtys;
	}

	public void setQtys(Quantity qtys) {
		this.qtys = qtys;
	}

	public boolean getSatuanDasar() {
		return satuanDasar;
	}

	public void setSatuanDasar(boolean satuanDasar) {
		this.satuanDasar = satuanDasar;
	}

	public boolean isDisable() {
		return disable;
	}

	public void setDisable(boolean disable) {
		this.disable = disable;
	}

}
