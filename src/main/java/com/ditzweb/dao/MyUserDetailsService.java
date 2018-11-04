package com.ditzweb.dao;

import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ditzweb.beans.Roles;

@Service("myUserDetailsService")
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UserDao userDao ;
	@Autowired
	private RolesDao rolesDao;

	public UserDetails loadUserByUsername(String uname) throws UsernameNotFoundException, DataAccessException {
		User user = null;
		com.ditzweb.beans.User userDb = userDao.getById(uname);
		if (userDb != null) {
			String username = userDb.getUsername();
			String password = userDb.getPassword();
			boolean enabled = true;
			boolean accountNonExpired = true;
			boolean credentialsNonExpired = true;
			boolean accountNonLocked = true;
			List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
			List<Roles> roles =  rolesDao.getByName(username);
			for (Roles roles2 : roles) {
				authList.add(new GrantedAuthorityImpl(roles2.getRole().toString()));
			}
			try {
				user = new User(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked,
						authList);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		

		return user;

	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public RolesDao getRolesDao() {
		return rolesDao;
	}

	public void setRolesDao(RolesDao rolesDao) {
		this.rolesDao = rolesDao;
	}

}
