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
	
	public Set<String> getFavoriteCaps() {
		return this.favoriteCaps;
	}

	public void setFavoriteCaps(Set<String> favoriteCaps) {
		this.favoriteCaps = favoriteCaps;
	}

	public void addFavoriteCap(String cap) {
		if (this.favoriteCaps == null) {
			this.favoriteCaps = new TreeSet<String>();
		}
		this.favoriteCaps.add(cap); 
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
			SortedSet<Event> foundEvents = new TreeSet<Event>(remoteEventStorage.getEvents(searchFilter));
			return foundEvents;
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