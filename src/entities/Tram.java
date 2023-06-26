package entities;

import javax.persistence.Entity;

@Entity
public class Tram extends Veicolo {

	// Attributi
	private int capienzaTram;

	// Getters & Setters
	public int getCapienzaTram() {
		return capienzaTram;
	}

	public void setCapienzaTram(int capienzaTram) {
		this.capienzaTram = capienzaTram;
	}

	// Costruttore
	public Tram() {

	}

	public Tram(boolean statoServizio, Tratta tratta) {
		super(statoServizio, tratta);
		setCapienzaTram(2);
		;
	}

	//toString
	@Override
	public String toString() {
		return "Tram [ID veicolo=" + getId() + ", capienzaTram=" + capienzaTram + "]";
	}

}
