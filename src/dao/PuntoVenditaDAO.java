package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import app.Application;
import entities.DistributoreAutomatico;
import entities.PuntoVendita;

public class PuntoVenditaDAO {

	private static Logger logger = LoggerFactory.getLogger(Application.class);

	private final EntityManager em;

	public PuntoVenditaDAO(EntityManager em) {
		this.em = em;
	}

	public void save(PuntoVendita pv) {
		EntityTransaction t = em.getTransaction();
		t.begin();
		em.persist(pv);
		t.commit();
		logger.info(pv.toString() + " salvato!");
	}

	public PuntoVendita selectPuntoVenditaPerId(Long id) {
		TypedQuery<PuntoVendita> query = em.createNamedQuery("selectPuntoVenditaPerId", PuntoVendita.class);
		query.setParameter("idPuntoVendita", id);
		return query.getSingleResult();
	}

	public List<PuntoVendita> selectPuntiVendita() {
		TypedQuery<PuntoVendita> query = em.createNamedQuery("selectPuntiVendita", PuntoVendita.class);
		return query.getResultList();
	}

	public DistributoreAutomatico selectDistributore(Long id) {
		TypedQuery<DistributoreAutomatico> query = em.createNamedQuery("selectDistributore", DistributoreAutomatico.class);
		query.setParameter("idDistributore", id);
		return query.getSingleResult();
	}

	public List<DistributoreAutomatico> selectAllDistributori() {
		TypedQuery<DistributoreAutomatico> query = em.createNamedQuery("selectAllDistributori", DistributoreAutomatico.class);
		return query.getResultList();
	}

	 public void updateStato(Long id) {
	        DistributoreAutomatico found = em.find(DistributoreAutomatico.class, id);
	        found.setAttivo(!found.isAttivo());
	        EntityTransaction transaction = em.getTransaction();
	        transaction.begin();
	        em.persist(found);
	        transaction.commit();
	    }
}
