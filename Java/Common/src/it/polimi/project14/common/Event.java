package it.polimi.project14.common;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.lang.Long;
import java.lang.Integer;

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
	public String toString() {
		return "Event [%n"
            + "    sourceId=" + sourceId + ",%n"
            + "    eventId=" + eventId + ",%n"
            + "    cap=" + cap + ",%n"
            + "    message=" + message + ",%n"
            + "    expectedAt=" + expectedAt + ",%n"
            + "    severity=" + severity + ",%n"
            + "    status=" + status + ",%n"
            + "    kind=" + kind + ",%n"
            + "]";
	}

    @Override
    public int compareTo(Event other) {
        int c = expectedAt.compareTo(other.expectedAt);
        if (c != 0) {
            return c;
        }
        c = new Integer(severity).compareTo(new Integer(other.severity));
        if (c != 0) {
            return -c;
        }
        c = kind.compareTo(other.kind);
        if (c != 0) {
            return c;
        }
        c = cap.compareTo(other.cap);
        if (c != 0) {
            return c;
        }
        c = status.compareTo(other.status);
        if (c != 0) {
            return c;
        }
        c = new Long(eventId).compareTo(new Long(other.eventId));
        if (c != 0) {
            return c;
        }
        c = new Long(sourceId).compareTo(new Long(other.sourceId));
        if (c != 0) {
            return c;
        }
        return message.compareTo(other.message);
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
            + (int) (sourceId ^ (sourceId >>> 32))
            + (int) (eventId ^ (eventId >>> 32))
            + (int) (severity ^ (severity >>> 32))
            + ((message == null) ? 0 : message.hashCode())
            + ((status == null) ? 0 : status.hashCode())
            + ((expectedAt == null) ? 0 : expectedAt.hashCode())
            + ((kind == null) ? 0 : kind.hashCode())
            + ((cap == null) ? 0 : cap.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;

        return sourceId == other.sourceId
            && eventId == other.eventId
            && cap.equals(other.cap)
            && message.equals(other.message)
            && expectedAt.equals(other.expectedAt)
            && severity == other.severity
            && status == other.status
            && kind.equals(other.kind);
	}
}
