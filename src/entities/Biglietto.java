package entities;

import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.Entity;

@Entity
public class Biglietto extends Ticket {

	private boolean timbrato;

	// Getters & Setters

	public boolean isTimbrato() {
		return timbrato;
	}

	public void setTimbrato(boolean timbrato) {
		this.timbrato = timbrato;
	}

	// Costruttore
	public Biglietto() {

	}

	public Biglietto(UUID id, LocalDate dataEmissione) {
		super(id, dataEmissione);
		setTimbrato(false);
	}

	@Override
	public String toString() {
		return "Biglietto [ id=" + getId() + ",dataEmissione=" + getDataEmissione() + ",puntoVendita=" + getPuntoVendita()
				+ ",tessera=" + getTessera() + ",timbrato=" + timbrato + "]";
	}

}
