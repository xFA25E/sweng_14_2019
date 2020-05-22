package it.polimi.project14.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Set;

import it.polimi.project14.common.Event;
import it.polimi.project14.common.SearchFilter;

public interface EventStorage extends Remote {
    public void storeEvents(Set<Event> eventList) throws SQLException, RemoteException;
    public Set<Event> getEvents(SearchFilter searchFilter) throws SQLException, RemoteException;
}
