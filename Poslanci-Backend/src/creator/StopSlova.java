package creator;

import helper.ParseHelper;
import reader.UNLFileReader;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StopSlova {
    private static final StopSlova stopSlova = new StopSlova(); //todo path ke stop slovum
    private static Set<String> set;

    private StopSlova() {
        initialize();
    }

    private void initialize() {
        set = new HashSet<>();
        String path = "resources/stop_slova.unl";
        UNLFileReader UNLFile = new UNLFileReader(path);
        List<String> myList;
        while ((myList = UNLFile.getLineList()) != null) {
            if(myList.size() == 1) {
                set.add(ParseHelper.removeUselessWhitespacesString(myList.get(0)));
            }
        }
    }

    public static StopSlova getInstance() {
        return stopSlova;
    }

    public boolean isStopSlovo(String word) {
        return set.contains(word);
    }

}
