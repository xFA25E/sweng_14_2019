package it.polimi.project14.user;

import java.util.Set;
import java.util.Arrays;
import java.util.HashSet;

import org.junit.Assert;
import org.junit.Test;

public class UserTests {
    @Test
    public void testFavoriteCaps() {
        Set<String> favCaps = new HashSet<>();
        favCaps.add("12345");
        favCaps.add("67890");
        favCaps.add("09876");
        favCaps.add("54321") ;
        User user = new User(favCaps);
        Assert.assertEquals(favCaps, user.getFavoriteCaps());

        Set<String> newFavCaps = new HashSet<>();
        newFavCaps.add("11111");
        newFavCaps.add("22222");
        newFavCaps.add("33333");
        newFavCaps.add("44444");
        user.setFavotiteCaps(newFavCaps);
        Assert.assertEquals(newFavCaps, user.getFavoriteCaps());
    }
}
