package it.polimi.project14.common;

import java.time.LocalDateTime;

public class Event {
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

    public void setCap(String cap) {
        this.cap = cap;
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

    public void setSeverity(int severity) {
        this.severity = severity;
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
}
