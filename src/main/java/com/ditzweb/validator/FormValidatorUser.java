package com.ditzweb.validator;

import java.util.Map;

import org.zkoss.bind.Property;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.validator.AbstractValidator;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.SpringUtil;

import com.ditzweb.dao.UserDao;

public class FormValidatorUser extends AbstractValidator {

	@WireVariable
	UserDao userDao = (UserDao) SpringUtil.getBean("userDao");

	public void validate(ValidationContext ctx) {
		Map<String, Property> beanProps = ctx.getProperties(ctx.getProperty().getBase());
		validateUsername(ctx, (String) beanProps.get("username").getValue());
		validatePassword(ctx, (String) beanProps.get("password").getValue());
		validateNama(ctx, (String) beanProps.get("nama").getValue());
		validateDeskripsi(ctx, (String) beanProps.get("deskripsi").getValue());
		validateNpk(ctx, (String) beanProps.get("npk").getValue());
		validateAlamat(ctx, (String) beanProps.get("alamat").getValue());
		validateNoKontak(ctx, (String) beanProps.get("noKontak").getValue());

	}

	private void validateNoKontak(ValidationContext ctx, String noKontak) {
		if (noKontak == null || noKontak == "") {
			this.addInvalidMessage(ctx, "noKontak", "harus di isi");
		} else if (!noKontak.matches("-?\\d+(\\.\\d+)?")) {
			this.addInvalidMessage(ctx, "npk", "di isi menggunakan angka");
		}

	}

	private void validateAlamat(ValidationContext ctx, String alamat) {
		if (alamat == null || alamat.equals("")) {
			this.addInvalidMessage(ctx, "alamat", "harus di isi");
		}

	}

	private void validateNpk(ValidationContext ctx, String npk) {
		if (npk == null || npk == "") {
			this.addInvalidMessage(ctx, "npk", "harus di isi");
		} else if (!npk.matches("-?\\d+(\\.\\d+)?")) {
			this.addInvalidMessage(ctx, "npk", "di isi menggunakan angka");
		}

	}

	private void validateDeskripsi(ValidationContext ctx, String deskripsi) {
		if (deskripsi == null || deskripsi == "") {
			this.addInvalidMessage(ctx, "deskripsi", "harus di isi");
		}
	}

	private void validateUsername(ValidationContext ctx, String username) {
		if (username == null) {
			this.addInvalidMessage(ctx, "username", "harus di isi");
		} else if (username.length() < 5) {
			this.addInvalidMessage(ctx, "username", "panjang harus lebih dari 4");
		} else if (userDao.getById(username) != null) {
			this.addInvalidMessage(ctx, "username", "username sudah terdaftar");
		}
	}

	private void validatePassword(ValidationContext ctx, String password) {
		if (password == null) {
			this.addInvalidMessage(ctx, "password", "harus di isi");
		} else if (password.length() < 5) {
			this.addInvalidMessage(ctx, "password", "panjang harus lebih dari 4");
		}
	}

	private void validateNama(ValidationContext ctx, String nama) {
		if (nama == null || nama == "") {
			this.addInvalidMessage(ctx, "nama", "harus di isi");
		}
	}

}
