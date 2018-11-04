package com.ditzweb.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ditzweb.beans.QuantityConversion;

@Service
@Transactional
public class QuantityConversionDaoImpl implements QuantityConversionDao {

	@Autowired
	SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	public List<QuantityConversion> getAll() {
		Session currentSession = sessionFactory.getCurrentSession();
		List<QuantityConversion> list = currentSession.createQuery("from QuantityConversion I ").list();
		return list;
	}

	public QuantityConversion getById(int id) {
		Session currentSession = sessionFactory.getCurrentSession();
		QuantityConversion qc = (QuantityConversion) currentSession
				.createQuery("from QuantityConversion I where I.id = :itemsId ").setParameter("itemsId", id)
				.uniqueResult();

		return qc;
	}

	public void save(QuantityConversion qc) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.save(qc);

	}

	public void update(QuantityConversion qc) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query query = currentSession
				.createQuery("update QuantityConversion set besarKonversi = :besarKonversi , status = :status WHERE id = :id ")
				.setParameter("besarKonversi", qc.getBesarKonversi()).setParameter("id", qc.getId()).setParameter("status", qc.getStatus());
		query.executeUpdate();

	}

	public void delete(int id) {
		Session currentSession = sessionFactory.getCurrentSession();
		QuantityConversion qty = (QuantityConversion) currentSession.get(QuantityConversion.class, id);
		currentSession.delete(qty);
	}

	public QuantityConversion getByName(int dari, int ke) {
		Session currentSession = sessionFactory.getCurrentSession();
		QuantityConversion list = (QuantityConversion) currentSession.createQuery(
				"from QuantityConversion I WHERE I.konversiDari.id =" + dari + " " + "AND I.konversiKe.id = " + ke + "")
				.uniqueResult();
		return list;
	}

}
