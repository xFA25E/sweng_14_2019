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

	public TimedForecast(){
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
		Set<Event> forecastGenerated;
		Event generated;
		generated.setSourceId();
		generated.setEventId((int) Math.random() * Integer.MAX_VALUE);
		ArrayList<String> capList = new ArrayList<String> (Arrays.asList("22031","22032","22070","22040","22024","22040","22040","22070","22010","22060",
		"22033","22030","22021","22010","22070","22070","22020","22028","22020","22070","22040","22010","22034","22070","22060",
		"22071","22030","22061","22063","22035","22070","22010","22070","22060","22010","22060","22030","22070","22070","22030",
		"22070","22010","22023","22020","22072","22012","22070","22010","22010","22041","22100","22010","22010","22060","22010",
		"22020","22013","22014","22010","22036","22030","22020","22020","22070","22060","22073","22010","22010","22070","22010",
		"22015","22011","22070","22044"));
		generated.setCap(capList.get((int) Math.random() * capList.size()));
		ArrayList<String> messageList = new ArrayList<String> (Arrays.asList("Message"));
		generated.setMessage(messageList.get((int) Math.random() * messageList.size()));
		generated.setExpectedAt();
		generated.setSeverity((int) Math.random() * MAX_SEVERITY);
		generated.setStatus();
		ArrayList<String> kindList = new ArrayList<String> (Arrays.asList("Atmosferico"));
		generated.setKind(kindList.get((int) Math.random() * kindList.size()));
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