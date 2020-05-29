package it.polimi.project14.user;

import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import it.polimi.project14.common.Event;

public class TimedNotification extends Notification {
    private Timer timer;

    TimedNotification(User user) {
        super(user);
        timer = new Timer();
        TimerTask task = new Notify();
        timer.schedule(task, 0, 10000);
    }
	
    public class Notify extends TimerTask {
        public void run() {
            try {Set<Event> eventsToNotify = getForecasts();
			if (eventsToNotify.isEmpty()) {
                sendEventsToNotify(eventsToNotify);
                } 
            } catch (Exception e) {
                //TODO: handle exception
            }
        }
    }
}