package it.polimi.project14.user;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Caps {
    static HashMap<String, HashMap<String, HashSet<String>>> caps;

    static {
        try (BufferedReader csvReader = new BufferedReader(new FileReader("data/provincia_comune_cap.csv"))) {

            String row;
            caps = new HashMap<>();
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",");

                String province = data[0], municipality = data[1], cap = data[2];

                if (!caps.containsKey(province)) {
                    caps.put(province, new HashMap<>());
                }

                if (!caps.get(province).containsKey(municipality)) {
                    caps.get(province).put(municipality, new HashSet<>());
                }

                caps.get(province).get(municipality).add(cap);
            }
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static public Set<String> narrow(String province, String municipality) {
        Set<String> capList = new HashSet<String>();
        capList = caps.entrySet().stream()
                      .filter(
                          e -> province == null || province.equals(e.getKey())
                      )
                      .flatMap(e -> e.getValue().entrySet().stream())
                      .filter(
                          e -> municipality == null || municipality.equals(e.getKey())
                      )
                      .flatMap(e -> e.getValue().stream())
                      .collect(Collectors.toCollection(HashSet::new));

        return capList;
    }

    static public Set<String> getProvinces() {
        return caps.keySet();
    }

    static public Set<String> getMunicipality(String province) {
        return caps.entrySet().stream()
                   .filter(e -> province == null || province.equals(e.getKey()))
                   .flatMap(e -> e.getValue().keySet().stream())
                   .collect(Collectors.toCollection(HashSet::new));
    }
}