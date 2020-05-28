package it.polimi.project14.user;

import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import it.polimi.project14.common.Event;

public class TimedNotification extends Notification {
    private Timer timer;
	
    public class Notify extends TimerTask {
        public void run() {
			Set<Event> eventsToNotify = getForecasts();
			if (eventsToNotify.isEmpty()) {
                sendEventsToNotify();
			}
        }
    }

    Timer timer = new Timer();
    TimerTask task = new Notify();
 
    timer.schedule(task, 0, 10000);
}