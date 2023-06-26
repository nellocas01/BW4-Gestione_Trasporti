package entities;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;

@Entity
@NamedQuery(name = "checkValiditaAbbonamento", query = "SELECT t FROM Tessera t WHERE t.id = :idTessera AND t.id IN (SELECT tk.tessera.id FROM Ticket tk WHERE tk.tessera.id = :idTessera AND :dataScadenza < :oggi)")
@NamedQuery(name = "selectUtenteByIdTessera", query = "SELECT t FROM Tessera t WHERE t.id = :idTessera")
public class Tessera {

	@Id
	@SequenceGenerator(name = "tessera_seq", sequenceName = "tessera_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tessera_seq")
	private Long id;
	private UUID numeroTessera;
	private LocalDate dataEmissione;
	private LocalDate dataScadenza;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "utente_id", referencedColumnName = "id")
	private Utente utente;

	@OneToMany(mappedBy = "tessera", cascade = CascadeType.ALL)
	@OrderBy(value = "tessera.numeroTessera")
	private List<Ticket> listaTicket;

	public List<Ticket> getListaTicket() {
		return listaTicket;
	}

	public void setTicket(List<Ticket> listaTicket) {
		this.listaTicket = listaTicket;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UUID getNumeroTessera() {
		return numeroTessera;
	}

	public void setNumeroTessera(UUID numeroTessera) {
		this.numeroTessera = numeroTessera;
	}

	public LocalDate getDataEmissione() {
		return dataEmissione;
	}

	public void setDataEmissione(LocalDate dataEmissione) {
		this.dataEmissione = dataEmissione;
	}

	public LocalDate getDataScadenza() {
		return dataScadenza;
	}

	public void setDataScadenza(LocalDate dataScadenza) {
		this.dataScadenza = dataScadenza;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

	public Utente getUtente() {
		return utente;
	}

	public Tessera() {

	}

	public Tessera(UUID numeroTessera, LocalDate dataEmissione, LocalDate dataScadenza, Utente utente) {
		setNumeroTessera(numeroTessera);
		setDataEmissione(dataEmissione);
		setDataScadenza(dataScadenza);
		setUtente(utente);
	}

	@Override
	public String toString() {
		return "Tessera [id=" + id + ", numeroTessera=" + numeroTessera + ", dataEmissione=" + dataEmissione
				+ ", dataScadenza=" + dataScadenza + ", utente=" + utente + "]";
	}
}
