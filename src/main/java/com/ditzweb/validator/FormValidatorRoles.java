package com.ditzweb.validator;


import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.validator.AbstractValidator;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.SpringUtil;

import com.ditzweb.beans.Roles;
import com.ditzweb.beans.User;
import com.ditzweb.dao.RolesDao;

public class FormValidatorRoles extends AbstractValidator{

	
	@WireVariable
	RolesDao rolesDao = (RolesDao) SpringUtil.getBean("rolesDao");
	
	public void validate(ValidationContext ctx) {
		validateRoles(ctx, (User)ctx.getValidatorArg("username"));
		validateRoles(ctx, (User)ctx.getValidatorArg("username"),(String)ctx.getValidatorArg("rl"));
		
	}

	
	private void validateRoles(ValidationContext ctx, User username) {
		if(username.getUsername()==null) {
			this.addInvalidMessage(ctx,"username","Harus di isi");
		}
		
	}


	private void validateRoles(ValidationContext ctx, User username, String roles) {
		boolean DuplicateRoles=false;
		for (Roles rolez : rolesDao.getByName(username.getUsername())) {
			if(rolez.getRole().toString().equals(roles)) {
				DuplicateRoles=true;
			}
		}
		if(roles.equals("")) {
			this.addInvalidMessage(ctx,"roles","Harus di isi");
		}if(DuplicateRoles) {
			this.addInvalidMessage(ctx,"roles","Roles Duplikasi");
		}
		
	}

	

	

	
	
	

	
	
}
