package com.ditzweb.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ItemsQuantity {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@ManyToOne
	private Items item;
	@ManyToOne
	private Quantity qty;
	@ManyToOne
	private ItemsBasicUnit satuanDasar;

	private Status status;
	
	public ItemsQuantity() {}
	
	public ItemsQuantity(int id, Items item, Quantity qty, Status status,ItemsBasicUnit satuanDasar) {
		super();
		this.id = id;
		this.item = item;
		this.qty = qty;
		this.status = status;
		this.satuanDasar = satuanDasar;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Items getItem() {
		return item;
	}
	public void setItem(Items item) {
		this.item = item;
	}
	public Quantity getQty() {
		return qty;
	}
	public void setQty(Quantity qty) {
		this.qty = qty;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}

	public ItemsBasicUnit getSatuanDasar() {
		return satuanDasar;
	}

	public void setSatuanDasar(ItemsBasicUnit satuanDasar) {
		this.satuanDasar = satuanDasar;
	}

	
	
	
	
	
}
