package com.ditzweb.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ditzweb.beans.Client;



@Service
@Transactional
public class ClientDaoImpl implements ClientDao {
	@Autowired
	SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	public List<Client> getAll() {
		Session currentSession = sessionFactory.getCurrentSession();
		List<Client> list = currentSession.createQuery("FROM Client ").list();
		return list;
	}

	public Client getById(String id) {
		Session currentSession = sessionFactory.getCurrentSession();
		Client client = (Client) currentSession.get(Client.class, id);
		return client;
	}

	public void save(Client client) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.save(client);

	}

	public void update(Client client) {
		Session currentSession = sessionFactory.getCurrentSession();
		Client cl = (Client) currentSession.get(Client.class, client.getId());
		cl.setNama(client.getNama());
		cl.setDeskripsi(client.getDeskripsi());
		cl.setNamaKontak(client.getNamaKontak());
		cl.setTelpKontak(client.getTelpKontak());
		cl.setStatus(client.getStatus());
		currentSession.update(cl);
	}

	public void delete(String id) {
		Session currentSession = sessionFactory.getCurrentSession();
		Client client = (Client) currentSession.get(Client.class, id);
		currentSession.delete(client);
	}

	@SuppressWarnings("unchecked")
	public List<Client> getAllSearch(String id, String nama, String deskripsi, String status) {
		Session currentSession = sessionFactory.getCurrentSession();
		List<Client> list =  currentSession.createQuery("from Client I WHERE I.id like '%"+id+"%' AND I.nama like '%"+nama+"%' "
				+ "AND I.deskripsi like '%"+deskripsi+"%' AND I.status like '%"+status+"%' ").list();
		
		return list;		
		
	}

	@SuppressWarnings("unchecked")
	public List<Client> getAllByName(String id) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query query = currentSession.createQuery("from Client I WHERE I.id =:id ").setParameter("id", id);
		List<Client> list = query.list();
		return list;
	}
}
