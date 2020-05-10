package it.polimi.project14.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import it.polimi.project14.common.Event;
import it.polimi.project14.common.EventStatus;
import it.polimi.project14.common.SearchFilter;
import it.polimi.project14.server.IServer;

public class Server implements IServer {
    private static String url = "jdbc:sqlite:civil_protection.db";
    private static DateTimeFormatter formatter
        = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static void createTables(Connection conn) throws SQLException {
        String create_table_query = ""
            + "CREATE TABLE IF NOT EXISTS raw_event ("
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
            + "  PRIMARY KEY (`source_id`, `event_id`)" + ")";

        String create_view_query = ""
            + "CREATE VIEW IF NOT EXISTS event AS"
            + "  SELECT source_id,"
            + "         event_id,"
            + "         printf('%05d', cap) AS cap,"
            + "         message,"
            + "         DATETIME(expected_at, 'unixepoch') AS expected_at,"
            + "         severity,"
            + "         status,"
            + "         kind"
            + "    FROM raw_event";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(create_table_query);
            stmt.execute(create_view_query);
        }
    }

    public Server() throws SQLException {
        try (Connection conn = DriverManager.getConnection(url)) {
            Server.createTables(conn);
        }
    }

    public void storeEvents(Set<Event> eventList) throws SQLException {
        if (eventList != null && eventList.size() > 0) {

            StringBuilder builder = new StringBuilder();
            builder.append("INSERT INTO raw_event (source_id, event_id, cap, message,"
                           + "                     expected_at, severity, status, kind)"
                           + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

            for (int i = 1; i < eventList.size(); i++) {
                builder.append(",(?, ?, ?, ?, ?, ?, ?, ?)");
            }

            String query = builder.toString();

            try (Connection conn = DriverManager.getConnection(url)) {
                try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                    int i = 0;
                    for (Event event : eventList) {
                        String status = null;
                        switch (event.getStatus()) {
                        case EXPECTED:
                            status = "expected";
                            break;
                        case ONGOING:
                            status = "ongoing";
                            break;
                        case OCCURED:
                            status = "occured";
                            break;
                        case CANCELED:
                            status = "canceled";
                            break;
                        }

                        pstmt.setLong(i + 1, event.getSourceId());
                        pstmt.setLong(i + 2, event.getEventId());
                        pstmt.setInt(i + 3, Integer.parseInt(event.getCap()));
                        pstmt.setString(i + 4, event.getMessage());
                        pstmt.setLong(i + 5, event.getExpectedAt().toEpochSecond(ZoneOffset.UTC));
                        pstmt.setInt(i + 6, event.getSeverity());
                        pstmt.setString(i + 7, status);
                        pstmt.setString(i + 8, event.getKind());

                        i += 8;
                    }

                    pstmt.executeUpdate();
                }
            }
        }
    }

    public Set<Event> getEvents(SearchFilter searchFilter) throws SQLException {
        StringBuilder builder = new StringBuilder("SELECT * FROM event");

        if (searchFilter != null) {
            Set<String> wheres = new HashSet<String>();

            LocalDateTime expectedAt = searchFilter.getExpectedAt();
            if (expectedAt != null) {
                wheres.add("expected_at = " + expectedAt.toEpochSecond(ZoneOffset.UTC));
            }

            String kind = searchFilter.getKind();
            if (kind != null) {
                wheres.add("kind = " + kind);
            }

            Set<String> capList = searchFilter.getCapList();
            if (capList != null) {
                wheres.add("cap IN (" + String.join(", ", capList) + ") ");
            }

            if (wheres.size() > 0) {
                builder.append("WHERE " + String.join(" AND ", wheres));
            }
        }

        String query = builder.toString();

        Set<Event> eventList = new HashSet<Event>();

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rset = stmt.executeQuery(query)) {
            while (rset.next()) {
                Event event = new Event();
                EventStatus status = null;
                switch (rset.getString("status")) {
                case "expected":
                    status = EventStatus.EXPECTED;
                    break;
                case "ongoing":
                    status = EventStatus.ONGOING;
                    break;
                case "occured":
                    status = EventStatus.OCCURED;
                    break;
                case "canceled":
                    status = EventStatus.CANCELED;
                    break;
                }

                event.setSourceId(rset.getLong("source_id"));
                event.setEventId(rset.getLong("event_id"));
                event.setCap(rset.getString("cap"));
                event.setMessage(rset.getString("message"));
                event.setExpectedAt(LocalDateTime.parse(rset.getString("expected_at"), formatter));
                event.setSeverity(rset.getInt("severity"));
                event.setStatus(status);
                event.setKind(rset.getString("kind"));

                eventList.add(event);
            }
        }

        return eventList;
    }
}
