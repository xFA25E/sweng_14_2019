package it.polimi.project14.user;

import java.util.Set;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.time.LocalDate;

import it.polimi.project14.common.Event;
import it.polimi.project14.common.SearchFilter;
import it.polimi.project14.server.EventStorage;

public class User {

	private Set<String> favoriteCaps;

	public void setFavotiteCaps(Set<String> favoriteCaps) {
		this.favoriteCaps = favoriteCaps;
	}

	public Set<Event> getUrgentEvents() throws Exception {
		SearchFilter searchFilter = new SearchFilter();
		searchFilter.setCapList(favoriteCaps);
		searchFilter.setExpectedSince(LocalDate.now().atStartOfDay());
		searchFilter.setMaxSeverity(true);
		try {
			EventStorage eventStorage = (EventStorage) Naming.lookup("EVENT_STORAGE");
			return eventStorage.getEvents(searchFilter); // RMI
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		} catch (RemoteException e) {
			throw new Exception(e.getMessage());
		}
	}

	public Set<Event> searchEvents(SearchFilter searchFilter) throws Exception {
		try {
			EventStorage eventStorage = (EventStorage) Naming.lookup("EVENT_STORAGE");
			return eventStorage.getEvents(searchFilter); // RMI
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		} catch (RemoteException e) {
			throw new Exception(e.getMessage());
		}
	}

}