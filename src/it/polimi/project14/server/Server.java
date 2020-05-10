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
import java.util.ArrayList;
import java.util.List;

import it.polimi.project14.common.EventList;
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

    public void storeEvents(EventList eventList) throws SQLException {
        List<Event> list = new ArrayList<Event>();

        if (list.size() > 0) {

            StringBuilder builder = new StringBuilder();
            builder.append("INSERT INTO raw_event (source_id, event_id, cap, message,"
                           + "                     expected_at, severity, status, kind)"
                           + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

            for (int i = 1; i < list.size(); i++) {
                builder.append(",(?, ?, ?, ?, ?, ?, ?, ?)");
            }

            String query = builder.toString();

            try (Connection conn = DriverManager.getConnection(url)) {
                try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                    int i = 0;
                    for (Event event : list) {
                        String status = null;
                        switch (event.status) {
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

                        pstmt.setLong(i + 1, event.sourceId);
                        pstmt.setLong(i + 2, event.eventId);
                        pstmt.setInt(i + 3, Integer.parseInt(event.cap));
                        pstmt.setString(i + 4, event.message);
                        pstmt.setLong(i + 5, event.expectedAt.toEpochSecond(ZoneOffset.UTC));
                        pstmt.setInt(i + 6, event.severity);
                        pstmt.setString(i + 7, status);
                        pstmt.setString(i + 8, event.kind);

                        i += 8;
                    }

                    pstmt.executeUpdate();
                }
            }
        }
    }

    public EventList getEvents(SearchFilter searchFilter) throws SQLException {
        String query = "SELECT * FROM event";

        EventList eventList = new EventList();
        List<Event> list = new ArrayList<Event>();

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rset = stmt.executeQuery(query)) {
            while (rset.next()) {
                Event event = new Event();
                event.sourceId = rset.getLong("source_id");
                event.eventId = rset.getLong("event_id");
                event.cap = rset.getString("cap");
                event.message = rset.getString("message");
                event.expectedAt
                    = LocalDateTime.parse(rset.getString("expected_at"), formatter);
                event.severity = rset.getInt("severity");

                Status status = null;

                switch (rset.getString("status")) {
                case "expected":
                    status = Status.EXPECTED;
                    break;
                case "ongoing":
                    status = Status.ONGOING;
                    break;
                case "occured":
                    status = Status.OCCURED;
                    break;
                case "canceled":
                    status = Status.CANCELED;
                    break;
                }

                event.status = status;

                event.kind = rset.getString("kind");

                list.add(event);
            }
        }

        return eventList;
    }
}

enum Status {
    EXPECTED, ONGOING, OCCURED, CANCELED
}

class Event {
    public long sourceId;
    public long eventId;
    public String cap;
    public String message;
    public LocalDateTime expectedAt;
    public int severity;
    public Status status ;
    public String kind;
}
