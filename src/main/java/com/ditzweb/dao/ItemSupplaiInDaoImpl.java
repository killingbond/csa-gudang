package com.ditzweb.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ditzweb.beans.ItemSupplaiIn;




@Service
@Transactional
public class ItemSupplaiInDaoImpl implements ItemSupplaiInDao{

	@Autowired
	SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	public List<ItemSupplaiIn> getAll(int id) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query query =  currentSession.createQuery("from ItemSupplaiIn WHERE transIn= "+id+" ");
		List<ItemSupplaiIn> list = query.list();	
		return list;
	}

	public ItemSupplaiIn getById(int id) {
		Session currentSession = sessionFactory.getCurrentSession();
		ItemSupplaiIn itemSupplaiIn = (ItemSupplaiIn) currentSession.createQuery("from ItemSupplaiIn I where I.id = :itemsId " )
												.setParameter("itemsId", id).uniqueResult();
		
		return itemSupplaiIn;
	}

	public void save(ItemSupplaiIn itemSupplaiIn) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.save(itemSupplaiIn);

	}

	public void update(ItemSupplaiIn itemSupplaiIn) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query query =  currentSession.createQuery("update ItemSupplaiIn set jumlah = :jumlah, qty = :qty WHERE id = :id ")
											.setParameter("id", itemSupplaiIn.getId())
											.setParameter("qty", itemSupplaiIn.getQty())
											.setParameter("jumlah", itemSupplaiIn.getJumlah());
										
		query.executeUpdate();									
		
	}

	public void delete(int id) {
		Session currentSession = sessionFactory.getCurrentSession();
		ItemSupplaiIn itemSupplaiIn = (ItemSupplaiIn) currentSession.get(ItemSupplaiIn.class, id);
		currentSession.delete(itemSupplaiIn);
	}
	
	public Double countStock(int id) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query query = currentSession.createQuery(
		        "select sum(jumlah) from ItemSupplaiIn I  where I.item.id=:id");
		query.setParameter("id", id);
		return (Double)query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<ItemSupplaiIn> getAllById(int id) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query query =  currentSession.createQuery("from ItemSupplaiIn WHERE item.id= "+id+" ");
		List<ItemSupplaiIn> list = query.list();	
		return list;
	}

	public void updateHarga(ItemSupplaiIn itemSup) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query query =  currentSession.createQuery("update ItemSupplaiIn set hargaSatuanDasar = :hargaSatuanDasar, hargaSatuanInput = :hargaSatuanInput"
				+ ", hargaTotal = :hargaTotal WHERE id = :id ")
											.setParameter("id", itemSup.getId())
											.setParameter("hargaSatuanDasar", itemSup.getHargaSatuanDasar())
											.setParameter("hargaSatuanInput", itemSup.getHargaSatuanInput())
											.setParameter("hargaTotal", itemSup.getHargaTotal());
										
		query.executeUpdate();		
		
	}
	

}
