package it.polimi.project14.source;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import it.polimi.project14.common.Event;
import it.polimi.project14.common.EventStatus;

public class TimedForecast implements Forecast {

	// milliseconds
	private static final long SLEEP = 20 * 1000;
	private static final long EVENT_DURATION = 30; //Minutes

	private Set<Event> forecast24H;
	private Timer timer;
	private TimerTask timerTask;

	public TimedForecast() {
		forecast24H = new HashSet<Event>();
		timer = new Timer();
		timerTask = new taskGenerateForecast();
		timer.schedule(timerTask, SLEEP);
	}

	/*
	 * public TimedForecast(ArrayList<Event> forecast24H, Timer timer){
	 * this.forecast24H = forecast24H; this.timer = timer; }
	 */

	@Override
	public Set<Event> getForecasts() {
		return forecast24H;
	}

	@Override
	public void removeForecasts(Set<Event> events) {
		forecast24H.removeAll(events);
	}

	private void generateForecast() {
		Set<Event> forecastGenerated = new HashSet<>();
		for (int i = 0; i < 10; i++) {
			Event generated = RandomEvent.generate();
			forecastGenerated.add(generated);
		}
		forecast24H.addAll(forecastGenerated);
		LocalDateTime now = LocalDateTime.now();
		for (Event event:forecast24H) {
			if (event.getExpectedAt().isBefore(now) && event.getStatus() == EventStatus.EXPECTED) {
				event.setStatus(EventStatus.CANCELED);
			}
			if (event.getExpectedAt().isBefore(now.plusMinutes(EVENT_DURATION)) && event.getStatus() == EventStatus.ONGOING) {
				event.setStatus(EventStatus.OCCURED);
			}
		}
	}

	public class taskGenerateForecast extends TimerTask {
		public void run() {
			generateForecast();
		}
	}

}
