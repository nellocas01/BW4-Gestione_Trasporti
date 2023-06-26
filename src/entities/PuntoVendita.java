package entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQuery(name = "selectPuntoVenditaPerId", query = "SELECT p FROM PuntoVendita p WHERE p.id = :idPuntoVendita")
@NamedQuery(name = "selectPuntiVendita", query = "SELECT p FROM PuntoVendita p")
public abstract class PuntoVendita {
	@Id
	@SequenceGenerator(name = "puntovendita_seq", sequenceName = "puntovendita_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "puntovendita_seq")
	private Long id;
	private String citta;
	private String indirizzo;
	@OneToMany
	@JoinColumn(name = "puntoVenditaBiglietto_id", referencedColumnName = "id", nullable = true)
	private List<Biglietto> listaBigliettiVenduti;
	@OneToMany
	@JoinColumn(name = "puntoVenditaAbbonamento_id", referencedColumnName = "id", nullable = true)
	private List<Abbonamento> listaAbbonamentiVenduti;

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCitta() {
		return citta;
	}

	public void setCitta(String citta) {
		this.citta = citta;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public List<Biglietto> getListaBigliettiVenduti() {
		return listaBigliettiVenduti;
	}

	public void setListaBigliettiVenduti(List<Biglietto> listaBigliettiVenduti) {
		this.listaBigliettiVenduti = listaBigliettiVenduti;
	}

	public List<Abbonamento> getListaAbbonamentiVenduti() {
		return listaAbbonamentiVenduti;
	}

	public void setListaAbbonamentiVenduti(List<Abbonamento> listaAbbonamentiVenduti) {
		this.listaAbbonamentiVenduti = listaAbbonamentiVenduti;
	}

	public PuntoVendita() {

	}

	public PuntoVendita(String citta, String indirizzo) {
		setCitta(citta);
		setIndirizzo(indirizzo);
	}

}
