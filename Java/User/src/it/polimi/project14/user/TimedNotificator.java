package it.polimi.project14.user;

import java.util.Timer;
import java.util.TimerTask;

import it.polimi.project14.user.gui.EventsNotificationShower;

public class TimedNotificator extends Notificator {
    private final Timer timer = new Timer();

    public TimedNotificator(final User user, final EventsNotificationShower notificationShower) {
        super(user, notificationShower);
        timer.schedule(new Notify(), 0, 10000);
    }

    public class Notify extends TimerTask {
        public void run() {
            try {
                getEvents().stream().forEach(e -> sendEventToNotify(e));
            } catch (final Exception e) {
                System.out.println(e.getMessage());

            }
        }
    }
}
