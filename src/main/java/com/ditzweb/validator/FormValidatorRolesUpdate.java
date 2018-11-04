package com.ditzweb.validator;


import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.validator.AbstractValidator;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.SpringUtil;

import com.ditzweb.beans.Roles;
import com.ditzweb.beans.User;
import com.ditzweb.dao.RolesDao;

public class FormValidatorRolesUpdate extends AbstractValidator{

	
	@WireVariable
	RolesDao rolesDao = (RolesDao) SpringUtil.getBean("rolesDao");
	
	public void validate(ValidationContext ctx) {
		validateRoles(ctx, (User)ctx.getValidatorArg("username"),(String)ctx.getValidatorArg("rl"),(String)ctx.getValidatorArg("initRoles"));
		
	}

	

	private void validateRoles(ValidationContext ctx, User username, String roles, String initRoles) {
		if(!roles.equals(initRoles)) {
			boolean DuplicateRoles=false;
			for (Roles rolez : rolesDao.getByName(username.getUsername())) {
				if(rolez.getRole().toString().equals(roles)) {
					
					DuplicateRoles=true;
				}
			}
			if(DuplicateRoles) {
				this.addInvalidMessage(ctx,"roles","Roles Duplikasi");
			}
		}
		
	}




	

	

	
	
	

	
	
}
