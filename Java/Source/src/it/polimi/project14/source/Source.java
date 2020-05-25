package it.polimi.project14.source;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Set;
import java.util.Timer;

import it.polimi.project14.common.Event;
import it.polimi.project14.common.EventStorage;

public class Source {

	private int id;
	private Timer timer;

	final private String urlServer = "rmi://localhost:6666/";
	static {
		System.setProperty("java.security.policy", "policy.all");
	}

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

	public void sendForecast(Set<Event> eventList) throws Exception {
		//RMI
		try {
			EventStorage remoteEventStorage = (EventStorage) Naming.lookup(urlServer + "EVENT_STORAGE");
			remoteEventStorage.storeEvents(eventList);
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		} catch (RemoteException e) {
			throw new Exception(e.getMessage());
		}
		// return EventList;
	}
}