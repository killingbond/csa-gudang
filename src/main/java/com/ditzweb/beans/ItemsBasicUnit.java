package com.ditzweb.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ItemsBasicUnit {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@ManyToOne
	private Items item;
	@ManyToOne
	private Quantity satuanDasar;
	
	public ItemsBasicUnit() {};
	
	public ItemsBasicUnit(int id, Items item, Quantity satuanDasar) {
		super();
		this.id = id;
		this.item = item;
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
	public Quantity getSatuanDasar() {
		return satuanDasar;
	}
	public void setSatuanDasar(Quantity satuanDasar) {
		this.satuanDasar = satuanDasar;
	}
	
	
	
}
