package it.polimi.project14.source;

import java.io.Serializable;
import java.time.LocalDateTime;
import it.polimi.project14.common.Event;
import it.polimi.project14.common.EventStatus;

public class RandomEvent {

    private static final String[] COMO_CAPS = {
        "22033", "22077", "22034", "22078", "22079", "22035", "22036", "22037",
        "22038", "22039", "22070", "22071", "22072", "22073", "22030", "22074",
        "22031", "22075", "22076", "22032", "22044", "22045", "22046", "22040",
        "22041", "22042", "22043", "22011", "22012", "22013", "22014", "22015",
        "22016", "22017", "22018", "22010", "22066", "22100", "22023", "22024",
        "22025", "22069", "22026", "22027", "22028", "22029", "22060", "22063",
        "22020", "22021",
    };

    private static final String[] KINDS = {
        "Brillamento solare", "Carestia", "Desertificazione", "Epidemia",
        "Eruzione vulcanica", "Frana", "Impatto meteoritico", "Incendio",
        "Inondazione e alluvione", "Lahar", "Maelström", "Ondata di caldo",
        "Ondata di freddo", "Pandemia", "Terremoto", "Tornado", "Tsunami",
        "Uragano", "Valanga",
    };

    public static final int MAX_SEVERITY = 10;
    private static final int MAX_TIME_SHIFT = 24 * 60 * 60; // 24 HOURS
    private static final int ONGOING_SHIFT = 1 * 60 * 60; // 1 HOUR

    public static Event generate() {
        LocalDateTime now = LocalDateTime.now();
        long randomShift = (long) (Math.random() * MAX_TIME_SHIFT);
        Event event = new Event();

        event.setSourceId((long) (Math.random() * Long.MAX_VALUE));
        event.setEventId((long) (Math.random() * Long.MAX_VALUE));
        event.setCap(COMO_CAPS[(int) (Math.random() * COMO_CAPS.length)]);
        event.setMessage("Messaggio pseudo-casuale!");
        event.setExpectedAt(now.plusSeconds(randomShift));
        event.setSeverity((int) (Math.random() * MAX_SEVERITY));
        event.setKind(KINDS[(int) (Math.random() * KINDS.length)]);

        if (randomShift < ONGOING_SHIFT) {
            event.setStatus(EventStatus.ONGOING);
        } else {
            event.setStatus(EventStatus.EXPECTED);
        }

        return event;
    }
}
