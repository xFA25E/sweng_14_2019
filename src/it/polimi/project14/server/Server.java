package it.polimi.project14.server;

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
import java.util.Set;
import java.util.stream.Collectors;

import it.polimi.project14.common.Event;
import it.polimi.project14.common.EventStatus;
import it.polimi.project14.common.SearchFilter;
import it.polimi.project14.server.IServer;

public class Server implements IServer {
    private static String url = "jdbc:sqlite:civil_protection.db";

    private static String createQuery = ""
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

    private static String insertQuery = ""
        + "INSERT INTO event (source_id, event_id, cap, message,"
        + "                     expected_at, severity, status, kind)"
        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    private static String selectQuery = ""
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
        + "   AND (? IS NULL OR expected_at BETWEEN ? AND ? + 3600)";

    public Server() throws SQLException {
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            stmt.execute(createQuery);
        }
    }

    public void storeEvents(Set<Event> eventList) throws SQLException {
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

                    pstmt.addBatch();
                }
                pstmt.executeUpdate();
            }
        }
    }

    public Set<Event> getEvents(SearchFilter searchFilter) throws SQLException {
        String kind = getKind(searchFilter);
        Long expectedAt = getExpectedAt(searchFilter);
        String query = generateSelectQuery(searchFilter);
        Set<Event> eventList = new HashSet<Event>();

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setObject(1, kind, Types.VARCHAR);
            pstmt.setObject(2, kind, Types.VARCHAR);
            pstmt.setObject(3, expectedAt, Types.INTEGER);
            pstmt.setObject(4, expectedAt, Types.INTEGER);
            pstmt.setObject(5, expectedAt, Types.INTEGER);

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
        return eventList;
    }

    // It seems that sqlite does not support arrays. We have to escape and add
    // strings manually. This basically adds a string to query like:
    // "AND cap IN ('cap1', 'cap2'..)"
    private static String generateSelectQuery(SearchFilter searchFilter) {
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

    private static Long getExpectedAt(SearchFilter searchFilter) {
        Long expectedAt = null;
        if (searchFilter != null) {
            expectedAt = dateTimeToEpoch(searchFilter.getExpectedAt());
        }
        return expectedAt;
    }

    private static String getKind(SearchFilter searchFilter) {
        String kind = null;
        if (searchFilter != null) {
            kind = searchFilter.getKind();
        }
        return kind;
    }

    private static int stringToInt(String cap) {
        return Integer.parseInt(cap);
    }

    private static Long dateTimeToEpoch(LocalDateTime dateTime) {
        if (dateTime != null) {
            return dateTime.toEpochSecond(ZoneOffset.UTC);
        } else {
            return null;
        }
    }

    private static LocalDateTime epochToDateTime(long epoch) {
        return LocalDateTime.ofEpochSecond(epoch, 0, ZoneOffset.UTC);
    }

    public static EventStatus stringToStatus(String status) {
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

    public String statusToString(EventStatus status) {
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
