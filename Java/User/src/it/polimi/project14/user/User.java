package it.polimi.project14.user;

import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.HashSet;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.time.LocalDateTime;

import it.polimi.project14.common.Event;
import it.polimi.project14.common.EventStorage;
import it.polimi.project14.common.SearchFilter;

public class User {
    private final Set<String> favoriteCaps = new HashSet<>();

	private final String urlServer = "rmi://localhost:6666/";

	static {
		System.setProperty("java.security.policy", "policy.all");
	}

    public User() {}

	public User(final Set<String> favouriteCaps) {
		this.favoriteCaps.addAll(Objects.requireNonNull(favouriteCaps));
	}

	public Set<String> getFavoriteCaps() {
        return favoriteCaps;
	}

    public void clearFavoriteCaps() {
		favoriteCaps.clear();
	}

	public void addFavoriteCaps(final Set<String> caps) {
		favoriteCaps.addAll(Objects.requireNonNull(caps));
	}

	public SortedSet<Event> getUrgentEvents() throws Exception {
		final SearchFilter searchFilter = new SearchFilter();
		searchFilter.setCapList(favoriteCaps);
		// set expectedSicne at start of current hour
		searchFilter.setExpectedSince(LocalDateTime.now().withMinute(0));
		searchFilter.setMaxSeverity(true);

		return searchEvents(searchFilter);
	}

	public SortedSet<Event> searchEvents(SearchFilter searchFilter) throws Exception {
		Set<String> filterCaps = searchFilter.getCapList();
		if (filterCaps == null || filterCaps.isEmpty()) {
			return new TreeSet<Event>();
		}

		// RMI
		try {
			final EventStorage remoteEventStorage = (EventStorage) Naming.lookup(urlServer + "EVENT_STORAGE");
            return new TreeSet<Event>(remoteEventStorage.getEvents(searchFilter));
		} catch (final SQLException e) {
			throw new Exception(e.getMessage());
		} catch (final RemoteException e) {
			throw new Exception(e.getMessage());
		} catch (final Exception e) {
			// Stop program execution
			System.out.println(e.getMessage());
			System.exit(0);
			// Only cause java doesn't know what System.exit do and compiler
			// need the return value
            return null;
		}
	}

}
