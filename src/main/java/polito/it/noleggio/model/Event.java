package polito.it.noleggio.model;

import java.time.LocalTime;

public class Event implements Comparable<Event> {

	// sempre presenti tempo e tipo di evento

	// classe enumerazione per definire una serie di costanti
	public enum EventType {

		// il compilatore java indicherà con 0 il primo e con 1 il secondo
		NEW_CLIENT, CAR_RETURNED
	}

	// sempre presenti tempo e tipo di evento
	private LocalTime time;
	private EventType type;

	public Event(LocalTime time, EventType type) {

		this.time = time;
		this.type = type;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public EventType getType() {
		return type;
	}

	public void setType(EventType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Event [time=" + time + ", type=" + type + "]";
	}

	//genera ordinamento della coda
    //delega all'attributo time
	@Override
	public int compareTo(Event other) {
		// TODO Auto-generated method stub
		return time.compareTo(other.time);
	}

}
