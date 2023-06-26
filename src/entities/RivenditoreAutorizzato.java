package entities;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "rivenditori_autorizzati")
public class RivenditoreAutorizzato extends PuntoVendita {
	private String nomeAttivita;
	private TipoAttivita tipoAttivita;

	public String getNomeAttivita() {
		return nomeAttivita;
	}

	public void setNomeAttivita(String nomeAttivita) {
		this.nomeAttivita = nomeAttivita;
	}

	public TipoAttivita getTipoAttivita() {
		return tipoAttivita;
	}

	public void setTipoAttivita(TipoAttivita tipoAttivita) {
		this.tipoAttivita = tipoAttivita;
	}

	// Constructor
	public RivenditoreAutorizzato() {

	}

	public RivenditoreAutorizzato(String citta, String indirizzo, String nomeAttivita, TipoAttivita tipoAttivita) {
		super(citta, indirizzo);
		setNomeAttivita(nomeAttivita);
		setTipoAttivita(tipoAttivita);
	}

	@Override
	public String toString() {
		return "RivenditoreAutorizzato [ id=" + getId() + ",citta=" + getCitta() + ",indirizzo=" + getIndirizzo()
				+ "nomeAttivita=" + nomeAttivita + ",tipoAttivita=" + tipoAttivita + "]";
	}
}
