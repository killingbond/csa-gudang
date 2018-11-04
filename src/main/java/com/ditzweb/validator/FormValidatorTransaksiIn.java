package com.ditzweb.validator;

import java.util.HashMap;

import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.validator.AbstractValidator;

import com.ditzweb.beans.ItemSupplaiIn;
import com.ditzweb.beans.Supplier;

public class FormValidatorTransaksiIn extends AbstractValidator {

	@SuppressWarnings("unchecked")
	public void validate(ValidationContext ctx) {
		validateItemSupplai(ctx, (HashMap<Integer, ItemSupplaiIn>) ctx.getValidatorArg("itemSupplai"));
		validateFaktur(ctx, (String) ctx.getValidatorArg("noFaktur"));
		validateSupplier(ctx, (Supplier) ctx.getValidatorArg("sup"));
		validatenoKendaraan(ctx, (String) ctx.getValidatorArg("noKendaraan"));

	}

	private void validateItemSupplai(ValidationContext ctx, HashMap<Integer, ItemSupplaiIn> hash) {
		if (hash.isEmpty()) {
			this.addInvalidMessage(ctx, "hash", "Belum Menambah Barang");
		}

	}

	private void validateFaktur(ValidationContext ctx, String noFaktur) {
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
