package it.polimi.project14.user;

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
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

	public Set<String> getFavoriteCaps() {
		return this.favoriteCaps;
	}

	public SortedSet<Event> getUrgentEvents() throws Exception {
		SearchFilter searchFilter = new SearchFilter();
		searchFilter.setCapList(favoriteCaps);
		searchFilter.setExpectedSince(LocalDate.now().atStartOfDay());
		searchFilter.setMaxSeverity(true);

		return searchEvents(searchFilter);
	}

	public SortedSet<Event> searchEvents(SearchFilter searchFilter) throws Exception {
		// RMI
		try {
			EventStorage remoteEventStorage = (EventStorage) Naming.lookup(urlServer + "EVENT_STORAGE");
			return (SortedSet<Event>) remoteEventStorage.getEvents(searchFilter);
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		} catch (RemoteException e) {
			throw new Exception(e.getMessage());
		} catch (Exception e) {
			// Stop program execution
			System.out.println(e.getMessage());
			System.exit(0);
			// Only cause java doesn't know what System.exit do and compiler
			// need the return value
			return new TreeSet<Event>();
		}
	}

}