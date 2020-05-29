package it.polimi.project14.user;

import java.util.HashSet;
import java.util.Set;

import it.polimi.project14.common.Event;
import it.polimi.project14.common.SearchFilter;
//import it.polimi.project14.gui.ViewNotification;

public abstract class Notification {
    private Set<Event> notifiedEvents;
    
    private User user;

    Notification(User user) {
        this.user = user;
        notifiedEvents = new HashSet<Event>();
    }
    
    protected Set<Event> getForecasts() throws Exception{
		SearchFilter searchFilter = new SearchFilter();
		searchFilter.setCapList(user.getFavoriteCaps());
		Set<Event> eventsToNotify = user.searchEvents(searchFilter);
		eventsToNotify.removeAll(notifiedEvents); //sottrazione degli eventi già notificati
		return eventsToNotify;
    }

    protected void sendEventsToNotify(Set<Event> eventsToNotify) {
		//if (ViewNotification(eventsToNotify)) { //GUI
		    notifiedEvents.addAll(eventsToNotify);
		}
}
