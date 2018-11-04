package com.ditzweb.controllers;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.ditzweb.beans.ItemSupplaiIn;
import com.ditzweb.beans.Stock;
import com.ditzweb.beans.Supplier;
import com.ditzweb.beans.TransaksiIn;
import com.ditzweb.dao.ItemSupplaiInDao;
import com.ditzweb.dao.StockDao;
import com.ditzweb.dao.SupplierDao;
import com.ditzweb.dao.TransaksiInDao;

public class TransaksiInUpdateRedirectController {
	@Wire("#transaksiinupdate")
	private Window window;
	private String noFaktur;
	private String noKendaraan;
	private Date date;
	Supplier sup;
	HashMap<String, TransaksiIn> map = null;
	TransaksiIn transIn;
	private String addDisabled = "false";
	private String editDisabled = "false";

	@WireVariable
	SupplierDao supplierDao = (SupplierDao) SpringUtil.getBean("supplierDao");

	@WireVariable
	TransaksiInDao transaksiInDao = (TransaksiInDao) SpringUtil.getBean("transaksiInDao");

	@WireVariable
	ItemSupplaiInDao itemSupplaiInDao = (ItemSupplaiInDao) SpringUtil.getBean("itemSupplaiInDao");

	@WireVariable
	StockDao stockDao = (StockDao) SpringUtil.getBean("stockDao");

	ListModelList<ItemSupplaiIn> itemSupplaiIn;

	private List<Supplier> supplier = supplierDao.getAll();

	@SuppressWarnings("unchecked")
	@Init
	public void initSetup() {

		map = (HashMap<String, TransaksiIn>) Sessions.getCurrent().getAttribute("allmyvalues");
		if (this.map != null) {
			for (TransaksiIn tIn : map.values()) {
				this.noFaktur = tIn.getNoFaktur();
				this.sup = supplierDao.getById(tIn.getNamaSupplier().getId());
				this.noKendaraan = tIn.getNoKendaraan();
				this.date = tIn.getTanggal();
				this.transIn = tIn;
			}
			itemSupplaiIn = new ListModelList<ItemSupplaiIn>(itemSupplaiInDao.getAll(transIn.getId()));
			Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) SecurityContextHolder.getContext()
					.getAuthentication().getAuthorities();
			boolean hasRoleOwner = false;
			boolean hasRoleMarketing = false;
			boolean hasRoleStockish = false;
			for (GrantedAuthority authority : authorities) {
				hasRoleOwner = authority.getAuthority().equals("ROLE_OWNER");
				hasRoleMarketing = authority.getAuthority().equals("ROLE_MARKETING");
				hasRoleStockish = authority.getAuthority().equals("ROLE_STOKISH");
				if (hasRoleStockish) {
					this.addDisabled = "true";
					this.editDisabled = "false";
				}
				if (hasRoleMarketing) {
					this.addDisabled = "false";
					this.editDisabled = "false";
				}
				if (hasRoleOwner) {
					this.addDisabled = "false";
					this.editDisabled = "true";
				}
				if (hasRoleMarketing && hasRoleStockish) {
					this.addDisabled = "true";
					this.editDisabled = "false";
				}
				if (hasRoleStockish && hasRoleOwner) {
					this.addDisabled = "true";
					this.editDisabled = "true";
					break;
				}

			}
		} else {
			Executions.sendRedirect("transaksiin.zul");
		}

	}

	@GlobalCommand
	@NotifyChange("itemSupplai")
	public void updateTransIn(@BindingParam("obj2") TransaksiIn obj) {
		obj.getId();
		obj.getNamaSupplier();
		obj.getNoKendaraan();
	}

	@Command
	public void add() {
		TransaksiIn tIn = this.transIn;
		tIn.setNoFaktur(this.noFaktur);
		tIn.setNamaSupplier(this.sup);
		tIn.setNoKendaraan(this.noKendaraan);
		transaksiInDao.update(tIn);
		Executions.sendRedirect("transaksiin.zul");
	}

	@Command
	public void addBarang() {
		ItemSupplaiIn obj = new ItemSupplaiIn();
		HashMap<String, ItemSupplaiIn> dt = new HashMap<String, ItemSupplaiIn>();
		dt.put("data", obj);
		window = (Window) Executions.createComponents("/itemsupplaiinupdateadd.zul", null, dt);
		window.doModal();
	}

	@Command
	public void updateModal(@BindingParam("obj") ItemSupplaiIn obj) {
		HashMap<String, ItemSupplaiIn> dt = new HashMap<String, ItemSupplaiIn>();
		dt.put("data", obj);
		window = (Window) Executions.createComponents("/itemsupplaiinupdateupdate.zul", null, dt);
		window.doModal();
	}

	@GlobalCommand
	@NotifyChange("itemSupplaiIn")
	public void addUpdateItemsSupplaiIn(@BindingParam("obj") ItemSupplaiIn obj) {
		obj.setTransIn(this.transIn);
		itemSupplaiInDao.save(obj);
		Stock stock = stockDao.getStock(obj.getItem().getId());
		if (stock != null) {
			stock.setJumlahStock(stock.getJumlahStock() + obj.getJumlah());
			stockDao.updateStock(stock);
		} else if (stock == null) {
			stockDao.save(stock);
		}

		window.detach();
		this.initSetup();
	}

	@GlobalCommand
	@NotifyChange("itemSupplaiIn")
	public void updateupdateItemSupplaiIn(@BindingParam("obj") ItemSupplaiIn obj) {
		ItemSupplaiIn itemz = itemSupplaiInDao.getById(obj.getId());
		double hasil = 0;
		if (itemz.getJumlah() != null && obj.getJumlah() != null) {
			hasil = obj.getJumlah() - itemz.getJumlah();
		}
		itemSupplaiInDao.update(obj);
		Stock stock = stockDao.getStock(obj.getItem().getId());
		stock.setJumlahStock(stock.getJumlahStock() + hasil);
		stockDao.updateStock(stock);
		window.detach();
		Executions.sendRedirect("transaksiinupdate.zul");
	}

	@GlobalCommand
	@NotifyChange("itemSupplaiIn")
	public void updateUpdateHargaItemSupplaiIn(@BindingParam("obj") ItemSupplaiIn obj) {
		itemSupplaiInDao.updateHarga(obj);
		window.detach();
		Executions.sendRedirect("transaksiinupdate.zul");
	}

	@Command
	@NotifyChange("itemSupplaiIn")
	public void delete(@BindingParam("obj") ItemSupplaiIn obj) {

		Stock stock = stockDao.getStock(obj.getItem().getId());
		if (stock.getJumlahStock() - obj.getJumlah() > 0) {
			itemSupplaiInDao.delete(obj.getId());
			stock.setJumlahStock(stock.getJumlahStock() - obj.getJumlah());
			stockDao.updateStock(stock);
		} else {
			Messagebox.show("Stock akan kurang dari 0 jika item di hapus");
		}

		this.initSetup();
	}

	@Command
	@NotifyChange("itemSupplaiIn")
	public void addHarga(@BindingParam("obj") ItemSupplaiIn obj) {
		HashMap<String, ItemSupplaiIn> dt = new HashMap<String, ItemSupplaiIn>();
		dt.put("data", obj);
		window = (Window) Executions.createComponents("/itemsupplaiinupdateupdateharga.zul", null, dt);
		window.doModal();
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

	public ListModelList<ItemSupplaiIn> getItemSupplaiIn() {
		return itemSupplaiIn;
	}

	public void setItemSupplaiIn(ListModelList<ItemSupplaiIn> itemSupplaiIn) {
		this.itemSupplaiIn = itemSupplaiIn;
	}

	public HashMap<String, TransaksiIn> getMap() {
		return map;
	}

	public void setMap(HashMap<String, TransaksiIn> map) {
		this.map = map;
	}

	public TransaksiIn getTransIn() {
		return transIn;
	}

	public void setTransIn(TransaksiIn transIn) {
		this.transIn = transIn;
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

}
