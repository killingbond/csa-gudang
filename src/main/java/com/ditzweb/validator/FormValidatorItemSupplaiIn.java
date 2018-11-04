package com.ditzweb.validator;

import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.validator.AbstractValidator;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.SpringUtil;


import com.ditzweb.beans.Items;
import com.ditzweb.beans.ItemsBasicUnit;
import com.ditzweb.beans.ItemsQuantity;

import com.ditzweb.dao.QuantityConversionDao;


public class FormValidatorItemSupplaiIn extends AbstractValidator {

	@WireVariable
	QuantityConversionDao quantityConversionDao = (QuantityConversionDao) SpringUtil.getBean("quantityConversionDao");

	public void validate(ValidationContext ctx) {
		validateNamaBarang(ctx, (Items) ctx.getValidatorArg("items"));
		ValidateSatuan(ctx, (ItemsQuantity) ctx.getValidatorArg("itemsQuantity"), ctx,
				(ItemsBasicUnit) ctx.getValidatorArg("satuanDasar"));
		ValidateJmlQty(ctx, (Integer) ctx.getValidatorArg("jmlQty"));
	}

	private void validateNamaBarang(ValidationContext ctx, Items items) {
		if (items == null) {
			this.addInvalidMessage(ctx, "items", "Harus di isi");
		}

	}

	private void ValidateSatuan(ValidationContext ctx, ItemsQuantity itemsQuantity, ValidationContext ctx2,
			ItemsBasicUnit itemsBasicUnit) {
		if(itemsQuantity==null) {
			this.addInvalidMessage(ctx, "Satuan", "Harus di isi");
		}else if (itemsBasicUnit==null) {
			this.addInvalidMessage(ctx, "Satuan", "Harus di isi");
		}else if(quantityConversionDao.getByName(itemsQuantity.getQty().getId(), itemsBasicUnit.getSatuanDasar().getId())==null) {
			if(itemsQuantity.getQty().getId()==itemsBasicUnit.getSatuanDasar().getId()) {
				
			}else {
				this.addInvalidMessage(ctx, "satuan", "Satuan "+ itemsQuantity.getQty().getNama()+" Belum di Konversi ke Satuan " + itemsBasicUnit.getSatuanDasar().getNama());
			}
			
		}

	}

	private void ValidateJmlQty(ValidationContext ctx, Integer jmlQty) {
		if (jmlQty == 0) {
			this.addInvalidMessage(ctx, "jmlQty", "Harus di isi");
		}else if(jmlQty<1) {
			this.addInvalidMessage(ctx, "jmlQty", "Harus Lebih dari 0");
		}

	}

}
