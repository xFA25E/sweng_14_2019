package it.polimi.project14.common;

import java.time.LocalDateTime;

enum EventStatus {
    PREVISTO,
    INCORSO,
    ACCADUTO,
    RIENTRATO;
}

public class Event {
    private int id;
    private int cap;
    private String message;
    private String type;
    private LocalDateTime exeptedAt;
    private int gravity;    
    private EventStatus status;
    private int source;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCap() {
        return cap;
    }

    public void setCap(int cap) {
        this.cap = cap;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getExeptedAt() {
        return exeptedAt;
    }

    public void setExeptedAt(LocalDateTime exeptedAt) {
        this.exeptedAt = exeptedAt;
    }

    public int getGravity() {
        return gravity;
    }

    public void setGravity(int gravity) {
        this.gravity = gravity;
    }

    public EventStatus getStatus() {
        return status;
    }

    public void setStatus(EventStatus status) {
        this.status = status;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }
}