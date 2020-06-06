package it.polimi.project14.user;

import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import it.polimi.project14.common.Event;
import it.polimi.project14.user.gui.EventsNotificationShower;

public class TimedNotification extends Notification {
    private final Timer timer = new Timer();

    public TimedNotification(final User user, final EventsNotificationShower notificationShower) {
        super(user, notificationShower);
        timer.schedule(new Notify(), 0, 10000);
    }

    public class Notify extends TimerTask {
        public void run() {
            try {
                getForecasts().stream().forEach(e -> sendEventToNotify(e));
            } catch (final Exception e) {
                // TODO: handle exception
            }
        }
    }
}
