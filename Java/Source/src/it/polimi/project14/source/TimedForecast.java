package it.polimi.project14.source;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import it.polimi.project14.common.Event;

public class TimedForecast implements Forecast {

	// milliseconds
	private static final long SLEEP = 20 * 1000;
	private static final int MAX_SEVERITY = 10;

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

	private Set<Event> generateForecast() {
		Set<Event> forecastGenerated = new HashSet<>();
        Event generated = RandomEvent.generate();
        forecastGenerated.add(generated);
		return forecastGenerated;
	}

	public class taskGenerateForecast extends TimerTask {
		public void run() {
			forecast24H = generateForecast();
		}
	}

	private void computeDifferences() {

	}

}
