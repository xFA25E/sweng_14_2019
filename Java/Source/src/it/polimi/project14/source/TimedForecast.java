package it.polimi.project14.source;

import java.util.Set;
import java.util.Timer;

import it.polimi.project14.common.Event;

public class TimedForecast implements Forecast {

	private Set<Event> forecast24H;
	private Timer timer;

	// public TimedForecast(){
		// this.forecast24H = new HashSet<Event>();
		// this.timer = timer;
	// }

	/*
	 * public TimedForecast(ArrayList<Event> forecast24H, Timer timer){
	 * this.forecast24H = forecast24H; this.timer = timer; }
	 */

	public Set<Event> getForecasts() {
		return forecast24H;
	}

	public void setForecast24H(Set<Event> forecast24H) {
		this.forecast24H = forecast24H;
	}

	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}

	public Set<Event> generateForecast() {
		return forecast24H;
	}

	private void computeDifferences() {
	}
}