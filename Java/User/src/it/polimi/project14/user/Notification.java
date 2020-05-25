package it.polimi.project14.user;

import java.util.HashSet;
import java.util.Set;

import it.polimi.project14.common.Event;

public abstract class Notification {
    private Set<Event> notifiedEvents;
    private User user;

    Notification(User user) {
        this.user = user;
        notifiedEvents = new HashSet<Event>();
    }
    
    private void checkForecasts() {
		Sea
        user.searchEvents(); //?????
		//sottrazione eventi
    }

    protected abstract void sendEventsToNotify(Set<Event> eventsToNotify) {
		
	}
}
