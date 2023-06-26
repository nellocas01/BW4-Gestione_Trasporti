package entities;

import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;

import org.hibernate.annotations.Cascade;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name = "tipologia")
@NamedQuery(name = "selectAllTickets", query = "SELECT t FROM Ticket t WHERE t.puntoVendita.id = :idPuntoVendita AND :dataInizio < t.dataEmissione AND t.dataEmissione < :dataFine")
@NamedQuery(name = "selectAllTicketsByIdVeicolo", query = "SELECT t FROM Ticket t WHERE t.veicolo.id = :idVeicolo AND t.timbrato=true")
@NamedQuery(name = "selectAllTicketsValidati", query = "SELECT t FROM Ticket t WHERE :dataInizio < t.dataEmissione AND t.dataEmissione < :dataFine and t.timbrato=true")
@NamedQuery(name = "selectAbbonamenti", query = "SELECT t FROM Ticket t WHERE t.periodicita IS NOT NULL")
@NamedQuery(name = "selectAbbonamentiById", query = "SELECT t FROM Ticket t WHERE t.id = :idAbbonamento")
public abstract class Ticket {

	@Id
	private UUID id;
	private LocalDate dataEmissione;
	@ManyToOne
	@JoinColumn(name = "puntoVendita_id", referencedColumnName = "id", nullable = true)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private PuntoVendita puntoVendita;

	@ManyToOne
	@JoinColumn(name = "tessera_id", referencedColumnName = "id", nullable = true)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private Tessera tessera;

	@ManyToOne
	@JoinColumn(name = "veicolo_id", referencedColumnName = "id", nullable = true)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private Veicolo veicolo;

	// Getters & Setters
	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getId() {
		return id;
	}

	public LocalDate getDataEmissione() {
		return dataEmissione;
	}

	public void setDataEmissione(LocalDate dataEmissione) {
		this.dataEmissione = dataEmissione;
	}

	public PuntoVendita getPuntoVendita() {
		return puntoVendita;
	}

	public void setPuntoVendita(PuntoVendita puntoVendita) {
		this.puntoVendita = puntoVendita;
	}

	public void setTessera(Tessera tessera) {
		this.tessera = tessera;
	}

	public Tessera getTessera() {
		return tessera;
	}

	public Veicolo getVeicolo() {
		return veicolo;
	}

	public void setVeicolo(Veicolo veicolo) {
		this.veicolo = veicolo;
	}

	// Costruttore
	public Ticket() {

	}

	public Ticket(UUID id, LocalDate dataEmissione) {
		setId(id);
		setDataEmissione(dataEmissione);
	}

}
