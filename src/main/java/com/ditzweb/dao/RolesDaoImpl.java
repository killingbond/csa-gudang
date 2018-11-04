package com.ditzweb.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ditzweb.beans.Roles;



@Service
@Transactional
public class RolesDaoImpl implements RolesDao {

	@Autowired
	SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	public List<Roles> getAll() {
		Session currentSession = sessionFactory.getCurrentSession();
		List<Roles> list = currentSession.createCriteria(Roles.class).list();
		return list;
	}

	public Roles getById(int id) {
		Session currentSession = sessionFactory.getCurrentSession();
		Roles roles = (Roles) currentSession.createQuery("from Roles R where R.id = :rolesId " )
				.setParameter("rolesId", id).uniqueResult();

		return roles;
	}

	public void save(Roles roles) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.save(roles);

	}

	public void update(Roles roles) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query us = currentSession.createQuery("UPDATE Roles SET role =:role, status =:status WHERE id =:id")
				.setParameter("role", roles.getRole())
				.setParameter("status", roles.getStatus())
				.setParameter("id", roles.getId());
		us.executeUpdate();
	}

	public void delete(int id) {
		Session currentSession = sessionFactory.getCurrentSession();
		Roles roles = (Roles) currentSession.get(Roles.class, id);
		currentSession.delete(roles);
	}

	@SuppressWarnings("unchecked")
	public List<Roles> getByName(String username) {
		Session currentSession = sessionFactory.getCurrentSession();
		List<Roles> list =  currentSession.createQuery("from Roles R  WHERE R.user.username= :uname")
				.setParameter("uname", username)
				.list();
		return list;
	}
	
	

	@SuppressWarnings("unchecked")
	public List<Roles> getlAllSearch(String username, String nama, String roles, String status) {
		Session currentSession = sessionFactory.getCurrentSession();
		List<Roles> list =  currentSession.createQuery("from Roles I WHERE I.user.username like '%"+username+"%' AND I.user.nama like '%"+nama+"%' "
				+ "AND I.role like '%"+roles+"%' AND I.status like '%"+status+"%' ").list();
		return list;
	}

	
	
}
