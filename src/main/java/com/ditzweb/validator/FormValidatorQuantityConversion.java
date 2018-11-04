package com.ditzweb.validator;

import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.validator.AbstractValidator;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.SpringUtil;

import com.ditzweb.beans.Quantity;
import com.ditzweb.dao.QuantityConversionDao;

public class FormValidatorQuantityConversion extends AbstractValidator {

	@WireVariable
	QuantityConversionDao quantityConversionDao = (QuantityConversionDao) SpringUtil.getBean("quantityConversionDao");

	public void validate(ValidationContext ctx) {
		validateDari(ctx, (Quantity) ctx.getValidatorArg("qtyDari"));
		validateKe(ctx, (Quantity) ctx.getValidatorArg("qtyDari"), ctx, (Quantity) ctx.getValidatorArg("qtyKe"));

	}

	private void validateDari(ValidationContext ctx, Quantity qtyDari) {
		if (qtyDari == null || qtyDari.equals("")) {
			this.addInvalidMessage(ctx, "qtyDari", "Harus di isi");
		}

	}

	private void validateKe(ValidationContext ctx, Quantity qtyDari, ValidationContext ctx2, Quantity qtyKe) {
		if (qtyKe == null) {
			this.addInvalidMessage(ctx, "qtyKe", "Harus di isi");
		} else {
			if (qtyDari.getId() == qtyKe.getId()) {
				this.addInvalidMessage(ctx, "qtyKe", "Tidak Perlu di konversi");
			} else if (quantityConversionDao.getByName(qtyDari.getId(), qtyKe.getId()) != null) {
				this.addInvalidMessage(ctx, "qtyKe", "Konversi Sudah Tersedia");
			}
		}

	}

}
