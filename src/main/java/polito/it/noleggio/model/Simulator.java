package polito.it.noleggio.model;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;

import polito.it.noleggio.model.Event.EventType;

public class Simulator {

	// avrà come struttura principale una coda prioritaria fatta di eventi

	// CODA DEGLI EVENTI
	private PriorityQueue<Event> queue = new PriorityQueue<>();

	// PARAMETRI DI SIMULAZIONE
	private int NC = 10; // numero di macchine
	private Duration T_IN = Duration.of(10, ChronoUnit.MINUTES); // intervallo tra i clienti
	// do sempre dei valori di default per nonn avere errori

	private final LocalTime oraApertura = LocalTime.of(8, 00);
	private final LocalTime oraChiusura = LocalTime.of(17, 00);

	// devono essere impostati dall'esterno quindi devono avere dei setter

	// MODELLO DEL MONDO
	// sarà il numero di auto ancora disponibili
	private int nAuto; // compreso tra 0 e NC

	// VALORI IN USCITA DA CALCOLARE
	// da calcolare num clienti arrivati e insoddisfatti
	private int clienti;
	private int insoddisfatti;

	// METODI PER IMPOSTARE I PARAMETRI

	public int getClienti() {
		return clienti;
	}

	public int getInsoddisfatti() {
		return insoddisfatti;
	}

	public void setNumCars(int nC) {
		NC = nC;
	}

	public void setClientFrequency(Duration t_IN) {
		T_IN = t_IN;
	}

	// SIMULAZIONE VERA E PROPRIA

	public void run() {
		// avvia la simulazione e gestisce la coda degli eventi

		/*
		 * Due parti: 1. preparazione iniziale (impostare variabili mondo e predisporre
		 * la cosa eventi) 2. Esecuzione del ciclo di simulazione
		 * 
		 */

		// parte 1
		this.nAuto = this.NC;
		this.clienti = this.insoddisfatti = 0;

		// per sicurezza svuoto sempre la coda
		this.queue.clear();
		// ipotizzo che arrivi appena apre
		LocalTime oraArrivoCliente = this.oraApertura;

		// aggiungo un nuovo cliente finchè l'ora di arrivo del cliente
		do {
			Event e = new Event(oraArrivoCliente, EventType.NEW_CLIENT);
			this.queue.add(e);
			oraArrivoCliente = oraArrivoCliente.plus(this.T_IN);
		} while (oraArrivoCliente.isBefore(this.oraChiusura));

		// parte 2
		// se la coda non è vuota significa che non ho ancora finito di simulare
		while (!this.queue.isEmpty()) {

			// se ho eventi in coda, estrai il primo, elaboralo e ripeti
			Event e = this.queue.poll(); // estraggo l'evento in ordine di tempo
			// questo perchè l'oggetto evento ha un comparatore sulla base del time

			// in base al tipo di evento che ricevo dovrò fare cose diverse

			System.out.println(e);
			this.processEvent(e);

		}
	}

	private void processEvent(Event e) {
		
		switch (e.getType()) {
		case NEW_CLIENT:
			
			if(this.nAuto>0) {
				//cliente servito, auto noleggiata
				
				//1.aggiorna modello mondo
				this.nAuto--; 
				//2.aggiorna i risultati
				this.clienti++; 
				//3. genera nuovi eventi
				double num = Math.random(); //[0,1)
				Duration travel ; 
				if(num<1.0/3.0)
					travel= Duration.of(1, ChronoUnit.HOURS); 
				else if(num<2.0/3.0)
					travel= Duration.of(2, ChronoUnit.HOURS); 
				else 
					travel= Duration.of(3, ChronoUnit.HOURS);
				//con get time tempo corrente più ritardo calcolato sopra
				Event nuovo = new Event(e.getTime().plus(travel),EventType.CAR_RETURNED);
			
				this.queue.add(nuovo);
			
			}else {
				//cliente insoddisfatto
				this.clienti++; 
				this.insoddisfatti++;
			}
			
			
			
			break;

		case CAR_RETURNED:
			
			this.nAuto++;
				
			break;
		}
	}

	
	
	
	
	
	
	
	
}
