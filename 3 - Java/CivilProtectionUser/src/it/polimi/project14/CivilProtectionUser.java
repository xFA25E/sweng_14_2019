package it.polimi.project14;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import it.polimi.project14.common.EventStorage;
import it.polimi.project14.common.Event;

public class CivilProtectionUser {
    public static void main(String[] args) {
        try {
            // // Test
            // System.setProperty("java.security.policy", "policy.all");
            // String urlServer = "rmi://localhost:6666/";
            // EventStorage eventStorage = (EventStorage) Naming.lookup(urlServer + "EVENT_STORAGE");
            // System.out.println("testing...");
            // Event e = new Event();
            // e.setEventId(3);
            // e.setCap("91026");
            // e.setMessage("Test");
            // e.setKind("Terremoto");
            // e.setExpectedAt(LocalDateTime.now());
            // e.setSeverity(10);
            // e.setSourceId(3);
            // e.setStatus(EventStatus.OCCURED);
            // System.out.println("testing event created");
            // Set<Event> eventsList = new HashSet<Event>();
            // eventsList.add(e);
            // eventStorage.storeEvents(eventsList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}