package com.ditzweb.validator;

import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.validator.AbstractValidator;

import com.ditzweb.beans.Client;

public class FormValidatorTransaksiOutUpdate extends AbstractValidator {

	public void validate(ValidationContext ctx) {
		validateBanPenjualan(ctx, (String) ctx.getValidatorArg("noBanPenjualan"));
		validateClient(ctx, (Client) ctx.getValidatorArg("cl"));
		validatenoKendaraan(ctx, (String) ctx.getValidatorArg("noKendaraan"));

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
