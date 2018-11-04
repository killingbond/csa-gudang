package com.ditzweb.validator;

import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.validator.AbstractValidator;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.SpringUtil;

import com.ditzweb.beans.Quantity;
import com.ditzweb.dao.QuantityDao;


public class FormValidatorQuantity extends AbstractValidator {
	
	@WireVariable
	QuantityDao quantityDao = (QuantityDao) SpringUtil.getBean("quantityDao");

	public void validate(ValidationContext ctx) {
		validateNama(ctx, (String) ctx.getValidatorArg("namax"));
		validateDeskripsi(ctx, (String) ctx.getValidatorArg("deskripsi"));

	}

	private void validateNama(ValidationContext ctx, String nama) {
		if (nama==null||nama.equals("")) {
			this.addInvalidMessage(ctx, "nama", "Harus di isi");
		} else {
			boolean isTrue = false;
			for (Quantity qty : quantityDao.getAllByName(nama)) {
				if(qty.getNama().equalsIgnoreCase(nama)) {
					isTrue = true;
				}
			} 
			if (isTrue) {
				this.addInvalidMessage(ctx, "nama", "Satuan sudah ada");
			}
		}
	}

	private void validateDeskripsi(ValidationContext ctx, String deskripsi) {
		if (deskripsi==null||deskripsi.equals("")) {
			this.addInvalidMessage(ctx, "deskripsi", "Harus di isi");
		} else if (deskripsi.length() < 3) {
			this.addInvalidMessage(ctx, "deskripsi", "Panjang min 2");
		}
	}

}
