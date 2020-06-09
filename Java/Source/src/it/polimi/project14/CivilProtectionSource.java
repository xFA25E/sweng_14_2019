package it.polimi.project14;

import java.util.HashSet;
import java.util.Set;

import it.polimi.project14.common.Event;
import it.polimi.project14.source.Source;
import it.polimi.project14.source.TimedForecast;
import it.polimi.project14.source.RandomEvent;

public class CivilProtectionSource {

    public static void main(String[] args) {
        System.setProperty("java.security.policy", "policy.all");
        Set<Event> eventList = new HashSet<Event>();
        for (int i = 0; i < 10; i++) {
            Event randomEvent = RandomEvent.generate();
            eventList.add(randomEvent);
        }
        TimedForecast timedForecast = new TimedForecast();
        Source source = new Source(timedForecast);
        System.out.println("Source is running...");
    }
}
