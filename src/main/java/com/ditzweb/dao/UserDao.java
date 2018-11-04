package com.ditzweb.dao;

import java.util.List;

import com.ditzweb.beans.User;

public interface UserDao {

	public List<User> getAll();
	public User getById(String id);
	public void save(User user);
	public void update(User user);
	public void delete(String id);
	public User getByName(String nama);
	public List<User> getAllSearch(String id,String nama, String deskripsi,String status, String npk);
}
