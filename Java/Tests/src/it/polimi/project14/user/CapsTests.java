package it.polimi.project14.user;

import java.util.Set;
import java.util.HashSet;

import org.junit.Assert;
import org.junit.Test;

public class CapsTests {
    @Test
    public void testFilter() {
        Set<String> provinces = Caps.getProvinces();
        for (String province : provinces) {
            Set<String> municipalities = Caps.getMunicipalities(province);

            Set<String> provinceCapsM = new HashSet<>();

            for (String municipality : municipalities) {
                Set<String> municipalityCaps = Caps.filter(province, municipality);
                provinceCapsM.addAll(municipalityCaps);

                Assert.assertTrue(municipalityCaps.size() != 0);
            }

            Set<String> provinceCaps = Caps.filter(province, null);
            Assert.assertEquals(provinceCaps, provinceCapsM);
        }

    }
}
