package com.ditzweb.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.SpringUtil;

import com.ditzweb.beans.Role;
import com.ditzweb.beans.Roles;
import com.ditzweb.beans.User;
import com.ditzweb.dao.RolesDao;
import com.ditzweb.dao.UserDao;

public class LoginController extends SelectorComposer<Component> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Object contextPath = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/WEB-INF/initialadmin");

	@WireVariable
	UserDao userDao = (UserDao) SpringUtil.getBean("userDao");
	RolesDao rolesDao = (RolesDao) SpringUtil.getBean("rolesDao");

	@AfterCompose
	public void initsetup() throws IOException {
		String username = Files.readAllLines(Paths.get(contextPath.toString())).get(0);
		String password = Files.readAllLines(Paths.get(contextPath.toString())).get(0);
		if (userDao.getById(username) != null && userDao.getById(username).getPassword().equals(password)) {

		} else if (userDao.getById(username) != null && userDao.getById(username).getPassword() != password) {
			User user = new User();
			user.setUsername(username);
			user.setPassword(password);
			userDao.update(user);
		} else if (userDao.getById(username) == null) {
			User user = new User();
			user.setUsername(username);
			user.setPassword(password);
			userDao.save(user);
			Roles roles = new Roles();
			roles.setUser(user);
			roles.setRole(Role.ROLE_ADMIN);
			rolesDao.save(roles);
		}

	}

}
