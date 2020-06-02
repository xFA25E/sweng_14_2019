package it.polimi.project14.user.gui;

import it.polimi.project14.common.Event;

public interface EventsNotifiable {
    public void showNotification(Event eventToNotify) throws Exception;
}