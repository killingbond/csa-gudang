package com.ditzweb.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ditzweb.beans.Items;


@Service
@Transactional
public class ItemsDaoImpl implements ItemsDao {

	@Autowired
	SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	public List<Items> getAll() {
		Session currentSession = sessionFactory.getCurrentSession();
		List<Items> list =  currentSession.createQuery("from Items").list();
		return list;
	}

	public Items getById(int id) {
		Session currentSession = sessionFactory.getCurrentSession();
		Items items = (Items) currentSession.createQuery("from Items I where I.id = :itemsId " )
												.setParameter("itemsId", id).uniqueResult();
		
		return items;
	}

	public void save(Items items) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.save(items);

	}

	public void update(Items items) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query query =  currentSession.createQuery("update Items set nama = :nama, deskripsi = :deskripsi "
				+ ", status = :status, file = :file WHERE id = :id ")
											.setParameter("id", items.getId())
											.setParameter("nama", items.getNama())
											.setParameter("deskripsi", items.getDeskripsi())
											.setParameter("status", items.getStatus())
											.setParameter("file", items.getFile());
		query.executeUpdate();									
		
	}

	public void delete(int id) {
		Session currentSession = sessionFactory.getCurrentSession();
		Items items = (Items) currentSession.get(Items.class, id);
		currentSession.delete(items);
	}

	@SuppressWarnings("unchecked")
	public List<Items>  getAllSearch(String id, String nama, String deskripsi,String status) {
		Session currentSession = sessionFactory.getCurrentSession();
		List<Items> list =  currentSession.createQuery("from Items I WHERE I.id like '%"+id+"%' AND I.nama like '%"+nama+"%' AND "
				+ "I.deskripsi like '%"+deskripsi+"%' AND I.status like '%"+status+"%'").list();
		
		return list;			
	
	}

}
