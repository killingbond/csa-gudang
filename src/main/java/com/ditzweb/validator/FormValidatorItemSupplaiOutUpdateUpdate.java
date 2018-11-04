package com.ditzweb.validator;

import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.validator.AbstractValidator;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.SpringUtil;

import com.ditzweb.beans.Items;
import com.ditzweb.beans.ItemsBasicUnit;
import com.ditzweb.beans.ItemsQuantity;
import com.ditzweb.dao.ItemSupplaiOutDao;
import com.ditzweb.dao.ItemsDao;
import com.ditzweb.dao.QuantityConversionDao;
import com.ditzweb.dao.StockDao;

public class FormValidatorItemSupplaiOutUpdateUpdate extends AbstractValidator {

	@WireVariable
	QuantityConversionDao quantityConversionDao = (QuantityConversionDao) SpringUtil.getBean("quantityConversionDao");

	@WireVariable
	StockDao stockDao = (StockDao) SpringUtil.getBean("stockDao");

	@WireVariable
	ItemsDao itemsDao = (ItemsDao) SpringUtil.getBean("itemsDao");

	ItemSupplaiOutDao itemSupplaiOutDao = (ItemSupplaiOutDao) SpringUtil.getBean("itemSupplaiOutDao");

	public void validate(ValidationContext ctx) {
		validateNamaBarang(ctx, (Items) ctx.getValidatorArg("items"));
		ValidateSatuan(ctx, (ItemsQuantity) ctx.getValidatorArg("itemsQuantity"), ctx,
				(ItemsBasicUnit) ctx.getValidatorArg("satuanDasar"));
		ValidateJmlQty(ctx, (Integer) ctx.getValidatorArg("jmlQty"));
		ValidateJmlSatuanDasar(ctx, (Double) ctx.getValidatorArg("jmlSatuanDasar"), ctx,
				(Integer) ctx.getValidatorArg("key"));
	}

	private void ValidateJmlSatuanDasar(ValidationContext ctx, Double jml, ValidationContext ctx2, Integer key) {
		if (jml == null || stockDao.getStock(itemSupplaiOutDao.getById(key).getItem().getId()) == null) {

		} else if ((stockDao.getStock(itemSupplaiOutDao.getById(key).getItem().getId()).getJumlahStock()
				- (jml - itemSupplaiOutDao.getById(key).getJumlah())) < 0) {
			this.addInvalidMessage(ctx, "jmlSatuanDasar", "Stock " + itemSupplaiOutDao.getById(key).getItem().getNama()
					+ " = " + stockDao.getStock(itemSupplaiOutDao.getById(key).getItem().getId()).getJumlahStock());
		}

	}

	private void validateNamaBarang(ValidationContext ctx, Items items) {
		if (items == null) {
			this.addInvalidMessage(ctx, "items", "Harus di isi");
		} else if (stockDao.getStock(items.getId()) == null) {
			this.addInvalidMessage(ctx, "items",
					"" + itemsDao.getById(items.getId()).getNama() + " Belum memiliki Stock");
		}
	}

	private void ValidateSatuan(ValidationContext ctx, ItemsQuantity itemsQuantity, ValidationContext ctx2,
			ItemsBasicUnit itemsBasicUnit) {
		if (itemsQuantity == null) {
			this.addInvalidMessage(ctx, "Satuan", "Harus di isi");
		} else if (itemsBasicUnit == null) {
			this.addInvalidMessage(ctx, "Satuan", "Harus di isi");
		} else if (quantityConversionDao.getByName(itemsQuantity.getQty().getId(),
				itemsBasicUnit.getSatuanDasar().getId()) == null) {
			if (itemsQuantity.getQty().getId() == itemsBasicUnit.getSatuanDasar().getId()) {

			} else {
				this.addInvalidMessage(ctx, "satuan", "Satuan " + itemsQuantity.getQty().getNama()
						+ " Belum di Konversi ke Satuan " + itemsBasicUnit.getSatuanDasar().getNama());
			}

		}

	}

	private void ValidateJmlQty(ValidationContext ctx, Integer jmlQty) {
		if (jmlQty == 0) {
			this.addInvalidMessage(ctx, "jmlQty", "Harus di isi");
		} else if (jmlQty < 1) {
			this.addInvalidMessage(ctx, "jmlQty", "Harus Lebih dari 0");
		}

	}

}
