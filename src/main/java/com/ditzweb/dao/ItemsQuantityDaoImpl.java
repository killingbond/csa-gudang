package com.ditzweb.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ditzweb.beans.ItemsQuantity;

@Service
@Transactional
public class ItemsQuantityDaoImpl implements ItemsQuantityDao {

	@Autowired
	SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	public List<ItemsQuantity> getAll() {
		Session currentSession = sessionFactory.getCurrentSession();
		List<ItemsQuantity> list = currentSession.createQuery("from ItemsQuantity I ").list();
		return list;
	}

	public ItemsQuantity getById(int id) {
		Session currentSession = sessionFactory.getCurrentSession();
		ItemsQuantity itemsQty = (ItemsQuantity) currentSession
				.createQuery("from ItemsQuantity I where I.id = :itemsId ").setParameter("itemsId", id).uniqueResult();

		return itemsQty;
	}

	public void save(ItemsQuantity itemsQty) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.save(itemsQty);

	}

	public void update(ItemsQuantity itemsQty) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query query = currentSession
				.createQuery(
						"update ItemsQuantity set item = :item, qty = :qty " + ", status = :status WHERE id = :id ")
				.setParameter("id", itemsQty.getId()).setParameter("item", itemsQty.getItem())
				.setParameter("qty", itemsQty.getQty()).setParameter("status", itemsQty.getStatus());

		query.executeUpdate();

	}

	public void delete(int id) {
		Session currentSession = sessionFactory.getCurrentSession();
		ItemsQuantity itemsQty = (ItemsQuantity) currentSession.get(ItemsQuantity.class, id);
		currentSession.delete(itemsQty);
	}

	@SuppressWarnings("unchecked")
	public List<ItemsQuantity> getAllSearch(String id, String nama, String satuan, String status) {
		Session currentSession = sessionFactory.getCurrentSession();
		List<ItemsQuantity> list = currentSession
				.createQuery("from ItemsQuantity I WHERE I.item.id like '%" + id + "%' AND I.item.nama like '%" + nama
						+ "%' AND " + "I.qty.nama like '%" + satuan + "%' AND I.status like '%" + status + "%' ")
				.list();

		return list;

	}

	@SuppressWarnings("unchecked")
	public List<ItemsQuantity> getByItems(int item) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query query = currentSession.createQuery("from ItemsQuantity I WHERE I.item.id =:itemId ")
				.setParameter("itemId", item);
		List<ItemsQuantity> list = query.list();
		return list;
	}

	public void updateBasic(ItemsQuantity itemsQty) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query query = currentSession.createQuery("update ItemsQuantity set satuanDasar = :satuanDasar WHERE id = :id ")
				.setParameter("id", itemsQty.getId()).setParameter("satuanDasar", itemsQty.getSatuanDasar());

		query.executeUpdate();

	}

}
