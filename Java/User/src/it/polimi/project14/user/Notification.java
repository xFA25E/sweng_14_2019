package it.polimi.project14.user;

import java.util.HashSet;
import java.util.Set;

import it.polimi.project14.common.Event;
import it.polimi.project14.common.SearchFilter;
//import it.polimi.project14.gui.ViewNotification;
import it.polimi.project14.user.gui.EventsNotifiable;

public abstract class Notification {
  private Set<Event> notifiedEvents;
  private EventsNotifiable eventsNotifiable;
  private User user;

  Notification(User user) {
    this.user = user;
    notifiedEvents = new HashSet<Event>();
  }

  protected Set<Event> getForecasts() throws Exception {
    SearchFilter searchFilter = new SearchFilter();
    searchFilter.setCapList(user.getFavoriteCaps());
    Set<Event> eventsToNotify = user.searchEvents(searchFilter);
    // substraction of yet notified events
    // TODO: check what substraction do
    eventsToNotify.removeAll(notifiedEvents);

    return eventsToNotify;
  }

  protected void sendEventToNotify(Event eventToNotify) {
    try {
      eventsNotifiable.showNotification(eventToNotify);
      notifiedEvents.add(eventToNotify);
    } catch (Exception e) {

    }
  }
}
