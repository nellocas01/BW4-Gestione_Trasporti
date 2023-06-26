package app;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dao.PuntoVenditaDAO;
import dao.TesseraDAO;
import dao.TicketDAO;
import dao.TrattaDAO;
import dao.UtenteDAO;
import dao.VeicoloDAO;
import entities.Abbonamento;
import entities.Autobus;
import entities.Biglietto;
import entities.DistributoreAutomatico;
import entities.PuntoVendita;
import entities.RivenditoreAutorizzato;
import entities.StatoPeriodicita;
import entities.Tessera;
import entities.Ticket;
import entities.TipoAttivita;
import entities.Tram;
import entities.Tratta;
import entities.Utente;
import entities.Veicolo;
import util.JpaUtil;

public class Application {

	private static Logger logger = LoggerFactory.getLogger(Application.class);
	private static EntityManagerFactory emf = JpaUtil.getEntityManagerFactory();

	public static void main(String[] args) {

		EntityManager em = emf.createEntityManager();

		boolean dbPopolato = true;

		boolean runApplication = true;

		// *********** CREO DAO ***********

		UtenteDAO ud = new UtenteDAO(em);
		TesseraDAO td = new TesseraDAO(em);
		TicketDAO tkd = new TicketDAO(em);
		PuntoVenditaDAO pvd = new PuntoVenditaDAO(em);
		VeicoloDAO vd = new VeicoloDAO(em);
		TrattaDAO trd = new TrattaDAO(em);

		if (!dbPopolato) {
			popolaDb(ud, td, tkd, pvd, vd, trd);
		}

		Scanner scanner = new Scanner(System.in);

		while (runApplication) {

			logger.info("********* GESTIONE TRASPORTI *********");
			logger.info("********* SCEGLI UNA TRA LE SEGUENTI OPZIONI: *********");
			logger.info("********* 1 - STAMPA IL NUMERO DI TICKETS VENDUTI DA UN PUNTO VENDITA *********");
			logger.info("********* 2 - VERIFICA LA VALIDITA' DELL'ABBONAMENTO DI UN UTENTE *********");
			logger.info("********* 3 - STAMPA IL NUMERO DI BIGLIETTI VALIDATI PER UN VEICOLO *********");
			logger.info("********* 4 - STAMPA IL NUMERO TOTALE DI BIGLIETTI VALIDATI *********");
			logger.info("********* 5 - STAMPA IL NUMERO VIAGGI PER UN VEICOLO E PER UNA TRATTA *********");
			logger.info("********* 6 - STAMPA IL TEMPO EFFETTIVO PER UNA TRATTA *********");
			logger.info("********* 7 - CAMBIA STATO ATTIVITA' DISTRIBUTORE *********");
			logger.info("********* 8 - CAMBIA STATO ATTIVITA' VEICOLO *********");
			logger.info("********* 9 - AGGIUNGI UTENTE *********");
			logger.info("********* 0 - TERMINA L'APPLICAZIONE *********");
			logger.info("********* QUALE OPERAZIONE INTENDI ESEGUIRE? *********");

			int input = scanner.nextInt();

			switch (input) {
			case 1:
				selezionaPuntoVenditaTicketVenduti(scanner, pvd, tkd);
				break;
			case 2:
				validitaAbbonamento(scanner, tkd, td);
				break;
			case 3:
				bigliettiValidatiVeicolo(scanner, vd, tkd);
				break;
			case 4:
				stampaNumeroTotaleBigliettiTimbrati(tkd);
				break;
			case 5:
				numeroViaggiVeicoloTratta(scanner, trd, vd);
				break;
			case 6:
				tempoEffettivo(scanner, trd);
				break;
			case 7:
				cambiaStatoAttivitaDistributore(scanner, pvd);
				break;
			case 8:
				cambiaStatoAttivitaVeicolo(scanner, vd);
				break;
			case 9:
				aggiungiUtente(scanner, ud, td);
				break;
			case 0:
				runApplication = false;
				logger.info("Termino l'applicazione.");
				break;
			default:
				logger.info("Inserisci un input valido!");
				break;
			}
		}

		scanner.close();

		em.close();
		emf.close();
	}

	private static void selezionaPuntoVenditaTicketVenduti(Scanner scanner, PuntoVenditaDAO pvd, TicketDAO tkd) {
		stampaPuntiVendita(pvd);
		int inputPv;

		do {
			logger.info("********* INSERISCI L'ID DEL PUNTO VENDITA: *********");
			inputPv = scanner.nextInt();
			if (inputPv == 0 || inputPv < 0) {
				logger.error("INPUT ERRATO!");
				logger.error("INSERISCI UN INPUT VALIDO");
			}
		} while (inputPv == 0 || inputPv < 0);

		Long idPuntoVendita = (long) inputPv;
		stampaNumeroTicketsVenduti(pvd, tkd, idPuntoVendita);

	}

	private static void stampaPuntiVendita(PuntoVenditaDAO pvd) {
		List<PuntoVendita> listaPuntiVendita = pvd.selectPuntiVendita();
		logger.info("********* LISTA COMPLETA PUNTI VENDITA: *********");
		for (PuntoVendita p : listaPuntiVendita) {
			logger.info(p.toString());
		}
	}

	private static void stampaNumeroTicketsVenduti(PuntoVenditaDAO pvd, TicketDAO tkd, Long id) {

		PuntoVendita p = pvd.selectPuntoVenditaPerId(id);
		logger.info("*********************************");
		logger.info("TICKETS VENDUTI DAL PUNTO VENDITA CON ID : " + p.getId());
		logger.info("" + tkd.selectAllTickets(LocalDate.of(2023, 1, 1), LocalDate.now(), p.getId()));

	}

	private static void validitaAbbonamento(Scanner scanner, TicketDAO tkd, TesseraDAO td) {
		// scanner.next();
		stampaAbbonamenti(tkd);
		String inputAbbonamento;
		do {
			logger.info("********* INSERISCI L'ID DELL' ABBONAMENTO: *********");
			inputAbbonamento = scanner.next();
			if (inputAbbonamento.equals("")) {
				logger.error("DATO OBBLIGATORIO!");
				logger.error("INSERISCI L'ID DELL'ABBONAMENTO!");
			}
		} while (inputAbbonamento.equals(""));
		UUID idAbbonamento = UUID.fromString(inputAbbonamento);
		verificaValiditaAbbonamentoUtente(td, tkd, idAbbonamento);
	}

	private static void stampaAbbonamenti(TicketDAO tkd) {
		List<Ticket> listaAbbonamenti = tkd.selectAbbonamenti();
		logger.info("********* LISTA COMPLETA ABBONAMENTI: *********");
		for (Ticket ticket : listaAbbonamenti) {
			logger.info(ticket.toString());
		}
	}

	private static void verificaValiditaAbbonamentoUtente(TesseraDAO td, TicketDAO tkd, UUID idAbbonamento) {
		LocalDate dataScadenzaAbbonamento;
		Ticket t = tkd.selectAbbonamentiById(idAbbonamento);
		Abbonamento a = null;
		if (t != null && t instanceof Abbonamento) {
			a = (Abbonamento) t;
		}
		if (a.getPeriodicita().toString().equals("SETTIMANALE")) {
			dataScadenzaAbbonamento = a.getDataEmissione().plusDays(7);
		} else {
			dataScadenzaAbbonamento = a.getDataEmissione().plusMonths(1);

		}
		Utente u = td.selectUtenteByIdTessera(a.getTessera().getId());
		logger.info("*********************************");
		logger.info("L'ABBONAMENTO DI " + u.getNome() + " " + u.getCognome() + " E' "
				+ (td.checkValiditaAbbonamento(u.getTessera().getId(), dataScadenzaAbbonamento) ? "SCADUTO"
						: "VALIDO"));
		logger.info("DATA SCADENZA:" + dataScadenzaAbbonamento);
		logger.info("*********************************");
	}

	private static void bigliettiValidatiVeicolo(Scanner scanner, VeicoloDAO vd, TicketDAO tkd) {
		stampaVeicoli(vd);
		String inputVeicolo;
		do {
			logger.info("********* INSERISCI L'ID DEL VEICOLO: *********");
			inputVeicolo = scanner.next();
			if (inputVeicolo.equals("")) {
				logger.error("DATO OBBLIGATORIO!");
				logger.error("INSERISCI L'ID DEL VEICOLO!");
			}
		} while (inputVeicolo.equals(""));

		UUID idVeicolo = UUID.fromString(inputVeicolo);
		stampaNumeroBigliettiTimbratiVeicolo(tkd, idVeicolo);
	}

	private static void stampaVeicoli(VeicoloDAO vd) {
		List<Veicolo> listaVeicoli = vd.selectVeicoli();
		logger.info("********* LISTA COMPLETA VEICOLI: *********");
		for (Veicolo v : listaVeicoli) {
			logger.info(v.toString());
		}
	}

	private static void stampaNumeroBigliettiTimbratiVeicolo(TicketDAO tkd, UUID idVeicolo) {
		logger.info("*********************************");
		logger.info("BIGLIETTI VIDIMATI PER IL VEICOLO CON ID " + idVeicolo + ": "
				+ tkd.selectAllTicketsByIdVeicolo(idVeicolo));
		logger.info("*********************************");
	}

	private static void stampaNumeroTotaleBigliettiTimbrati(TicketDAO tkd) {
		logger.info("*********************************");
		logger.info("BIGLIETTI VIDIMATI IN TOTALE NEL RANGE DI DATE: "
				+ tkd.selectAllTicketsValidati(LocalDate.of(2023, 1, 1)));
		logger.info("*********************************");
	}

	private static void numeroViaggiVeicoloTratta(Scanner scanner, TrattaDAO trd, VeicoloDAO vd) {
		stampaTratte(trd);
		int idTratta;
		String inputVeicoloTratta;

		do {
			logger.info("********* INSERISCI L'ID DELLA TRATTA: *********");
			idTratta = scanner.nextInt();
			if (idTratta == 0 || idTratta < 0) {
				logger.error("INPUT ERRATO!");
				logger.error("INSERISCI UN INPUT VALIDO");
			}
		} while (idTratta == 0 || idTratta < 0);

		do {
			logger.info("********* INSERISCI L'ID DEL VEICOLO: *********");
			inputVeicoloTratta = scanner.next();
			if (inputVeicoloTratta.equals("")) {
				logger.error("DATO OBBLIGATORIO!");
				logger.error("INSERISCI L'ID VEICOLO!");
			}
		} while (inputVeicoloTratta.equals(""));

		UUID idVeicoloTratta = UUID.fromString(inputVeicoloTratta);
		stampaNumeroViaggiPerVeicoloTratta(vd, idVeicoloTratta, idTratta);
	}

	private static void stampaTratte(TrattaDAO trd) {
		List<Tratta> listaTratte = trd.selectTratte();
		logger.info("********* LISTA COMPLETA TRATTE: *********");
		for (Tratta t : listaTratte) {
			logger.info(t.toString());
		}
	}

	private static void stampaNumeroViaggiPerVeicoloTratta(VeicoloDAO vd, UUID idVeicolo, int idTratta) {
		logger.info("*********************************");
		logger.info(
				"NUMERO VIAGGI PER VEICOLO CON ID " + idVeicolo + ": " + vd.selectNumeroViaggi(idVeicolo, idTratta));
		logger.info("*********************************");
	}

	private static void tempoEffettivo(Scanner scanner, TrattaDAO trd) {
		stampaTratte(trd);
		int idTratta;
		do {
			logger.info("********* INSERISCI L'ID DELLA TRATTA: *********");
			idTratta = scanner.nextInt();
			if (idTratta == 0 || idTratta < 0) {
				logger.error("INPUT ERRATO!");
				logger.error("INSERISCI UN INPUT VALIDO");
			}
		} while (idTratta == 0 || idTratta < 0);
		stampaTempoEffettivoPerTratta(trd, idTratta);

	}

	private static void stampaTempoEffettivoPerTratta(TrattaDAO trd, int idTratta) {
		logger.info("*********************************");
		logger.info("TEMPO EFFETTIVO TRATTA CON ID " + idTratta + ": " + trd.selectTempoEffettivoPerTratta(idTratta));
		logger.info("*********************************");
	}

	private static void cambiaStatoAttivitaDistributore(Scanner scanner, PuntoVenditaDAO pvd) {
		stampaDistributori(pvd);
		int idDistributore;
		do {
			logger.info("********* INSERISCI L'ID DEL DISTRIBUTORE: *********");
			idDistributore = scanner.nextInt();
			if (idDistributore == 0 || idDistributore < 0) {
				logger.error("INPUT ERRATO!");
				logger.error("INSERISCI UN INPUT VALIDO");
			}
		} while (idDistributore == 0 || idDistributore < 0);
		Long id = (long) idDistributore;
		DistributoreAutomatico d = pvd.selectDistributore(id);
		if (d != null) {
			logger.info("STATO ATTUALE DISTRIBUTORE: " + (d.isAttivo() ? "ATTIVO" : "FUORI SERVIZIO"));
			pvd.updateStato(id);
			logger.info("STATO DISTRIBUTORE AGGIORNATO: " + (d.isAttivo() ? "ATTIVO" : "FUORI SERVIZIO"));
		}
	}

	private static void stampaDistributori(PuntoVenditaDAO pvd) {
		List<DistributoreAutomatico> listaDistributori = pvd.selectAllDistributori();
		logger.info("********* LISTA COMPLETA DISTRIBUTORI: *********");
		for (DistributoreAutomatico d : listaDistributori) {
			logger.info(d.toString());
		}
	}

	private static void cambiaStatoAttivitaVeicolo(Scanner scanner, VeicoloDAO vd) {
		stampaVeicoli(vd);
		String inputVeicolo;
		do {
			logger.info("********* INSERISCI L'ID DEL VEICOLO: *********");
			inputVeicolo = scanner.next();
			if (inputVeicolo.equals("")) {
				logger.error("DATO OBBLIGATORIO!");
				logger.error("INSERISCI L'ID DEL VEICOLO!");
			}
		} while (inputVeicolo.equals(""));

		UUID idVeicolo = UUID.fromString(inputVeicolo);
		Veicolo v = vd.selectVeicoloById(idVeicolo);
		if (v != null) {
			logger.info("STATO ATTUALE VEICOLO: " + (v.isStatoServizio() ? "IN SERVIZIO" : "IN MANUTENZIONE")
					+ ". DATA " + (v.isStatoServizio() ? "INIZIO" : "FINE") + " SERVIZIO: "
					+ (v.isStatoServizio() ? v.getDataInizioServizio() : v.getDataFineServizio()));
			vd.updateStato(idVeicolo);
			logger.info("STATO AGGIORNATO VEICOLO: " + (v.isStatoServizio() ? "IN SERVIZIO" : "IN MANUTENZIONE")
					+ ". DATA " + (v.isStatoServizio() ? "INIZIO" : "FINE") + " SERVIZIO: "
					+ (v.isStatoServizio() ? v.getDataInizioServizio() : v.getDataFineServizio()));
		}

	}

	private static void aggiungiUtente(Scanner scanner, UtenteDAO ud, TesseraDAO td) {
		String nomeUtente;
		String cognomeUtente;
		do {
			logger.info("********* INSERISCI IL NOME DELL'UTENTE: *********");
			nomeUtente = scanner.next();
			if (nomeUtente.equals("")) {
				logger.error("DATO OBBLIGATORIO!");
				logger.error("INSERISCI IL NOME!");
			}
		} while (nomeUtente.equals(""));
		do {
			logger.info("********* INSERISCI IL COGNOME DELL'UTENTE: *********");
			cognomeUtente = scanner.next();
			if (cognomeUtente.equals("")) {
				logger.error("DATO OBBLIGATORIO!");
				logger.error("INSERISCI IL COGNOME!");
			}
		} while (cognomeUtente.equals(""));
		Utente u = new Utente(UUID.randomUUID(), nomeUtente, cognomeUtente);
		Tessera tesseraU1 = new Tessera(UUID.randomUUID(), LocalDate.now(), LocalDate.now().plusYears(1), u);
		td.save(tesseraU1);
		u.setTessera(tesseraU1);
		ud.save(u);
		logger.info("UTENTE SALVATO!");
	}

	private static void popolaDb(UtenteDAO ud, TesseraDAO td, TicketDAO tkd, PuntoVenditaDAO pvd, VeicoloDAO vd,
			TrattaDAO trd) {
		// *********** CREO UTENTI ***********

		Utente utente1 = new Utente(UUID.randomUUID(), "Mario", "Rossi");
		Utente utente2 = new Utente(UUID.randomUUID(), "Giovanni", "Storti");
		Utente utente3 = new Utente(UUID.randomUUID(), "Roberto", "Baggio");
		Utente utente4 = new Utente(UUID.randomUUID(), "Piero", "Pelù");
		Utente utente5 = new Utente(UUID.randomUUID(), "Aldo", "Baio");

		// *********** CREO E SALVO TESSERE ***********

		Tessera tesseraU1 = new Tessera(UUID.randomUUID(), LocalDate.now(), LocalDate.now().plusYears(1), utente1);
		Tessera tesseraU2 = new Tessera(UUID.randomUUID(), LocalDate.now(), LocalDate.now().plusYears(1), utente2);
		Tessera tesseraU3 = new Tessera(UUID.randomUUID(), LocalDate.now(), LocalDate.now().plusYears(1), utente3);
		Tessera tesseraU4 = new Tessera(UUID.randomUUID(), LocalDate.now(), LocalDate.now().plusYears(1), utente4);
		Tessera tesseraU5 = new Tessera(UUID.randomUUID(), LocalDate.now(), LocalDate.now().plusYears(1), utente5);

		ud.save(utente1);
		ud.save(utente2);
		ud.save(utente3);
		ud.save(utente4);
		ud.save(utente5);

		utente1.setTessera(tesseraU1);
		utente2.setTessera(tesseraU2);
		utente3.setTessera(tesseraU3);
		utente4.setTessera(tesseraU4);
		utente5.setTessera(tesseraU5);

		td.save(tesseraU1);
		td.save(tesseraU2);
		td.save(tesseraU3);
		td.save(tesseraU4);
		td.save(tesseraU5);

		// *********** CREO E SALVO ABBONAMENTI E BIGLIETTI ***********

		Abbonamento abbonamentoU1 = new Abbonamento(UUID.randomUUID(), LocalDate.now().minusDays(14),
				StatoPeriodicita.SETTIMANALE);
		Biglietto bigliettoU2 = new Biglietto(UUID.randomUUID(), LocalDate.now().minusDays(35));
		Biglietto bigliettoU3 = new Biglietto(UUID.randomUUID(), LocalDate.now().minusDays(60));
		Biglietto bigliettoU4 = new Biglietto(UUID.randomUUID(), LocalDate.now().minusDays(29));
		Biglietto bigliettoU5 = new Biglietto(UUID.randomUUID(), LocalDate.now().minusDays(13));

		tkd.save(abbonamentoU1);
		tkd.save(bigliettoU2);
		tkd.save(bigliettoU3);
		tkd.save(bigliettoU4);
		tkd.save(bigliettoU5);

		// *********** CREO TRATTA ***********
		Tratta tratta1 = new Tratta("Milano San Siro", "Milano Centrale", LocalTime.of(13, 00), LocalTime.of(1, 15), 5);
		Tratta tratta2 = new Tratta("Roma", "Viterbo", LocalTime.of(8, 00), LocalTime.of(1, 45), 3);

		// *********** CREO VEICOLI ***********

		Tram tram1 = new Tram(true, tratta1);
		Autobus autobus1 = new Autobus(true, tratta2);

		// *********** CREO DISTRIBUTORI E RIVENDITORI ***********

		DistributoreAutomatico da1 = new DistributoreAutomatico("Milano", "Via Torino", true);
		RivenditoreAutorizzato rv1 = new RivenditoreAutorizzato("Roma", "Via Nomentana", "Da Pippo",
				TipoAttivita.TABACCHI);

		// *********** SETTO GLI ATTRIBUTI ***********
		abbonamentoU1.setPuntoVendita(da1);
		bigliettoU2.setPuntoVendita(da1);
		bigliettoU3.setPuntoVendita(rv1);
		bigliettoU4.setPuntoVendita(rv1);
		bigliettoU5.setPuntoVendita(da1);

		abbonamentoU1.setTessera(tesseraU1);
		bigliettoU2.setTessera(tesseraU2);
		bigliettoU3.setTessera(tesseraU3);
		bigliettoU4.setTessera(tesseraU4);
		bigliettoU5.setTessera(tesseraU5);

		List<Ticket> listaTicketU1 = new ArrayList<>();
		listaTicketU1.add(abbonamentoU1);
		tesseraU1.setTicket(listaTicketU1);

		List<Ticket> listaTicketU2 = new ArrayList<>();
		listaTicketU2.add(bigliettoU2);
		tesseraU2.setTicket(listaTicketU2);

		List<Ticket> listaTicketU3 = new ArrayList<>();
		listaTicketU3.add(bigliettoU3);
		tesseraU3.setTicket(listaTicketU3);

		List<Ticket> listaTicketU4 = new ArrayList<>();
		listaTicketU4.add(bigliettoU4);
		tesseraU4.setTicket(listaTicketU4);

		List<Ticket> listaTicketU5 = new ArrayList<>();
		listaTicketU5.add(bigliettoU5);
		tesseraU5.setTicket(listaTicketU5);

		List<Abbonamento> listaAbbonamentiDistributore1 = new ArrayList<>();
		listaAbbonamentiDistributore1.add(abbonamentoU1);

		List<Biglietto> listaBigliettiDistributore1 = new ArrayList<>();
		listaBigliettiDistributore1.add(bigliettoU2);
		listaBigliettiDistributore1.add(bigliettoU5);

		List<Biglietto> listaBigliettiRivenditore1 = new ArrayList<>();
		listaBigliettiRivenditore1.add(bigliettoU3);
		listaBigliettiRivenditore1.add(bigliettoU4);

		List<Veicolo> listaVeicoliTratta1 = new ArrayList<>();
		listaVeicoliTratta1.add(autobus1);

		List<Veicolo> listaVeicoliTratta2 = new ArrayList<>();
		listaVeicoliTratta2.add(tram1);

		// *********** LISTA TICKET TRAM1 ***********
		List<Ticket> listaBigliettiTram1 = new ArrayList<>();
		listaBigliettiTram1.add(bigliettoU2);
		listaBigliettiTram1.add(bigliettoU3);

		tram1.setListaTickets(listaBigliettiTram1);

		// *********** BIGLIETTI TIMBRATI ***********
		bigliettoU2.setTimbrato(true);
		bigliettoU3.setTimbrato(true);

		if (tram1.getCapienzaTram() == listaBigliettiTram1.size()) {
			logger.info("Capienza massima raggiunta!");
		}

		List<Ticket> listaBigliettiAutobus1 = new ArrayList<>();
		listaBigliettiAutobus1.add(abbonamentoU1);
		listaBigliettiAutobus1.add(bigliettoU4);
		listaBigliettiAutobus1.add(bigliettoU5);

		autobus1.setListaTickets(listaBigliettiAutobus1);

		// BIGLIETTI TIMBRATI
		bigliettoU4.setTimbrato(true);
		bigliettoU5.setTimbrato(true);

		if (autobus1.getCapienzaAutobus() == listaBigliettiAutobus1.size()) {
			logger.info("Capienza massima raggiunta!");
		}

		da1.setListaAbbonamentiVenduti(listaAbbonamentiDistributore1);
		da1.setListaBigliettiVenduti(listaBigliettiDistributore1);
		rv1.setListaBigliettiVenduti(listaBigliettiRivenditore1);

		// *********** SET ORARIO FINE TRATTA E TEMPO EFFETTIVO ***********
		tratta1.setOraFineTratta(LocalTime.of(14, 25));
		tratta2.setOraFineTratta(LocalTime.of(9, 55));

		// *********** SALVO SUL DATABASE ***********
		pvd.save(da1);
		pvd.save(rv1);

		trd.save(tratta1);
		trd.save(tratta2);

		vd.save(tram1);
		vd.save(autobus1);

	}

}
