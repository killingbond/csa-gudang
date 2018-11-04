package com.ditzweb.validator;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.validator.AbstractValidator;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.SpringUtil;

import com.ditzweb.beans.Client;
import com.ditzweb.beans.ItemSupplaiOut;
import com.ditzweb.dao.StockDao;

public class FormValidatorTransaksiOut extends AbstractValidator {

	@WireVariable
	StockDao stockDao = (StockDao) SpringUtil.getBean("stockDao");

	@SuppressWarnings("unchecked")
	public void validate(ValidationContext ctx) {
		validateItemSupplai(ctx, (HashMap<Integer, ItemSupplaiOut>) ctx.getValidatorArg("itemSupplai"));
		validateBanPenjualan(ctx, (String) ctx.getValidatorArg("noBanPenjualan"));
		validateClient(ctx, (Client) ctx.getValidatorArg("cl"));
		validatenoKendaraan(ctx, (String) ctx.getValidatorArg("noKendaraan"));

	}

	private void validateItemSupplai(ValidationContext ctx, HashMap<Integer, ItemSupplaiOut> hash) {
		if (hash.isEmpty()) {
			this.addInvalidMessage(ctx, "hash", "Belum Menambah Barang");
		} else if (hash.size() > 0) {
			Map<Integer, Double> arrayStock = new HashMap<Integer, Double>();
			if (hash.size() > 0) {
				for (ItemSupplaiOut is : hash.values()) {
					Double hasil = is.getJumlah();
					if (arrayStock.get(is.getItem().getId()) != null) {
						Double value = arrayStock.get(is.getItem().getId());
						hasil = hasil + value;
					}
					arrayStock.put(is.getItem().getId(), hasil);
				}
			}

			for (Entry<Integer, Double> entry : arrayStock.entrySet()) {
				if (stockDao.getStock(entry.getKey()) != null) {
					if (entry.getValue() > stockDao.getStock(entry.getKey()).getJumlahStock()) {
						this.addInvalidMessage(ctx, "hash",
								"Persediaan " + stockDao.getStock(entry.getKey()).getItem().getNama() + " Tinggal "
										+ stockDao.getStock(entry.getKey()).getJumlahStock().intValue());
						break;
					}
				} else if (stockDao.getStock(entry.getKey()) == null) {
					this.addInvalidMessage(ctx, "hash", "Stock Belum Tersedia");
					break;
				}
			}

		}

	}

	private void validateBanPenjualan(ValidationContext ctx, String noBanPenjualan) {
		if (noBanPenjualan == null || noBanPenjualan.equals("")) {
			this.addInvalidMessage(ctx, "noBanPenjualan", "Harus di isi");
		}

	}

	private void validateClient(ValidationContext ctx, Client cl) {
		if (cl == null) {
			this.addInvalidMessage(ctx, "cl", "Harus di isi");
		}

	}

	private void validatenoKendaraan(ValidationContext ctx, String noKendaraan) {
		if (noKendaraan == null || noKendaraan.equals("")) {
			this.addInvalidMessage(ctx, "noKendaraan", "Harus di isi");
		}

	}

}
