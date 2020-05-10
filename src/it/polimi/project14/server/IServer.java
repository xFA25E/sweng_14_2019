package it.polimi.project14.server;

import java.sql.SQLException;

import it.polimi.project14.common.EventList;
import it.polimi.project14.common.SearchFilter;

public interface IServer {
    public void storeEvents(EventList eventList) throws SQLException;
    public EventList getEvents(SearchFilter searchFilter) throws SQLException;
}
