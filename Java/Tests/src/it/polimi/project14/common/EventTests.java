package it.polimi.project14.common;

import org.junit.Test;
import org.junit.Assert;
import java.time.LocalDateTime;

public class EventTests {
    @Test
    public void testEvent() {
        Event event = new Event();

        String cap0 = "00000";
        event.setCap(cap0);
        Assert.assertEquals(event.getCap(), cap0);

        String cap1 = "99999";
        event.setCap(cap1);
        Assert.assertEquals(event.getCap(), cap1);

        long eventId = 343;
        event.setEventId(eventId);
        Assert.assertEquals(event.getEventId(), eventId);

        String message = "Hello";
        event.setMessage(message);
        Assert.assertEquals(event.getMessage(), message);

        String kind = "Earthquake";
        event.setKind(kind);
        Assert.assertEquals(event.getKind(), kind);

        LocalDateTime expectedAt = LocalDateTime.now();
        event.setExpectedAt(expectedAt);
        Assert.assertEquals(event.getExpectedAt(), expectedAt);

        int severity = 8;
        event.setSeverity(severity);
        Assert.assertEquals(event.getSeverity(), severity);

        EventStatus status = EventStatus.EXPECTED;
        event.setStatus(status);
        Assert.assertEquals(event.getStatus(), status);

        long sourceId = 344;
        event.setSourceId(sourceId);
        Assert.assertEquals(event.getSourceId(), sourceId);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testSetCapException() {
        String cap = "hello world";
        Event event = new Event();
        event.setCap(cap);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testSetSeverityException() {
        int severity = 45;
        Event event = new Event();
        event.setSeverity(severity);
    }
}
