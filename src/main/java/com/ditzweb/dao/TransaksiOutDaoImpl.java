package com.ditzweb.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ditzweb.beans.ItemSupplaiOut;
import com.ditzweb.beans.TransaksiOut;



@Service
@Transactional
public class TransaksiOutDaoImpl implements TransaksiOutDao {

	@Autowired
	SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	public List<TransaksiOut> getAll() {
		Session currentSession = sessionFactory.getCurrentSession();
		List<TransaksiOut> list =  currentSession.createQuery("from TransaksiOut").list();
		return list;
	}

	public TransaksiOut getById(int id) {
		Session currentSession = sessionFactory.getCurrentSession();
		TransaksiOut transaksiOut = (TransaksiOut) currentSession.createQuery("from TransaksiOut T WHERE T.id = :transId " )
												.setParameter("transId", id).uniqueResult();
		
		return transaksiOut;
	}

	public void save(TransaksiOut transaksiOut) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.save(transaksiOut);

	}

	public void update(TransaksiOut transaksiOut) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query query =  currentSession.createQuery("update TransaksiOut set noBanPenjualan = :banPenjualan, namaClient = :client "
				+ ", noKendaraan = :noKendaraan WHERE id = :id ")
											.setParameter("id", transaksiOut.getId())
											.setParameter("banPenjualan", transaksiOut.getNoBanPenjualan())
											.setParameter("client", transaksiOut.getNamaClient())
											.setParameter("noKendaraan", transaksiOut.getNoKendaraan());
		query.executeUpdate();									
		
	}

	public void delete(int id) {
		Session currentSession = sessionFactory.getCurrentSession();
		ItemSupplaiOut itemSupp = (ItemSupplaiOut) currentSession.get(ItemSupplaiOut.class, id);
		currentSession.delete(itemSupp);
	}

	@SuppressWarnings("unchecked")
	public List<TransaksiOut> getSearch(String id, String noBanPenjualan, String namaClient) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query query = currentSession.createQuery(
				"FROM TransaksiOut F WHERE  F.id like '%"+id+"%' AND F.noBanPenjualan like '%"+noBanPenjualan+"%' AND F.namaClient.nama like '%"+namaClient+"%' ");
		List<TransaksiOut> list = query.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<TransaksiOut> getAllSearch(Date tanggalDari, Date tanggalKe, String id, String noBanPenjualan,
			String namaClient) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query query = currentSession.createQuery(
				"FROM TransaksiOut F WHERE  F.id like '%"+id+"%' AND F.noBanPenjualan like '%"+noBanPenjualan+"%' AND F.namaClient.nama like '%"+namaClient+"%' AND F.tanggal BETWEEN :tanggalDari AND :tanggalKe")
				.setParameter("tanggalDari", tanggalDari).setParameter("tanggalKe", tanggalKe);
		List<TransaksiOut> list = query.list();
		return list;
	}
	

}
