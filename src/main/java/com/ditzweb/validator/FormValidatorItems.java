package com.ditzweb.validator;

import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.validator.AbstractValidator;


public class FormValidatorItems extends AbstractValidator {

	

	public void validate(ValidationContext ctx) {

		validateNama(ctx, (String) ctx.getValidatorArg("nama"));
		validateDeskripsi(ctx, (String) ctx.getValidatorArg("deskripsi"));

	}

	private void validateNama(ValidationContext ctx, String nama) {
		if (nama==null||nama.equals("")) {
			this.addInvalidMessage(ctx, "nama", "Harus di isi");
		} else if (nama.length() < 3) {
			this.addInvalidMessage(ctx, "nama", "Panjang min 2");
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
