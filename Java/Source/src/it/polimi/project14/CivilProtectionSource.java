package it.polimi.project14;

import java.util.HashSet;
import java.util.Set;

import it.polimi.project14.common.Event;
import it.polimi.project14.source.Source;
import it.polimi.project14.source.RandomEvent;

public class CivilProtectionSource {

    public static void main(String[] args) {
        System.setProperty("java.security.policy", "policy.all");
        Set<Event> eventList = new HashSet<Event>();
        for (int i = 0; i < 10; i++) {
            Event randomEvent = RandomEvent.generate();
            eventList.add(randomEvent);
        }
        Source source = new Source();
        try {
          source.sendForecasts(eventList);
          System.out.println("10 random events genereted");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
