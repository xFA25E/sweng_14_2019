package it.polimi.project14.user;

import java.util.Set;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.time.LocalDate;

import it.polimi.project14.common.Event;
import it.polimi.project14.common.EventStorage;
import it.polimi.project14.common.SearchFilter;


public class User {

	private Set<String> favoriteCaps;

	final private String urlServer = "rmi://localhost:6666/";
	static {
		System.setProperty("java.security.policy", "policy.all");
	}

	public User(Set<String> favouriteCaps) {
		this.favoriteCaps = favouriteCaps;
	}

	public void setFavotiteCaps(Set<String> favoriteCaps) {
		this.favoriteCaps = favoriteCaps;
	}

	public Set<Event> getUrgentEvents() throws Exception {
		SearchFilter searchFilter = new SearchFilter();
		searchFilter.setCapList(favoriteCaps);
		searchFilter.setExpectedSince(LocalDate.now().atStartOfDay());
		searchFilter.setMaxSeverity(true);

		//RMI
		try {
            EventStorage remoteEventStorage = (EventStorage) Naming.lookup(urlServer + "EVENT_STORAGE");
			return remoteEventStorage.getEvents(searchFilter);
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		} catch (RemoteException e) {
			throw new Exception(e.getMessage());
		}
	}

	public Set<Event> searchEvents(SearchFilter searchFilter) throws Exception {
		//RMI
		try {
			EventStorage remoteEventStorage = (EventStorage) Naming.lookup(urlServer + "EVENT_STORAGE");
			return remoteEventStorage.getEvents(searchFilter);
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		} catch (RemoteException e) {
			throw new Exception(e.getMessage());
		}
	}

}