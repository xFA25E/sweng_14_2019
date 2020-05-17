package it.polimi.project14.user;

import java.util.Set;
import java.sql.SQLException;

import it.polimi.project14.common.Event;
import it.polimi.project14.common.SearchFilter;

public interface NotificationManager {
    public void setFavotiteCaps(Set<String> favoriteCaps);
	public Set<Event> getUrgentEvents() throws SQLException;
    public Set<Event> searchEvents(SearchFilter searchFilter) throws SQLException;
}
