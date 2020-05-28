package it.polimi.project14.common;

import org.junit.Test;
import org.junit.Assert;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SearchFilterTests {
    @Test
    public void testSearchFilter() {
        SearchFilter searchFilter= new SearchFilter();

        String[] caps = { "23423", "88888", "99999" };
        Set<String> capList = new HashSet<>(Arrays.asList(caps));
        searchFilter.setCapList(capList);
        Assert.assertEquals(searchFilter.getCapList(), capList);

        String kind = "Earthquake";
        searchFilter.setKind(kind);
        Assert.assertEquals(searchFilter.getKind(), kind);

        LocalDateTime expectedSince = LocalDateTime.of(2020, 1, 1, 1, 1);
        searchFilter.setExpectedSince(expectedSince);
        Assert.assertEquals(searchFilter.getExpectedSince(), expectedSince);

        LocalDateTime expectedUntil = LocalDateTime.of(2020, 1, 1, 1, 2);
        searchFilter.setExpectedUntil(expectedUntil);
        Assert.assertEquals(searchFilter.getExpectedUntil(), expectedUntil);

        boolean maxSeverity = true;
        searchFilter.setMaxSeverity(maxSeverity);
        Assert.assertEquals(searchFilter.isMaxSeverity(), maxSeverity);
    }

    @Test (expected=IllegalArgumentException.class)
    public void testSetCapSinceException() {
        SearchFilter searchFilter= new SearchFilter();

        LocalDateTime expectedUntil = LocalDateTime.of(2020, 1, 1, 1, 2);
        searchFilter.setExpectedUntil(expectedUntil);
        Assert.assertEquals(searchFilter.getExpectedUntil(), expectedUntil);

        LocalDateTime expectedSince = LocalDateTime.of(2020, 1, 1, 1, 3);
        searchFilter.setExpectedSince(expectedSince);
        Assert.assertEquals(searchFilter.getExpectedSince(), expectedSince);
    }

    @Test (expected=IllegalArgumentException.class)
    public void testSetCapUntilException() {
        SearchFilter searchFilter= new SearchFilter();

        LocalDateTime expectedSince = LocalDateTime.of(2020, 1, 1, 1, 3);
        searchFilter.setExpectedSince(expectedSince);
        Assert.assertEquals(searchFilter.getExpectedSince(), expectedSince);

        LocalDateTime expectedUntil = LocalDateTime.of(2020, 1, 1, 1, 2);
        searchFilter.setExpectedUntil(expectedUntil);
        Assert.assertEquals(searchFilter.getExpectedUntil(), expectedUntil);
    }
}
