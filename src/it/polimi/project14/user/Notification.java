package it.polimi.project14.user;

import java.util.Set;

import it.polimi.project14.common.Event;

public abstract class Notification {
    private Set<Event> notifiedEvents;
    protected abstract void sendEventsToNotify(Set<Event> eventsToNotify);
}
