package it.polimi.project14.user;

import java.util.HashSet;
import java.util.Set;

import it.polimi.project14.common.Event;
import it.polimi.project14.common.SearchFilter;
import it.polimi.project14.user.gui.EventsNotificationShower;

public abstract class Notification {
  private Set<Event> notifiedEvents;
  private EventsNotificationShower notificationShower;
  private User user;

  public Notification(User user, EventsNotificationShower notificationShower) {
    this.user = user;
    this.notificationShower = notificationShower;
    notifiedEvents = new HashSet<Event>();
    try {
      notifiedEvents = getForecasts();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  protected void setNotificationShower(EventsNotificationShower notificationShower) {
    this.notificationShower = notificationShower;
  }

  protected EventsNotificationShower getNotificationShower() {
    return notificationShower;
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
      notificationShower.showNotification(eventToNotify);
      notifiedEvents.add(eventToNotify);
    } catch (Exception e) {

    }
  }
}