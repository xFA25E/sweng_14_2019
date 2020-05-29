package it.polimi.project14.server;

import org.junit.Test;
import org.junit.Assert;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import it.polimi.project14.common.EventStatus;
import it.polimi.project14.common.SearchFilter;

public class EventStorageImplTests {
    @Test
    public void testStatusToString() {
        Assert.assertEquals(
            EventStorageImpl.statusToString(EventStatus.EXPECTED),
            "expected"
        );
        Assert.assertEquals(
            EventStorageImpl.statusToString(EventStatus.ONGOING),
            "ongoing"
        );
        Assert.assertEquals(
            EventStorageImpl.statusToString(EventStatus.OCCURED),
            "occured"
        );
        Assert.assertEquals(
            EventStorageImpl.statusToString(EventStatus.CANCELED),
            "canceled"
        );
    }

    @Test
    public void testStringToStatus() {
        Assert.assertEquals(
            EventStorageImpl.stringToStatus("expected"),
            EventStatus.EXPECTED
        );
        Assert.assertEquals(
            EventStorageImpl.stringToStatus("ongoing"),
            EventStatus.ONGOING
        );
        Assert.assertEquals(
            EventStorageImpl.stringToStatus("occured"),
            EventStatus.OCCURED
        );
        Assert.assertEquals(
            EventStorageImpl.stringToStatus("canceled"),
            EventStatus.CANCELED
        );
    }

    @Test (expected=IllegalArgumentException.class)
    public void testStringToStatusException() {
        Assert.assertEquals(EventStorageImpl.stringToStatus("gibberish"), null);
    }

    @Test
    public void testStringToInt() {
        Assert.assertEquals(EventStorageImpl.stringToInt("02345"), 2345);
        Assert.assertEquals(EventStorageImpl.stringToInt("00000"), 0);
        Assert.assertEquals(EventStorageImpl.stringToInt("99999"), 99999);
        Assert.assertEquals(EventStorageImpl.stringToInt("00102"), 102);
    }

    @Test
    public void testGetKind() {
        String kind = "Earthquake";
        SearchFilter searchFilter = new SearchFilter();
        searchFilter.setKind(kind);

        Assert.assertEquals(EventStorageImpl.getKind(searchFilter), kind);

        searchFilter.setKind(null);
        Assert.assertNull(EventStorageImpl.getKind(searchFilter));

        Assert.assertNull(EventStorageImpl.getKind(null));
    }

    @Test
    public void testGetExpectedSince() {
        LocalDateTime expectedSince = LocalDateTime.now();
        SearchFilter searchFilter = new SearchFilter();
        searchFilter.setExpectedSince(expectedSince);

        Assert.assertEquals(EventStorageImpl.getExpectedSince(searchFilter),
                            EventStorageImpl.dateTimeToEpoch(expectedSince));

        searchFilter.setExpectedSince(null);
        Assert.assertNull(EventStorageImpl.getExpectedSince(searchFilter));

        Assert.assertNull(EventStorageImpl.getExpectedSince(null));
    }

    @Test
    public void testGetExpectedUntil() {
        LocalDateTime expectedUntil = LocalDateTime.now();
        SearchFilter searchFilter = new SearchFilter();
        searchFilter.setExpectedUntil(expectedUntil);

        Assert.assertEquals(EventStorageImpl.getExpectedUntil(searchFilter),
                            EventStorageImpl.dateTimeToEpoch(expectedUntil));

        searchFilter.setExpectedUntil(null);
        Assert.assertNull(EventStorageImpl.getExpectedUntil(searchFilter));

        Assert.assertNull(EventStorageImpl.getExpectedUntil(null));
    }

    @Test
    public void testIsMaxSeverity() {
        boolean isMaxSeverity = true;
        SearchFilter searchFilter = new SearchFilter();
        searchFilter.setMaxSeverity(isMaxSeverity);

        Assert.assertTrue(EventStorageImpl.isMaxSeverity(searchFilter));
        Assert.assertFalse(EventStorageImpl.isMaxSeverity(null));
    }
}
