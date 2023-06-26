package entities;

import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.Entity;

@Entity
public class Abbonamento extends Ticket {

	private StatoPeriodicita periodicita;

	// Getters & Setters
	public StatoPeriodicita getPeriodicita() {
		return periodicita;
	}

	public void setPeriodicita(StatoPeriodicita periodicita) {
		this.periodicita = periodicita;
	}

	// Costruttore
	public Abbonamento() {

	}

	public Abbonamento(UUID id, LocalDate dataEmissione, StatoPeriodicita periodicita) {
		super(id, dataEmissione);
		setPeriodicita(periodicita);
	}

	@Override
	public String toString() {
		return "Abbonamento [ id=" + getId() + ",dataEmissione=" + getDataEmissione() + ",puntoVendita=" + getPuntoVendita()
				+ ",tessera=" + getTessera() + ",periodicita=" + periodicita + "]";
	}

}
