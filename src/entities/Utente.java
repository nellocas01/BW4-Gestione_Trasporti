package entities;

import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Utente {

	@Id
	private UUID id;
	private String nome;
	private String cognome;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "tessera_id", referencedColumnName = "id")
	private Tessera tessera;

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public void setTessera(Tessera tessera) {
		this.tessera = tessera;
	}

	public Tessera getTessera() {
		return tessera;
	}

	public Utente() {

	}

	public Utente(UUID id, String nome, String cognome) {
		setId(id);
		setNome(nome);
		setCognome(cognome);
	}

	@Override
	public String toString() {
		return "Utente [id=" + id + ", nome=" + nome + ", cognome=" + cognome + "]";
	}

}
