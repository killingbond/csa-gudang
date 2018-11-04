package com.ditzweb.dao;




import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ditzweb.beans.ItemsBasicUnit;




@Service
@Transactional
public class ItemsBasicUnitDaoImpl implements ItemsBasicUnitDao {

	@Autowired
	SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public void save(ItemsBasicUnit bsc) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.save(bsc);

	}

	
	public ItemsBasicUnit getItems(int id) {
		Session currentSession = sessionFactory.getCurrentSession();
		ItemsBasicUnit bsc = (ItemsBasicUnit) currentSession.createQuery("from ItemsBasicUnit I  where I.item.id = :itemsId " )
												.setParameter("itemsId", id).uniqueResult();
		
		return bsc;
	}

	public void update(ItemsBasicUnit bsc) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query query =  currentSession.createQuery("update ItemsBasicUnit  set satuanDasar =:nama WHERE item.id = :itemsId ")
											.setParameter("itemsId", bsc.getItem().getId())
											.setParameter("nama", bsc.getSatuanDasar());
		query.executeUpdate();		
	}

	
}
