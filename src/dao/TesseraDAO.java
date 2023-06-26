package dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import app.Application;
import entities.Tessera;
import entities.Utente;

public class TesseraDAO {

	private static Logger logger = LoggerFactory.getLogger(Application.class);

	private final EntityManager em;

	public TesseraDAO(EntityManager em) {
		this.em = em;
	}

	public void save(Tessera tessera) {
		EntityTransaction t = em.getTransaction();
		t.begin();
		em.persist(tessera);
		t.commit();
		logger.info(tessera.toString() + " salvato!");
	}

	public boolean checkValiditaAbbonamento(Long id, LocalDate dataScadenza) {
		List<Tessera> listaResult = new ArrayList<>();
		TypedQuery<Tessera> query = em.createNamedQuery("checkValiditaAbbonamento", Tessera.class);
		query.setParameter("idTessera", id);
		query.setParameter("dataScadenza", dataScadenza);
		query.setParameter("oggi", LocalDate.now());
		listaResult = query.getResultList();
		if (!listaResult.isEmpty()) {
			return true;
		}
		return false;
	}

	public Utente selectUtenteByIdTessera(Long id) {
		Utente u = null;
		TypedQuery<Tessera> query = em.createNamedQuery("selectUtenteByIdTessera", Tessera.class);
		query.setParameter("idTessera", id);
		Tessera t = query.getSingleResult();
		if (t != null) {
			u = t.getUtente();
		}
		return u;
	}

}
