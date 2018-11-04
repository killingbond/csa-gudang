package com.ditzweb.validator;

import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.validator.AbstractValidator;

import com.ditzweb.beans.Supplier;

public class FormValidatorTransaksiInUpdate extends AbstractValidator {

	public void validate(ValidationContext ctx) {
		validateNoFaktru(ctx, (String) ctx.getValidatorArg("noFaktur"));
		validateSupplier(ctx, (Supplier) ctx.getValidatorArg("sup"));
		validatenoKendaraan(ctx, (String) ctx.getValidatorArg("noKendaraan"));

	}

	private void validateNoFaktru(ValidationContext ctx, String noFaktur) {
		if (noFaktur == null || noFaktur.equals("")) {
			this.addInvalidMessage(ctx, "noFaktur", "Harus di isi");
		}

	}

	private void validateSupplier(ValidationContext ctx, Supplier sup) {
		if (sup == null) {
			this.addInvalidMessage(ctx, "sup", "Harus di isi");
		}

	}

	private void validatenoKendaraan(ValidationContext ctx, String noKendaraan) {
		if (noKendaraan == null || noKendaraan.equals("")) {
			this.addInvalidMessage(ctx, "noKendaraan", "Harus di isi");
		}

	}

}
