package it.polimi.project14.source;

import java.util.Set;
import java.util.Timer;

import it.polimi.project14.common.Event;

public class Source {

	private int id;
	private Timer timer;

	public Source(int id, Timer timer) {
		this.id = id;
		this.timer = timer;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}

	public void sendForecast(Set<Event> eventList){
		// return EventList;
	}
}