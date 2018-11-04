package com.ditzweb.validator;

import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.validator.AbstractValidator;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.SpringUtil;

import com.ditzweb.dao.SupplierDao;

public class FormValidatorSupplierUpdate extends AbstractValidator {

	@WireVariable
	SupplierDao supplierDao = (SupplierDao) SpringUtil.getBean("supplierDao");

	public void validate(ValidationContext ctx) {

		validateNamaSupplier(ctx, (String) ctx.getValidatorArg("nama"));
		validateDeskripsi(ctx, (String) ctx.getValidatorArg("deskripsi"));
		validateNamaKontak(ctx, (String) ctx.getValidatorArg("namaKontak"));
		validateTelpKontak(ctx, (String) ctx.getValidatorArg("telpKontak"));

	}

	private void validateNamaSupplier(ValidationContext ctx, String nama) {
		if (nama.equals("")) {
			this.addInvalidMessage(ctx, "nama", "Harus di isi");
		} else if (nama.length() < 3) {
			this.addInvalidMessage(ctx, "nama", "Panjang min 2");
		}

	}

	private void validateDeskripsi(ValidationContext ctx, String deskripsi) {
		if (deskripsi.equals("")) {
			this.addInvalidMessage(ctx, "deskripsi", "Harus di isi");
		} else if (deskripsi.length() < 3) {
			this.addInvalidMessage(ctx, "deskripsi", "Panjang min 2");
		}
	}

	private void validateNamaKontak(ValidationContext ctx, String namaKontak) {
		if (namaKontak.equals("")) {
			this.addInvalidMessage(ctx, "namaKontak", "Harus di isi");
		} else if (namaKontak.length() < 3) {
			this.addInvalidMessage(ctx, "namaKontak", "Panjang min 2");
		}

	}

	private void validateTelpKontak(ValidationContext ctx, String telpKontak) {
		if (telpKontak == null || telpKontak.equals("")) {
			this.addInvalidMessage(ctx, "telpKontak", "harus di isi");
		} else if (!telpKontak.matches("-?\\d+(\\.\\d+)?")) {
			this.addInvalidMessage(ctx, "telpKontak", "di isi menggunakan angka");
		}

	}

}
