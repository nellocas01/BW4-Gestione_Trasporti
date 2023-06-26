package entities;

import javax.persistence.Entity;

@Entity
public class Autobus extends Veicolo {

	// Attributi
	private int capienzaAutobus;

	// Getters & Setters
	public int getCapienzaAutobus() {
		return capienzaAutobus;
	}

	public void setCapienzaAutobus(int capienzaAutobus) {
		this.capienzaAutobus = capienzaAutobus;
	}

	// Costruttore
	public Autobus() {

	}

	public Autobus(boolean statoServizio, Tratta tratta) {
		super(statoServizio, tratta);
		setCapienzaAutobus(3);
	}

	//toString
	@Override
	public String toString() {
		return "Autobus [ID veicolo=" + getId() + ", capienzaAutobus=" + capienzaAutobus + "]";
	}

}
