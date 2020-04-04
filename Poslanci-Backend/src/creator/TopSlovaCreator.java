package creator;

import analyzer.MorphoditaAnalyzer;
import poslanciDB.entity.PoslanecStatistikyEntity;
import poslanciDB.entity.SlovoEntity;
import poslanciDB.entity.TopSlovaEntity;

import java.util.*;

public class TopSlovaCreator {
    //private static TopSlovaEntityService topSlovaEntityService = new TopSlovaEntityService();
    private Map<String, Integer> unsortedMap = new HashMap<>();
    private Map<String, Integer> sortedMapDescending = null;

    public TopSlovaCreator() { }

    public void addWord(SlovoEntity slovoEntity) {
        Integer value = unsortedMap.get(slovoEntity.getSlovo());
        if (value == null)
            value = 0;
        value += slovoEntity.getPocetVyskytu();
        unsortedMap.put(slovoEntity.getSlovo(), value);
    }

    public List<TopSlovaEntity> getTopNWords(Integer n, PoslanecStatistikyEntity poslanecStatistikyEntity) {
        List<TopSlovaEntity> topSlovaEntities = new ArrayList<>();
        if(sortedMapDescending == null)
            sortMap();

        StopSlova stopSlova = StopSlova.getInstance();
        int i = 0;
        for(Map.Entry<String, Integer> word : sortedMapDescending.entrySet()) {
            if(i >= n || i>sortedMapDescending.size()) {
                break;
            }
            if(!stopSlova.isStopSlovo(formatWord(word.getKey()))) {
                topSlovaEntities.add(new TopSlovaEntity(word.getKey(), word.getValue(), i + 1, poslanecStatistikyEntity));
                i++;
            }
        }

        return topSlovaEntities;
    }

    private String formatWord(String word) {
        String[] parts0 = word.split("-");
        String[] parts1 = parts0[0].split("_");
        String[] parts2 = parts1[0].split("`");
        return parts2[0];
    }

    private void sortMap() {
        sortedMapDescending = new LinkedHashMap<>();
        unsortedMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> sortedMapDescending.put(x.getKey(), x.getValue()));
    }
}
