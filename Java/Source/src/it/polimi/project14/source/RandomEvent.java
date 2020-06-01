package it.polimi.project14.source;

import java.time.LocalDateTime;
import it.polimi.project14.common.Event;
import it.polimi.project14.common.EventStatus;

public class RandomEvent extends Event {
    private static final long serialVersionUID = 1L;

    private static final String[] RANDOM_CAPS = {
        "00025", "00125", "00149", "00152", "01035", "02021", "06044", "06128",
        "07037", "10030", "10065", "10094", "12037", "12045", "12046", "12082",
        "13012", "13036", "13876", "14012", "16138", "16152", "17031", "18020",
        "20017", "20026", "20066", "20121", "20142", "20823", "21033", "21043",
        "23813", "25012", "25134", "26010", "26032", "26037", "26100", "26812",
        "26853", "27047", "28831", "28861", "29122", "31029", "32100", "33022",
        "33025", "33033", "35027", "36042", "37052", "37066", "37122", "39036",
        "39037", "39050", "40010", "40011", "40135", "48018", "51013", "52015",
        "52043", "53011", "53012", "54038", "57014", "57025", "58022", "60038",
        "60126", "63083", "63100", "65014", "67056", "67069", "71011", "71022",
        "71040", "72028", "73014", "73045", "73056", "74019", "80025", "81040",
        "82017", "82021", "82025", "83032", "88040", "88060", "89131", "89861",
        "90019", "91011", "92019", "98031",
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
    private static final int ONGOING_SHIFT = 1 * 60 * 60; // 24 HOURS

    public RandomEvent() {
        LocalDateTime now = LocalDateTime.now();
        long randomShift = (long) (Math.random() * MAX_TIME_SHIFT);

        this.setSourceId((long) (Math.random() * Long.MAX_VALUE));
        this.setEventId((long) (Math.random() * Long.MAX_VALUE));
        this.setCap(RANDOM_CAPS[(int) (Math.random() * RANDOM_CAPS.length)]);
        this.setMessage("Messaggio pseudo-casuale!");
        this.setExpectedAt(now.plusSeconds(randomShift));
        this.setSeverity((int) (Math.random() * MAX_SEVERITY));
        this.setKind(KINDS[(int) (Math.random() * KINDS.length)]);

        if (randomShift < ONGOING_SHIFT) {
            this.setStatus(EventStatus.ONGOING);
        } else {
            this.setStatus(EventStatus.EXPECTED);
        }
    }
}
