package it.polimi.project14.common;

import java.lang.Comparable;

public enum EventStatus implements Comparable<EventStatus> {
    ONGOING,
    EXPECTED,
    OCCURED,
    CANCELED;
}
