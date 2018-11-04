package com.ditzweb.dao;

import java.util.List;

import com.ditzweb.beans.Roles;


public interface RolesDao {
	public List<Roles> getAll();
	public Roles getById(int id);
	public void save(Roles roles);
	public void update(Roles roles);
	public void delete(int id);
	public List<Roles> getByName(String roles);
	public List<Roles> getlAllSearch(String username, String nama, String roles, String status);
}
