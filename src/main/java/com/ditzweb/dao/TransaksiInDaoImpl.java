package com.ditzweb.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ditzweb.beans.ItemSupplaiIn;
import com.ditzweb.beans.TransaksiIn;

@Service
@Transactional
public class TransaksiInDaoImpl implements TransaksiInDao {

	@Autowired
	SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	public List<TransaksiIn> getAll() {
		Session currentSession = sessionFactory.getCurrentSession();
		List<TransaksiIn> list = currentSession.createQuery("from TransaksiIn").list();
		return list;
	}

	public TransaksiIn getById(int id) {
		Session currentSession = sessionFactory.getCurrentSession();
		TransaksiIn transaksiIn = (TransaksiIn) currentSession.createQuery("from TransaksiIn T WHERE T.id = :transId ")
				.setParameter("transId", id).uniqueResult();

		return transaksiIn;
	}

	public void save(TransaksiIn transaksiIn) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.save(transaksiIn);

	}

	public void update(TransaksiIn transaksiIn) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query query = currentSession.createQuery(
				"update TransaksiIn set noFaktur = :faktur, namaSupplier = :supplier, noKendaraan = :noKendaraan  WHERE id = :id ")
				.setParameter("id", transaksiIn.getId()).setParameter("faktur", transaksiIn.getNoFaktur())
				.setParameter("supplier", transaksiIn.getNamaSupplier())
				.setParameter("noKendaraan", transaksiIn.getNoKendaraan());
		query.executeUpdate();

	}

	public void delete(int id) {
		Session currentSession = sessionFactory.getCurrentSession();
		ItemSupplaiIn itemSupp = (ItemSupplaiIn) currentSession.get(ItemSupplaiIn.class, id);
		currentSession.delete(itemSupp);
	}

	@SuppressWarnings("unchecked")
	public List<TransaksiIn> getAllSearch(Date tanggalDari, Date tanggalKe, String idTransaksi, String noFaktur,
			String namaSupplier) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query query = currentSession.createQuery(
				"FROM TransaksiIn F WHERE  F.id like '%"+idTransaksi+"%' AND F.noFaktur like '%"+noFaktur+"%' AND F.namaSupplier.nama like '%"+namaSupplier+"%' AND F.tanggal BETWEEN :tanggalDari AND :tanggalKe")
				.setParameter("tanggalDari", tanggalDari).setParameter("tanggalKe", tanggalKe);
		List<TransaksiIn> list = query.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<TransaksiIn> getSearch(String id, String noFaktur, String namaSupplier) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query query = currentSession.createQuery(
				"FROM TransaksiIn F WHERE  F.id like '%"+id+"%' AND F.noFaktur like '%"+noFaktur+"%' AND F.namaSupplier.nama like '%"+namaSupplier+"%' ");
		List<TransaksiIn> list = query.list();
		return list;
	}

}
