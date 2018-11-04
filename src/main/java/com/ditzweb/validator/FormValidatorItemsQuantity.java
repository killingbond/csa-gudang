package com.ditzweb.validator;

import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.validator.AbstractValidator;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.SpringUtil;

import com.ditzweb.beans.Items;
import com.ditzweb.beans.ItemsQuantity;
import com.ditzweb.beans.Quantity;
import com.ditzweb.dao.ItemsQuantityDao;

public class FormValidatorItemsQuantity extends AbstractValidator {

	@WireVariable
	ItemsQuantityDao itemsQuantityDao = (ItemsQuantityDao) SpringUtil.getBean("itemsQuantityDao");

	public void validate(ValidationContext ctx) {
		validateBarang(ctx, (Items) ctx.getValidatorArg("items"));
		validateSatuan(ctx, (Quantity) ctx.getValidatorArg("qtys"), ctx, (Items) ctx.getValidatorArg("items"));
	}

	private void validateBarang(ValidationContext ctx, Items items) {
		if (items == null || items.equals("")) {
			this.addInvalidMessage(ctx, "items", "Harus di isi");
		}

	}

	private void validateSatuan(ValidationContext ctx, Quantity qty, ValidationContext ctx2, Items items) {
		if (qty == null|| qty.equals("")) {
			this.addInvalidMessage(ctx, "qty", "Harus di isi");
		} else {
			boolean isTrue = false;
			for (ItemsQuantity itemsQty : itemsQuantityDao.getByItems(items.getId())) {
				if (itemsQty.getQty().getId() == qty.getId()) {
					isTrue = true;
					break;
				}
			}
			if (isTrue) {
				this.addInvalidMessage(ctx, "qty", "Satuan Sudah Terdaftar");
			}
		}

	}

}
