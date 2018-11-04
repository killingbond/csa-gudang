package com.ditzweb.validator;

import java.util.Map;

import org.zkoss.bind.Property;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.validator.AbstractValidator;



public class FormValidatorUserUpdate extends AbstractValidator{
	
	public void validate(ValidationContext ctx) {
		Map<String, Property> beanProps =  ctx.getProperties(ctx.getProperty().getBase());
		validateNama(ctx, (String)beanProps.get("nama").getValue());
		validateDeskripsi(ctx, (String)beanProps.get("deskripsi").getValue());
		validateNpk(ctx, (String)beanProps.get("npk").getValue());
		validateAlamat(ctx, (String)beanProps.get("alamat").getValue());
		validateNoKontak(ctx, (String)beanProps.get("noKontak").getValue());
	}
	
	

	private void validateNoKontak(ValidationContext ctx, String noKontak) {
		if (noKontak == null || noKontak.equals("")) {
			this.addInvalidMessage(ctx,"noKontak","harus di isi");
		}	
		
	}

	private void validateAlamat(ValidationContext ctx, String alamat) {
		if (alamat == null || alamat.equals("")) {
			this.addInvalidMessage(ctx,"alamat","harus di isi");
		}	
		
	}

	private void validateNpk(ValidationContext ctx, String npk) {
		if (npk == null || npk.equals("")) {
			this.addInvalidMessage(ctx,"npk","harus di isi");
		}	
		
	}

	private void validateDeskripsi(ValidationContext ctx, String deskripsi) {
		if (deskripsi == null || deskripsi.equals("")) {
			this.addInvalidMessage(ctx,"deskripsi","harus di isi");
		}	
	}

	
	private void validateNama(ValidationContext ctx, String nama) {
		if (nama == null || nama.equals("")) {
			this.addInvalidMessage(ctx,"nama","harus di isi");
		}
	}
	
}
