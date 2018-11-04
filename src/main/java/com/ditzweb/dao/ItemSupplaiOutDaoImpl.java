package com.ditzweb.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ditzweb.beans.ItemSupplaiOut;



@Service
@Transactional
public class ItemSupplaiOutDaoImpl implements ItemSupplaiOutDao {
	
	@Autowired
	SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	public List<ItemSupplaiOut> getAll(int id) {
		Session currentSession = sessionFactory.getCurrentSession();
		List<ItemSupplaiOut> list =  currentSession.createQuery("from ItemSupplaiOut WHERE transOut= "+id+" ").list();
		return list;
	}

	public ItemSupplaiOut getById(int id) {
		Session currentSession = sessionFactory.getCurrentSession();
		ItemSupplaiOut itemSupplaiOut = (ItemSupplaiOut) currentSession.createQuery("from ItemSupplaiOut I where I.id = :itemsId " )
												.setParameter("itemsId", id).uniqueResult();
		
		return itemSupplaiOut;
	}

	public void save(ItemSupplaiOut itemSupplaiOut) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.save(itemSupplaiOut);

	}

	public void update(ItemSupplaiOut itemSupplaiOut) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query query =  currentSession.createQuery("update ItemSupplaiOut set jumlah = :jumlah, qty = :qty WHERE id = :id ")
											.setParameter("id", itemSupplaiOut.getId())
											.setParameter("qty", itemSupplaiOut.getQty())
											.setParameter("jumlah", itemSupplaiOut.getJumlah());
										
		query.executeUpdate();									
		
	}

	public void delete(int id) {
		Session currentSession = sessionFactory.getCurrentSession();
		ItemSupplaiOut itemSupplaiOut = (ItemSupplaiOut) currentSession.get(ItemSupplaiOut.class, id);
		currentSession.delete(itemSupplaiOut);
	}

	@SuppressWarnings("unchecked")
	public List<ItemSupplaiOut> getAllById(int id) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query query =  currentSession.createQuery("from ItemSupplaiOut WHERE item.id= "+id+" ");
		List<ItemSupplaiOut> list = query.list();	
		return list;
	}

	public void updateHarga(ItemSupplaiOut itemSup) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query query =  currentSession.createQuery("update ItemSupplaiOut set hargaSatuanDasar = :hargaSatuanDasar, hargaSatuanInput = :hargaSatuanInput"
				+ ", hargaTotal = :hargaTotal WHERE id = :id ")
											.setParameter("id", itemSup.getId())
											.setParameter("hargaSatuanDasar", itemSup.getHargaSatuanDasar())
											.setParameter("hargaSatuanInput", itemSup.getHargaSatuanInput())
											.setParameter("hargaTotal", itemSup.getHargaTotal());
										
		query.executeUpdate();		
		
	}
}
