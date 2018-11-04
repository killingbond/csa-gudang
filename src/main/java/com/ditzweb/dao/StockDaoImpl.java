package com.ditzweb.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ditzweb.beans.Stock;



@Service
@Transactional
public class StockDaoImpl implements StockDao {

	@Autowired
	SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@SuppressWarnings("unchecked")
	public List<Stock> getAll() {
		Session currentSession = sessionFactory.getCurrentSession();
		List<Stock> list = currentSession.createCriteria(Stock.class).list();
		return list;
	}

	public void save(Stock stock) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.save(stock);
		
	}

	
	public Stock getStock(int id) {
		Session currentSession = sessionFactory.getCurrentSession();
		Stock bsc = (Stock) currentSession.createQuery("from Stock I  where I.item.id = :itemsId " )
												.setParameter("itemsId", id).uniqueResult();
		return bsc;
		
	}

	public void updateStock(Stock stock) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query query =  currentSession.createQuery("update Stock set jumlahStock = :jumlahStock WHERE item.id = :id ")
											.setParameter("id", stock.getItem().getId())
											.setParameter("jumlahStock", stock.getJumlahStock());
		query.executeUpdate();	
	}

	@SuppressWarnings("unchecked")
	public List<Stock> getAllSearch(String id, String namaBarang) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query query = currentSession.createQuery(
				"FROM Stock F WHERE  F.id like '%"+id+"%' AND F.item.nama like '%"+namaBarang+"%' ");
		List<Stock> list = query.list();
		return list;
	}

}
