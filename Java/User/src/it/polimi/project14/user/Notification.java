package it.polimi.project14.user;

import java.util.HashSet;
import java.util.Set;

import it.polimi.project14.common.Event;
import it.polimi.project14.common.SearchFilter;
import it.polimi.project14.gui.ViewNotification;

public abstract class Notification {
    private Set<Event> notifiedEvents;
	private Set<Event> eventsToNotify;
    private User user;
	private Set<String> favoriteCaps; //?

    Notification(User user) {
        this.user = user;
        notifiedEvents = new HashSet<Event>();
    }
    
    private boolean checkForecasts() { //boolean o passo come parametro o controllo dentro check?
		SearchFilter searchFilter = new SearchFilter();
		searchFilter.setCapList(favoriteCaps);
		eventsToNotify = user.searchEvents(searchFilter);
		eventsToNotify.removeAll(notifiedEvents); //sottrazione eventi
		return eventsToNotify.isEmpty();
    }

    protected abstract void sendEventsToNotify() {  //Set<Event> eventsToNotify) {
		ViewNotification(eventsToNotify); //GUI non ancora fatto
		notifiedEvents.addAll(eventsToNotify);
		eventsToNotify.clear();
	}
}
