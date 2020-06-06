package it.polimi.project14.user;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Caps {
    private static final String CSV_FILE = "provincia_comune_cap.csv";
    private static Map<String, Map<String, Set<String>>> caps = new HashMap<>();

    static {
        final ClassLoader capsLoader = Caps.class.getClassLoader();
        final InputStream csvStream = capsLoader.getResourceAsStream(CSV_FILE);
        if (csvStream != null) {

            final InputStreamReader csvReader = new InputStreamReader(csvStream);
            try (BufferedReader csvBufReader = new BufferedReader(csvReader)) {

                for (String row = csvBufReader.readLine();
                     row != null;
                     row = csvBufReader.readLine()) {

                    final String[] data = row.split(",");
                    final String
                        province = data[0],
                        municipality = data[1],
                        cap = data[2];

                    if (!caps.containsKey(province)) {
                        caps.put(province, new HashMap<>());
                    }

                    if (!caps.get(province).containsKey(municipality)) {
                        caps.get(province).put(municipality, new HashSet<>());
                    }

                    caps.get(province).get(municipality).add(cap);
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
    }

    static public Set<String> filter(final String province, final String municipality) {
        return caps.entrySet().stream()
            .filter(e -> province == null || province.equals(e.getKey()))
            .flatMap(e -> e.getValue().entrySet().stream())
            .filter(e -> municipality == null || municipality.equals(e.getKey()))
            .flatMap(e -> e.getValue().stream())
            .collect(Collectors.toCollection(HashSet::new));
    }

    static public Set<String> getProvinces() {
        return caps.keySet();
    }

    static public Set<String> getMunicipalities(final String province) {
        return caps.entrySet().stream()
            .filter(e -> province == null || province.equals(e.getKey()))
            .flatMap(e -> e.getValue().keySet().stream())
            .collect(Collectors.toCollection(HashSet::new));
    }
}
