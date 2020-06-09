package it.polimi.project14.source;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;

import it.polimi.project14.common.Event;
import it.polimi.project14.common.EventStatus;

public class TimedForecast implements Forecast {

    private static final Semaphore mutex = new Semaphore(1);

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
		timer.schedule(timerTask, 0, SLEEP);
	}

	@Override
	public Set<Event> getForecasts() {
        try {
            mutex.acquire();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        Set<Event> forecast24HCopy = new HashSet<>(forecast24H);
        mutex.release();
        return forecast24HCopy;
	}

	@Override
	public void removeForecasts(Set<Event> events) {
        try {
            mutex.acquire();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
		forecast24H.removeAll(events);
        mutex.release();
	}

	private void generateForecast() {
		Set<Event> forecastGenerated = new HashSet<>();

		// Every time that genereteForecast is called
		// there is 50% of probability to generate 1 Event. 
		if (Math.random() >= 0.5) {
			Event generated = RandomEvent.generate();
			forecastGenerated.add(generated);
		}

		try {
            mutex.acquire();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }

		forecast24H.addAll(forecastGenerated);

		LocalDateTime now = LocalDateTime.now();
		for (Event event : forecast24H) {
			if (event.getExpectedAt().isBefore(now) && event.getStatus() == EventStatus.EXPECTED) {
				event.setStatus(EventStatus.CANCELED);
			}
			if (event.getExpectedAt().isBefore(now.plusMinutes(EVENT_DURATION)) && event.getStatus() == EventStatus.ONGOING) {
				event.setStatus(EventStatus.OCCURED);
			}
		}

        mutex.release();
	}

	public class taskGenerateForecast extends TimerTask {
		public void run() {
			generateForecast();
		}
	}

}
