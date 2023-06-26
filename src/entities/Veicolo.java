package entities;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NamedQuery(name = "selectNumeroViaggi", query = "SELECT v FROM Veicolo v WHERE v.id = :idVeicolo AND v.tratta.id = :idTratta")
@NamedQuery(name = "selectVeicoli", query = "SELECT v FROM Veicolo v")
@NamedQuery(name = "selectVeicoloById", query = "SELECT v FROM Veicolo v WHERE v.id = :idVeicolo")
public class Veicolo {

	// Attributi
	@Id
	@GeneratedValue
	private UUID id;

	private boolean statoServizio;
	private LocalDate dataInizioServizio;
	private LocalDate dataFineServizio;

	@OneToMany
	@JoinColumn(name = "veicolo_id", referencedColumnName = "id", nullable = true)
	private List<Ticket> listaTickets;

	@ManyToOne
	@JoinColumn(name = "tratta_id", referencedColumnName = "id", nullable = true)
	private Tratta tratta;

	// Getters & Setters
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public boolean isStatoServizio() {
		return statoServizio;
	}

	public void setStatoServizio(boolean statoServizio) {
		this.statoServizio = statoServizio;
	}

	public LocalDate getDataInizioServizio() {
		return dataInizioServizio;
	}

	public void setDataInizioServizio(LocalDate dataInizioServizio) {
		this.dataInizioServizio = dataInizioServizio;
	}

	public LocalDate getDataFineServizio() {
		return dataFineServizio;
	}

	public void setDataFineServizio(LocalDate dataFineServizio) {
		this.dataFineServizio = dataFineServizio;
	}

	public List<Ticket> getListaTickets() {
		return listaTickets;
	}

	public void setListaTickets(List<Ticket> listaTickets) {
		this.listaTickets = listaTickets;
	}

	public Tratta getTratta() {
		return tratta;
	}

	public void setTratta(Tratta tratta) {
		this.tratta = tratta;
	}

	// Costruttore
	public Veicolo() {

	}

	public Veicolo(boolean statoServizio, Tratta tratta) {
		setStatoServizio(statoServizio);
		if (statoServizio) {
			setDataInizioServizio(LocalDate.now());
		} else {
			setDataInizioServizio(null);
		}
		setTratta(tratta);
	}

}
