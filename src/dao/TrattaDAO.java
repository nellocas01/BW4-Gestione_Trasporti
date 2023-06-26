package dao;

import java.time.LocalTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import app.Application;
import entities.Tratta;

public class TrattaDAO {

	private static Logger logger = LoggerFactory.getLogger(Application.class);

	private final EntityManager em;

	public TrattaDAO(EntityManager em) {
		this.em = em;
	}

	public void save(Tratta tratta) {
		EntityTransaction t = em.getTransaction();
		t.begin();
		em.persist(tratta);
		t.commit();
		logger.info(tratta.toString() + " salvato!");
	}

	public LocalTime selectTempoEffettivoPerTratta(int idTratta) {
		TypedQuery<Tratta> query = em.createNamedQuery("selectTempoEffettivoPerTratta", Tratta.class);
		query.setParameter("idTratta", idTratta);
		Tratta tratta = query.getSingleResult();
		return tratta.getTempoEffettivo();
	}

	public List<Tratta> selectTratte() {
		TypedQuery<Tratta> query = em.createNamedQuery("selectTratte", Tratta.class);
		return query.getResultList();
	}

}
