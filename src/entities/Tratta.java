package entities;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQuery(name = "selectTempoEffettivoPerTratta", query = "SELECT t FROM Tratta t WHERE t.id = :idTratta")
@NamedQuery(name = "selectTratte", query = "SELECT t FROM Tratta t")
public class Tratta {

	// Attributi
	@Id
	@GeneratedValue
	private int id;

	private String posizionePartenzaTratta;
	private String posizioneArrivoTratta;
	private LocalTime oraInizioTratta;
	private LocalTime oraFineTratta;
	private LocalTime tempoMedio;
	private LocalTime tempoEffettivo;
	private int numeroViaggi;

	@OneToMany
	@JoinColumn(name = "tratta_id", referencedColumnName = "id", nullable = true)
	private List<Veicolo> listaVeicoli;

	// Getters & Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPosizionePartenzaTratta() {
		return posizionePartenzaTratta;
	}

	public void setPosizionePartenzaTratta(String posizionePartenzaTratta) {
		this.posizionePartenzaTratta = posizionePartenzaTratta;
	}

	public String getPosizioneArrivoTratta() {
		return posizioneArrivoTratta;
	}

	public void setPosizioneArrivoTratta(String posizioneArrivoTratta) {
		this.posizioneArrivoTratta = posizioneArrivoTratta;
	}

	public LocalTime getOraInizioTratta() {
		return oraInizioTratta;
	}

	public void setOraInizioTratta(LocalTime oraInizioTratta) {
		this.oraInizioTratta = oraInizioTratta;
	}

	public LocalTime getOraFineTratta() {
		return oraFineTratta;
	}

	public void setOraFineTratta(LocalTime oraFineTratta) {
		this.oraFineTratta = oraFineTratta;

		// questa classe (Duration) permette di eseguire delle operazioni sulle date
		// impostiamo automaticamente il tempo effettivo data l'ora di arrivo
		Duration duration = Duration.between(this.oraInizioTratta, oraFineTratta);
		int hours = (int) duration.toHours();
		int minutes = (int) duration.toMinutesPart();
		LocalTime tempoEffettivo = LocalTime.of(hours, minutes);

		setTempoEffettivo(tempoEffettivo);
	}

	public LocalTime getTempoMedio() {
		return tempoMedio;
	}

	public void setTempoMedio(LocalTime tempoMedio) {
		this.tempoMedio = tempoMedio;
	}

	public LocalTime getTempoEffettivo() {
		return tempoEffettivo;
	}

	public void setTempoEffettivo(LocalTime tempoEffettivo) {
		this.tempoEffettivo = tempoEffettivo;
	}

	public int getNumeroViaggi() {
		return numeroViaggi;
	}

	public void setNumeroViaggi(int numeroViaggi) {
		this.numeroViaggi = numeroViaggi;
	}

	public List<Veicolo> getListaVeicoli() {
		return listaVeicoli;
	}

	public void setListaVeicoli(List<Veicolo> listaVeicoli) {
		this.listaVeicoli = listaVeicoli;
	}

	// Costruttore
	public Tratta() {

	}

	public Tratta(String posizionePartenzaTratta, String posizioneArrivoTratta, LocalTime oraInizioTratta,
			LocalTime tempoMedio, int numeroViaggi) {
		setPosizionePartenzaTratta(posizionePartenzaTratta);
		setPosizioneArrivoTratta(posizioneArrivoTratta);
		setOraInizioTratta(oraInizioTratta);
		setTempoMedio(tempoMedio);
		setNumeroViaggi(numeroViaggi);
	}

	@Override
	public String toString() {
		return "Tratta [id=" + id + ", posizionePartenzaTratta=" + posizionePartenzaTratta + ", posizioneArrivoTratta="
				+ posizioneArrivoTratta + ", oraInizioTratta=" + oraInizioTratta + ", oraFineTratta=" + oraFineTratta
				+ ", tempoMedio=" + tempoMedio + ", tempoEffettivo=" + tempoEffettivo + ", numeroViaggi=" + numeroViaggi
				+ ", listaVeicoli=" + listaVeicoli + "]";
	}

}
