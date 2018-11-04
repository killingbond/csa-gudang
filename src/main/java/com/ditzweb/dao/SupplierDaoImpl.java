package com.ditzweb.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ditzweb.beans.Supplier;




@Service
@Transactional
public class SupplierDaoImpl implements SupplierDao {

	@Autowired
	SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	public List<Supplier> getAll() {
		Session currentSession = sessionFactory.getCurrentSession();
		List<Supplier> list = currentSession.createCriteria(Supplier.class).list();
		return list;
	}

	public Supplier getById(String id) {
		Session currentSession = sessionFactory.getCurrentSession();
		Supplier supplier = (Supplier) currentSession.createQuery("from Supplier S where S.id = :supplierId " )
				.setParameter("supplierId", id).uniqueResult();

		return supplier;
	}

	public void save(Supplier supplier) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.save(supplier);

	}

	public void update(Supplier supplier) {
		Session currentSession = sessionFactory.getCurrentSession();
		Supplier br = (Supplier) currentSession.get(Supplier.class, supplier.getId());
		br.setNama(supplier.getNama());
		br.setNamaKontak(supplier.getNamaKontak());
		br.setTelpKontak(supplier.getTelpKontak());
		br.setDeskripsi(supplier.getDeskripsi());
		br.setStatus(supplier.getStatus());
		currentSession.update(br);
	}

	public void delete(String id) {
		Session currentSession = sessionFactory.getCurrentSession();
		Supplier supplier = (Supplier) currentSession.get(Supplier.class, id);
		currentSession.delete(supplier);
	}
	
	@SuppressWarnings("unchecked")
	public List<Supplier> getAllSearch(String id, String nama, String deskripsi,String status) {
		Session currentSession = sessionFactory.getCurrentSession();
		List<Supplier> list =  currentSession.createQuery("from Supplier I WHERE I.id like '%"+id+"%' AND I.nama like '%"+nama+"%' AND "
				+ "I.deskripsi like '%"+deskripsi+"%' AND I.status like '%"+status+"%' ").list();
		
		return list;		
		
	}

	@SuppressWarnings("unchecked")
	public List<Supplier> getAllByName(String id) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query query = currentSession.createQuery("from Supplier I WHERE I.id =:id ").setParameter("id", id);
		List<Supplier> list = query.list();
		return list;
	}

	

}
