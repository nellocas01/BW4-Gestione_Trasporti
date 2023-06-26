package entities;

import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "distributori_automatici")
@NamedQuery(name = "selectDistributore", query = "SELECT d FROM DistributoreAutomatico d WHERE d.id = :idDistributore")
@NamedQuery(name = "selectAllDistributori", query = "SELECT d FROM DistributoreAutomatico d")
public class DistributoreAutomatico extends PuntoVendita {
	private boolean attivo;

	public boolean isAttivo() {
		return attivo;
	}

	public void setAttivo(boolean attivo) {
		this.attivo = attivo;
	}

	public DistributoreAutomatico() {
	}

	public DistributoreAutomatico(String citta, String indirizzo, boolean attivo) {
		super(citta, indirizzo);
		this.attivo = attivo;
	}

	@Override
	public String toString() {
		return "DistributoreAutomatico [ id=" + getId() + ",citta=" + getCitta() + ",indirizzo=" + getIndirizzo()
				+ "attivo=" + attivo + "]";
	}

}
