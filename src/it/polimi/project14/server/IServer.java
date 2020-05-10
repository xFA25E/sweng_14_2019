package it.polimi.project14.server;

import java.sql.SQLException;
import java.util.Set;

import it.polimi.project14.common.Event;
import it.polimi.project14.common.SearchFilter;

public interface IServer {
    public void storeEvents(Set<Event> eventList) throws SQLException;
    public Set<Event> getEvents(SearchFilter searchFilter) throws SQLException;
}
