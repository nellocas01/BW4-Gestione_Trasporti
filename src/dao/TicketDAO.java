package dao;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import app.Application;
import entities.Ticket;

public class TicketDAO {

	private static Logger logger = LoggerFactory.getLogger(Application.class);

	private final EntityManager em;

	public TicketDAO(EntityManager em) {
		this.em = em;
	}

	public void save(Ticket ticket) {
		EntityTransaction t = em.getTransaction();
		t.begin();
		em.persist(ticket);
		t.commit();
		logger.info(ticket.toString() + " salvato!");
	}

	public int selectAllTickets(LocalDate dataInizio, LocalDate dataFine, Long id) {
		TypedQuery<Ticket> query = em.createNamedQuery("selectAllTickets", Ticket.class);
		query.setParameter("dataInizio", dataInizio);
		query.setParameter("dataFine", dataFine);
		query.setParameter("idPuntoVendita", id);
		return query.getResultList().size();
	}

	public int selectAllTicketsByIdVeicolo(UUID idVeicolo) {
		TypedQuery<Ticket> query = em.createNamedQuery("selectAllTicketsByIdVeicolo", Ticket.class);
		query.setParameter("idVeicolo", idVeicolo);
		return query.getResultList().size();
	}

	public int selectAllTicketsValidati(LocalDate dataInizio) {
		TypedQuery<Ticket> query = em.createNamedQuery("selectAllTicketsValidati", Ticket.class);
		query.setParameter("dataInizio", dataInizio);
		query.setParameter("dataFine", LocalDate.now());
		return query.getResultList().size();
	}

	public List<Ticket> selectAbbonamenti() {
		TypedQuery<Ticket> query = em.createNamedQuery("selectAbbonamenti", Ticket.class);
		return query.getResultList();
	}

	public Ticket selectAbbonamentiById(UUID idAbbonamento) {
		TypedQuery<Ticket> query = em.createNamedQuery("selectAbbonamentiById", Ticket.class);
		query.setParameter("idAbbonamento", idAbbonamento);
		return query.getSingleResult();
	}

}
