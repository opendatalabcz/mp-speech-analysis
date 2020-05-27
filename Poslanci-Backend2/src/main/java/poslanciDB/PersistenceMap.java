package poslanciDB;

import java.util.HashMap;
import java.util.Map;

public final class PersistenceMap {
    private static Map<String, String> properties = new HashMap<>();

    private PersistenceMap() {
    }

    public static void setUrl(String path) {
        String type = "jdbc:sqlite:";
        String name = "hibernate.connection.url";
        String value = type + path;
        properties.put(name, value);
    }

    public static Map<String, String> getMap() {
        return properties;
    }
}
