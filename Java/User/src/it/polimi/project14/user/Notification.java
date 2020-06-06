package it.polimi.project14.user;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import it.polimi.project14.common.Event;
import it.polimi.project14.common.SearchFilter;
import it.polimi.project14.user.gui.EventsNotificationShower;

public abstract class Notificator {
  private Set<Event> notifiedEvents = new HashSet<Event>();
  private EventsNotificationShower notificationShower;
  private User user;

  public Notificator(User user, EventsNotificationShower notificationShower) {
    this.user = Objects.requireNonNull(user);
    this.notificationShower = Objects.requireNonNull(notificationShower);

    try {
      notifiedEvents.addAll(getForecasts());
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
