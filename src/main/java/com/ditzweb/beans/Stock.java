package com.ditzweb.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Stock {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@ManyToOne
	private Items item;
	private Double jumlahStock;
	@ManyToOne
	private ItemsBasicUnit itemsBasicUnit;
	
	public Stock() {}
	
	public Stock(int id, Items item, Double jumlahStock, ItemsBasicUnit itemsBasicUnit) {
		super();
		this.id = id;
		this.item = item;
		this.jumlahStock = jumlahStock;
		this.itemsBasicUnit = itemsBasicUnit;
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

	public Double getJumlahStock() {
		return jumlahStock;
	}

	public void setJumlahStock(Double jumlahStock) {
		this.jumlahStock = jumlahStock;
	}

	public ItemsBasicUnit getItemsBasicUnit() {
		return itemsBasicUnit;
	}
	public void setItemsBasicUnit(ItemsBasicUnit itemsBasicUnit) {
		this.itemsBasicUnit = itemsBasicUnit;
	}
	
	
	
	
}
