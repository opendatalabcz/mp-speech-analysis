package creator;

import helper.ParseHelper;
import helper.StringHelper;
import helper.Timer;
import poslanciDB.entity.OsobyEntity;
import reader.AbstractUNLFileReader;

import java.util.ArrayList;
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
        AbstractUNLFileReader UNLFile = new AbstractUNLFileReader(path);
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
