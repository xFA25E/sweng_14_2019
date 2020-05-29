package it.polimi.project14.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.OptionalInt;
import java.util.Set;
import java.util.stream.Collectors;

import it.polimi.project14.common.Event;
import it.polimi.project14.common.EventStatus;
import it.polimi.project14.common.EventStorage;
import it.polimi.project14.common.SearchFilter;

public class EventStorageImpl extends UnicastRemoteObject implements EventStorage {
    private static final long serialVersionUID = 1L;
    private static final String defaultUrl = "jdbc:sqlite:civil_protection.db";

    private static final String createQuery = ""
            + "CREATE TABLE IF NOT EXISTS event ("
            + "  source_id INTEGER NOT NULL,"
            + "  event_id INTEGER NOT NULL,"
            + "  cap INTEGER NOT NULL CHECK(`cap` BETWEEN 0 AND 99999),"
            + "  message TEXT DEFAULT NULL CHECK(LENGTH(`message`) BETWEEN 1 AND 255),"
            + "  expected_at INTEGER NOT NULL CHECK("
            + "    TYPEOF(`expected_at`) == 'integer' AND 0 <= `expected_at`"
            + "  ),"
            + "  severity INTEGER NOT NULL CHECK(`severity` BETWEEN 0 AND 10),"
            + "  status TEXT NOT NULL CHECK("
            + "    `status` == 'expected'"
            + "    OR `status` == 'ongoing'"
            + "    OR `status` == 'occured'"
            + "    OR `status` == 'canceled'"
            + "  ),"
            + "  kind TEXT NOT NULL CHECK(LENGTH(`kind`) <> 0),"
            + "  PRIMARY KEY (`source_id`, `event_id`)"
            + ")";

    private static final String insertQuery = ""
        + "INSERT INTO event (source_id, event_id, cap, message,"
        + "                   expected_at, severity, status, kind) "
        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?) "
        + "ON CONFLICT(source_id, event_id) DO UPDATE"
        + "  severity = ?,"
        + "  message = ?,"
        + "  status = ? "
        + "WHERE status NOT IN ('occured', 'canceled')";

    private static final String selectQuery = ""
        + "SELECT source_id,"
        + "       event_id,"
        + "       printf(\"%05d\", cap) AS cap,"
        + "       message,"
        + "       expected_at,"
        + "       severity,"
        + "       status,"
        + "       kind"
        + "  FROM event"
        + " WHERE (? IS NULL OR kind = ?)"
        + "   AND (? IS NULL OR ? < expected_at)"
        + "   AND (? IS NULL OR expected_at < ?)";

    private final String url;

    public EventStorageImpl() throws SQLException, RemoteException  {
        this(defaultUrl);
    }

    public EventStorageImpl(String dbUrl) throws SQLException, RemoteException  {
        url = dbUrl;

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            stmt.execute(createQuery);
        }
    }

    public void storeEvents(Set<Event> eventList) throws SQLException, RemoteException {
        if (eventList != null && eventList.size() > 0) {
            try (Connection conn = DriverManager.getConnection(url);
                 PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {

                for (Event event : eventList) {
                    pstmt.setLong(1, event.getSourceId());
                    pstmt.setLong(2, event.getEventId());
                    pstmt.setInt(3, stringToInt(event.getCap()));
                    pstmt.setString(4, event.getMessage());
                    pstmt.setLong(5, dateTimeToEpoch(event.getExpectedAt()));
                    pstmt.setInt(6, event.getSeverity());
                    pstmt.setString(7, statusToString(event.getStatus()));
                    pstmt.setString(8, event.getKind());
                    pstmt.setInt(9, event.getSeverity());
                    pstmt.setString(10, event.getMessage());
                    pstmt.setString(11, statusToString(event.getStatus()));

                    pstmt.addBatch();
                }
                pstmt.executeUpdate();
            }
        }
    }

    public Set<Event> getEvents(SearchFilter searchFilter) throws SQLException, RemoteException {
        String kind = getKind(searchFilter);
        Long expectedSince = getExpectedSince(searchFilter);
        Long expectedUntil = getExpectedUntil(searchFilter);
        String query = generateSelectQuery(searchFilter);
        Set<Event> eventList = new HashSet<Event>();

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setObject(1, kind, Types.VARCHAR);
            pstmt.setObject(2, kind, Types.VARCHAR);
            pstmt.setObject(3, expectedSince, Types.INTEGER);
            pstmt.setObject(4, expectedSince, Types.INTEGER);
            pstmt.setObject(5, expectedUntil, Types.INTEGER);
            pstmt.setObject(6, expectedUntil, Types.INTEGER);

            try (ResultSet rSet = pstmt.executeQuery()) {
                while (rSet.next()) {
                    Event event = new Event();
                    event.setSourceId(rSet.getLong("source_id"));
                    event.setEventId(rSet.getLong("event_id"));
                    event.setCap(rSet.getString("cap"));
                    event.setMessage(rSet.getString("message"));
                    event.setExpectedAt(epochToDateTime(rSet.getLong("expected_at")));
                    event.setSeverity(rSet.getInt("severity"));
                    event.setStatus(stringToStatus(rSet.getString("status")));
                    event.setKind(rSet.getString("kind"));

                    eventList.add(event);
                }
            }
        }

        if (isMaxSeverity(searchFilter)) {
            OptionalInt maybeMax = eventList.stream()
                .mapToInt(e -> e.getSeverity())
                .max();

            if (maybeMax.isPresent()) {
                int max = maybeMax.getAsInt();
                eventList = eventList.stream()
                    .filter(e -> e.getSeverity() == max)
                    .collect(Collectors.toCollection(HashSet::new));
            }
        }

        return eventList;
    }

    // It seems that sqlite does not support arrays. We have to escape and add
    // strings manually. This basically adds a string to query like:
    // "AND cap IN ('cap1', 'cap2'..)"
    public final static String generateSelectQuery(SearchFilter searchFilter) {
        StringBuilder builder = new StringBuilder(selectQuery);
        if (searchFilter != null) {
            Set<String> capList = searchFilter.getCapList();
            if (capList != null) {
                builder.append(capList.stream()
                               .filter(c -> c.matches("\\d{5}"))
                               .map(c -> "'" + c + "'")
                               .collect(Collectors
                                        .joining(", ", " AND cap IN (", ")")));
            }
        }
        return builder.toString();
    }

    public final static boolean isMaxSeverity(SearchFilter searchFilter) {
        return searchFilter != null && searchFilter.isMaxSeverity();
    }

    public final static Long getExpectedSince(SearchFilter searchFilter) {
        Long expectedSince = null;
        if (searchFilter != null) {
            expectedSince = dateTimeToEpoch(searchFilter.getExpectedSince());
        }
        return expectedSince;
    }

    public final static Long getExpectedUntil(SearchFilter searchFilter) {
        Long expectedUntil = null;
        if (searchFilter != null) {
            expectedUntil = dateTimeToEpoch(searchFilter.getExpectedUntil());
        }
        return expectedUntil;
    }

    public final static String getKind(SearchFilter searchFilter) {
        String kind = null;
        if (searchFilter != null) {
            kind = searchFilter.getKind();
        }
        return kind;
    }

    public final static int stringToInt(String cap) {
        return Integer.parseInt(cap);
    }

    public final static Long dateTimeToEpoch(LocalDateTime dateTime) {
        if (dateTime != null) {
            return dateTime.toEpochSecond(ZoneOffset.UTC);
        } else {
            return null;
        }
    }

    public final static LocalDateTime epochToDateTime(long epoch) {
        return LocalDateTime.ofEpochSecond(epoch, 0, ZoneOffset.UTC);
    }

    public final static EventStatus stringToStatus(String status) {
        switch (status) {
        case "expected":
            return EventStatus.EXPECTED;
        case "ongoing":
            return EventStatus.ONGOING;
        case "occured":
            return EventStatus.OCCURED;
        case "canceled":
            return EventStatus.CANCELED;
        default:
            throw new IllegalArgumentException();
        }
    }

    public final static String statusToString(EventStatus status) {
        switch (status) {
        case EXPECTED:
            return "expected";
        case ONGOING:
            return "ongoing";
        case OCCURED:
            return "occured";
        case CANCELED:
            return "canceled";
        default:
            throw new IllegalArgumentException();
        }
    }
}
