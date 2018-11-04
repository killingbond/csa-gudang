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
import org.zkoss.zul.Window;

import com.ditzweb.beans.Client;
import com.ditzweb.beans.ItemSupplaiOut;
import com.ditzweb.beans.Stock;
import com.ditzweb.beans.TransaksiOut;
import com.ditzweb.dao.ClientDao;
import com.ditzweb.dao.ItemSupplaiOutDao;
import com.ditzweb.dao.StockDao;
import com.ditzweb.dao.TransaksiOutDao;

public class TransaksiOutUpdateRedirectController {
	@Wire("#transaksioutupdate")
	private Window window;
	private String noBanPenjualan;
	private String noKendaraan;
	private Date date;
	Client cl;
	HashMap<String, TransaksiOut> map = null;
	TransaksiOut transOut;
	private String addDisabled = "false";
	private String editDisabled = "false";

	@WireVariable
	ClientDao clientDao = (ClientDao) SpringUtil.getBean("clientDao");

	@WireVariable
	TransaksiOutDao transaksiOutDao = (TransaksiOutDao) SpringUtil.getBean("transaksiOutDao");

	@WireVariable
	ItemSupplaiOutDao itemSupplaiOutDao = (ItemSupplaiOutDao) SpringUtil.getBean("itemSupplaiOutDao");

	@WireVariable
	StockDao stockDao = (StockDao) SpringUtil.getBean("stockDao");

	List<ItemSupplaiOut> itemSupplaiOut;

	private List<Client> client = clientDao.getAll();

	@SuppressWarnings("unchecked")
	@Init
	public void initSetup() {

		map = (HashMap<String, TransaksiOut>) Sessions.getCurrent().getAttribute("alloutmyvalues");
		if (this.map != null) {
			for (TransaksiOut tIn : map.values()) {
				this.noBanPenjualan = tIn.getNoBanPenjualan();
				this.cl = clientDao.getById(tIn.getNamaClient().getId());
				this.noKendaraan = tIn.getNoKendaraan();
				this.date = tIn.getTanggal();
				this.transOut = tIn;
			}
			itemSupplaiOut = itemSupplaiOutDao.getAll(transOut.getId());
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
			Executions.sendRedirect("transaksiout.zul");
		}

	}

	@GlobalCommand
	@NotifyChange("itemSupplai")
	public void updateTransOut(@BindingParam("obj2") TransaksiOut obj) {
		obj.getId();
		obj.getNamaClient();
		obj.getNoKendaraan();
	}

	@Command
	public void add() {
		TransaksiOut ts = this.transOut;
		ts.setNamaClient(this.cl);
		ts.setNoBanPenjualan(this.noBanPenjualan);
		ts.setNoKendaraan(this.noKendaraan);
		transaksiOutDao.update(ts);
		Executions.sendRedirect("transaksiout.zul");
	}

	@Command
	public void addBarang() {
		ItemSupplaiOut obj = new ItemSupplaiOut();
		HashMap<String, ItemSupplaiOut> dt = new HashMap<String, ItemSupplaiOut>();
		dt.put("data", obj);
		window = (Window) Executions.createComponents("/itemsupplaioutupdateadd.zul", null, dt);
		window.doModal();
	}

	@Command
	public void updateModal(@BindingParam("obj") ItemSupplaiOut obj) {
		HashMap<String, ItemSupplaiOut> dt = new HashMap<String, ItemSupplaiOut>();
		dt.put("data", obj);
		window = (Window) Executions.createComponents("/itemsupplaioutupdateupdate.zul", null, dt);
		window.doModal();
	}

	@GlobalCommand
	@NotifyChange("itemSupplaiOut")
	public void addUpdateItemsSupplaiOut(@BindingParam("obj") ItemSupplaiOut obj) {
		obj.setTransOut(this.transOut);
		itemSupplaiOutDao.save(obj);
		Stock stock = stockDao.getStock(obj.getItem().getId());
		if (stock != null) {
			stock.setJumlahStock(stock.getJumlahStock() - obj.getJumlah());
			stockDao.updateStock(stock);
		}
		window.detach();
		this.initSetup();
	}

	@GlobalCommand
	@NotifyChange("itemSupplaiOut")
	public void updateupdateItemSupplaiOut(@BindingParam("obj") ItemSupplaiOut obj) {
		ItemSupplaiOut itemz = itemSupplaiOutDao.getById(obj.getId());
		double hasil = 0;
		if (itemz.getJumlah() != null && obj.getJumlah() != null) {
			hasil = obj.getJumlah() - itemz.getJumlah();
		}
		itemSupplaiOutDao.update(obj);
		Stock stock = stockDao.getStock(obj.getItem().getId());
		stock.setJumlahStock(stock.getJumlahStock() - hasil);
		stockDao.updateStock(stock);
		window.detach();
		Executions.sendRedirect("transaksioutupdate.zul");
		this.initSetup();
	}

	@GlobalCommand
	@NotifyChange("itemSupplaiOut")
	public void updateUpdateHargaItemSupplaiOut(@BindingParam("obj") ItemSupplaiOut obj) {
		itemSupplaiOutDao.updateHarga(obj);
		window.detach();
		Executions.sendRedirect("transaksioutupdate.zul");
	}

	@Command
	public void cancel() {
		Executions.sendRedirect("transaksiin.zul");
	}

	@Command
	@NotifyChange("itemSupplaiOut")
	public void delete(@BindingParam("obj") ItemSupplaiOut obj) {
		itemSupplaiOutDao.delete(obj.getId());
		Stock stock = stockDao.getStock(obj.getItem().getId());
		stock.setJumlahStock(stock.getJumlahStock() + obj.getJumlah());
		stockDao.updateStock(stock);
		this.initSetup();
	}

	@Command
	@NotifyChange("ItemSupplaiOut")
	public void addHarga(@BindingParam("obj") ItemSupplaiOut obj) {
		HashMap<String, ItemSupplaiOut> dt = new HashMap<String, ItemSupplaiOut>();
		dt.put("data", obj);
		window = (Window) Executions.createComponents("/itemsupplaioutupdateupdateharga.zul", null, dt);
		window.doModal();
	}

	public String getNoBanPenjualan() {
		return noBanPenjualan;
	}

	public void setNoBanPenjualan(String noBanPenjualan) {
		this.noBanPenjualan = noBanPenjualan;
	}

	public Client getCl() {
		return cl;
	}

	public void setCl(Client cl) {
		this.cl = cl;
	}

	public TransaksiOut getTransOut() {
		return transOut;
	}

	public void setTransOut(TransaksiOut transOut) {
		this.transOut = transOut;
	}

	public List<ItemSupplaiOut> getItemSupplaiOut() {
		return itemSupplaiOut;
	}

	public void setItemSupplaiOut(List<ItemSupplaiOut> itemSupplaiOut) {
		this.itemSupplaiOut = itemSupplaiOut;
	}

	public List<Client> getClient() {
		return client;
	}

	public void setClient(List<Client> client) {
		this.client = client;
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
