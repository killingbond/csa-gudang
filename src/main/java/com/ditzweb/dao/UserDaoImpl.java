package com.ditzweb.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ditzweb.beans.User;


@Service 
@Transactional
public class UserDaoImpl implements UserDao{

	@Autowired
	SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@SuppressWarnings("unchecked")
	public List<User> getAll() {
		Session currentSession = sessionFactory.getCurrentSession();
		List<User> list = currentSession.createCriteria(User.class).list();
		return list;
	}

	public User getById(String id) {
		Session currentSession = sessionFactory.getCurrentSession();
		User user = (User) currentSession.get(User.class, id);
		return user;
	}

	public void save(User user) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.save(user);	
	}

	public void update(User user) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		Query us = currentSession.createQuery("update User set nama=:nama, deskripsi=:deskripsi, npk=:npk, alamat=:alamat, "
				+ "noKontak=:noKontak, foto=:foto, status=:status WHERE username=:username")
				.setParameter("nama", user.getNama())
				.setParameter("deskripsi", user.getDeskripsi())
				.setParameter("npk", user.getNpk())
				.setParameter("alamat", user.getAlamat())
				.setParameter("noKontak", user.getNoKontak())
				.setParameter("foto", user.getFoto())
				.setParameter("status", user.getStatus())
				.setParameter("username", user.getUsername());
		
		us.executeUpdate();
		
	}

	public void delete(String id) {
		Session currentSession = sessionFactory.getCurrentSession();
		User user = (User) currentSession.get(User.class, id);
		currentSession.delete(user);
	}
	
	@SuppressWarnings("unchecked")
	public List<User>  getAllSearch(String id, String nama, String deskripsi,String status, String npk) {
		Session currentSession = sessionFactory.getCurrentSession();
		List<User> list =  currentSession.createQuery("from User I WHERE I.id like '%"+id+"%' AND I.nama like '%"+nama+"%' AND "
				+ "I.deskripsi like '%"+deskripsi+"%' AND I.status like '%"+status+"%' AND I.npk like '%"+npk+"%' ").list();
		
		return list;			
	
	}

	public User getByName(String nama) {
		Session currentSession = sessionFactory.getCurrentSession();
		User list =  (User) currentSession.createQuery("from User U WHERE U.username = :username")
				.setParameter("username", nama)
				.uniqueResult();
		return list;
	}

	
}
