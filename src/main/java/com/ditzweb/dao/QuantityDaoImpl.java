package com.ditzweb.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ditzweb.beans.Quantity;




@Service
@Transactional
public class QuantityDaoImpl implements QuantityDao {
	
	@Autowired
	SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	public List<Quantity> getAll() {
		Session currentSession = sessionFactory.getCurrentSession();
		List<Quantity> list =  currentSession.createQuery("from Quantity").list();
		return list;
	}

	public Quantity getById(int id) {
		Session currentSession = sessionFactory.getCurrentSession();
		Quantity qty = (Quantity) currentSession.createQuery("from Quantity Q where Q.id = :qtyid " )
												.setParameter("qtyid", id).uniqueResult();
		
		return qty;
	}

	public void save(Quantity qty) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.save(qty);

	}

	public void update(Quantity qty) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query query =  currentSession.createQuery("update Quantity set nama = :nama, deskripsi = :deskripsi "
				+ ", status = :status WHERE id = :id ")
											.setParameter("id", qty.getId())
											.setParameter("nama", qty.getNama())
											.setParameter("deskripsi", qty.getDeskripsi())
											.setParameter("status", qty.getStatus());											
		query.executeUpdate();									
		
	}

	public void delete(int id) {
		Session currentSession = sessionFactory.getCurrentSession();
		Quantity qty = (Quantity) currentSession.get(Quantity.class, id);
		currentSession.delete(qty);
	}

	@SuppressWarnings("unchecked")
	public List<Quantity> getAllSearch(String id, String nama, String satuan,String status) {
		Session currentSession = sessionFactory.getCurrentSession();
		List<Quantity> list =  currentSession.createQuery("from Quantity I WHERE I.id like '%"+id+"%' AND I.nama like '%"+nama+"%' AND "
				+ " I.status like '%"+status+"%'  ").list();
		return list;		
		
	}

	
	@SuppressWarnings("unchecked")
	public List<Quantity> getAllByName(String name) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query query =  currentSession.createQuery("from Quantity I WHERE I.nama=:nama").setParameter("nama", name);
		List<Quantity> list = query.list();
		return list;	
	}

	
	

	
}
