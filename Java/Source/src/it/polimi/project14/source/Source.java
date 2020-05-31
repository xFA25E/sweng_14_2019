package it.polimi.project14.source;

import java.rmi.Naming;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

import it.polimi.project14.common.Event;
import it.polimi.project14.common.EventStatus;
import it.polimi.project14.common.EventStorage;

public class Source {

	// milliseconds
	private static final long URGENT_SLEEP = 5 * 1000;
	private static final long NORMAL_SLEEP = 4 * 60 * 60 * 1000;

	private int id = (int) Math.random() * Integer.MAX_VALUE;
	private Forecast forecast;
	private Timer timer;
	private TimerTask timerTask4H = new taskGetForecast();
	private TimerTask timerTask5S = new taskSendImmutable();

	final private String urlServer = "rmi://localhost:6666/";
	static {
		System.setProperty("java.security.policy", "policy.all");
	}

	public Source(Forecast forecast){
		this(NORMAL_SLEEP, URGENT_SLEEP, forecast);
	}

	public Source(long normalSleep, long urgentSleep, Forecast forecast) {
		timer = new Timer();
		timer.schedule(timerTask4H, normalSleep);
		timer.schedule(timerTask5S, urgentSleep);
	}

	public class taskGetForecast extends TimerTask {
		public void run() {
			try {
				sendForecasts(forecast.getForecasts());
			} catch (Exception e) {
				//TODO: handle exception
			}
		}
	}

	public class taskSendImmutable extends TimerTask {
		public void run() {
			try {
				Set<Event> forecasts = forecast.getForecasts();
				sendForecasts(forecasts.stream()
							  .filter(e -> shouldSend(e))
							  .collect(Collectors.toSet()));
				forecast.removeForecasts(forecasts.stream()
										 .filter(e -> shouldRemove(e))
										 .collect(Collectors.toSet()));		
			} catch (Exception e) {
				//TODO: handle exception
			}
		}
	}

	public void sendForecasts(Set<Event> eventList) throws Exception {
		//RMI
		EventStorage remoteEventStorage = (EventStorage) Naming.lookup(urlServer + "EVENT_STORAGE");
		remoteEventStorage.storeEvents(eventList);
		// return EventList;
	}

	public static boolean shouldSend(Event event) {
		switch (event.getStatus()) {
			case EXPECTED:
				return false;
			default:
				return true;
		}
	}

	public static boolean shouldRemove(Event event){
		EventStatus status = event.getStatus();
		if (status == EventStatus.CANCELED || status == EventStatus.OCCURED) {
			return true;
		} else {
			return false;
		}
	}
}