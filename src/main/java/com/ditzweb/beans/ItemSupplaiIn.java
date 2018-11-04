package com.ditzweb.beans;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ItemSupplaiIn {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@ManyToOne
	private Items item;
	@ManyToOne
	private ItemsQuantity satuan;
	@ManyToOne
	private ItemsBasicUnit satuanDasar;
	private int qty;
	private Double jumlah;
	private Double hargaSatuanInput;
	private Double hargaSatuanDasar;
	private Double hargaTotal;
	@ManyToOne
	private TransaksiIn transIn;
	
	public ItemSupplaiIn() {}

	
	public ItemSupplaiIn(int id, Items item, ItemsQuantity satuan, ItemsBasicUnit satuanDasar, int qty, Double jumlah,
			Double hargaSatuanInput, Double hargaSatuanDasar, Double hargaTotal, TransaksiIn transIn) {
		super();
		this.id = id;
		this.item = item;
		this.satuan = satuan;
		this.satuanDasar = satuanDasar;
		this.qty = qty;
		this.jumlah = jumlah;
		this.hargaSatuanInput = hargaSatuanInput;
		this.hargaSatuanDasar = hargaSatuanDasar;
		this.hargaTotal = hargaTotal;
		this.transIn = transIn;
	}



	public Double getHargaSatuanInput() {
		return hargaSatuanInput;
	}



	public void setHargaSatuanInput(Double hargaSatuanInput) {
		this.hargaSatuanInput = hargaSatuanInput;
	}



	public Double getHargaSatuanDasar() {
		return hargaSatuanDasar;
	}



	public void setHargaSatuanDasar(Double hargaSatuanDasar) {
		this.hargaSatuanDasar = hargaSatuanDasar;
	}



	public Double getHargaTotal() {
		return hargaTotal;
	}



	public void setHargaTotal(Double hargaTotal) {
		this.hargaTotal = hargaTotal;
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

	public ItemsQuantity getSatuan() {
		return satuan;
	}

	public void setSatuan(ItemsQuantity satuan) {
		this.satuan = satuan;
	}

	public ItemsBasicUnit getSatuanDasar() {
		return satuanDasar;
	}

	public void setSatuanDasar(ItemsBasicUnit satuanDasar) {
		this.satuanDasar = satuanDasar;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public Double getJumlah() {
		return jumlah;
	}

	public void setJumlah(Double jumlah) {
		this.jumlah = jumlah;
	}

	public TransaksiIn getTransIn() {
		return transIn;
	}

	public void setTransIn(TransaksiIn transIn) {
		this.transIn = transIn;
	}
	
	
}
