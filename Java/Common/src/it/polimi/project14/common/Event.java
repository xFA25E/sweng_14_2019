package it.polimi.project14.common;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Event implements Comparable<Event>, Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -1046844052828004353L;

    private long sourceId;
    private long eventId;
    private String cap;
    private String message;
    private LocalDateTime expectedAt;
    private int severity;
    private EventStatus status;
    private String kind;


    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) throws IllegalArgumentException {
        if (cap.matches("\\d{5}")) {
            this.cap = cap;
        } else {
            throw new IllegalArgumentException("Cap should be a string of 5 digits");
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public LocalDateTime getExpectedAt() {
        return expectedAt;
    }

    public void setExpectedAt(LocalDateTime expectedAt) {
        this.expectedAt = expectedAt;
    }

    public int getSeverity() {
        return severity;
    }

    public void setSeverity(int severity) throws IllegalArgumentException {
        if (0 <= severity && severity <= 10) {
            this.severity = severity;
        } else {
            throw new IllegalArgumentException("Severity should be a number from 0 to 10");
        }
    }

    public EventStatus getStatus() {
        return status;
    }

    public void setStatus(EventStatus status) {
        this.status = status;
    }

    public long getSourceId() {
        return sourceId;
    }

    public void setSourceId(long sourceId) {
        this.sourceId = sourceId;
    }

    @Override
    public int compareTo(Event other) {
        return this.expectedAt.compareTo(other.getExpectedAt());
    }

	@Override
	public String toString() {
		return "Event [\n"
            + "    sourceId=" + sourceId + ",\n"
            + "    eventId=" + eventId + ",\n"
            + "    cap=" + cap + ",\n"
            + "    message=" + message + ",\n"
            + "    expectedAt=" + expectedAt + ",\n"
            + "    severity=" + severity + ",\n"
            + "    status=" + status + ",\n"
            + "    kind=" + kind + ",\n"
            + "]";
	}
}
