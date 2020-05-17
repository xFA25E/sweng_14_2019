package it.polimi.project14.user;

import java.util.Set;
import java.sql.SQLException;
import java.time.LocalDate;

import it.polimi.project14.common.Event;
import it.polimi.project14.common.SearchFilter;
import it.polimi.project14.server.Server;

public class User implements NotificationManager {
	
	private Set<String> favoriteCaps;
	
	public void setFavotiteCaps(Set<String> favoriteCaps) {
        this.favoriteCaps = favoriteCaps;
    }
	
	public Set<Event> getUrgentEvents() throws SQLException{
		SearchFilter searchFilter = new SearchFilter();
		searchFilter.setCapList(favoriteCaps);
		searchFilter.setExpectedSince(LocalDate.now().atStartOfDay());
		searchFilter.setMaxSeverity(true);
		Server server = new Server();
		return server.getEvents(searchFilter); //RMI
	}
	
	public Set<Event> searchEvents(SearchFilter searchFilter) throws SQLException{
		Server server = new Server();
		return server.getEvents(searchFilter); //RMI
	}
	
}