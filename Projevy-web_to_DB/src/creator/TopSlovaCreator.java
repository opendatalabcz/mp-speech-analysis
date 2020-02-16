package creator;

import analyzer.Analyzer;
import entity.StatistikyEntity;
import entity.TopSlovaEntity;
import service.TopSlovaEntityService;

import java.util.*;

public class TopSlovaCreator {
    //private static TopSlovaEntityService topSlovaEntityService = new TopSlovaEntityService();
    private Map<String, Integer> unsortedMap = new HashMap<>();
    private Map<String, Integer> sortedMapDescending = null;
    private Integer idPoslanec;

    public TopSlovaCreator(Integer idPoslanec) {
        this.idPoslanec = idPoslanec;
    }

    public void processSpeech(String speech) {
        List<String> lemmasList = Analyzer.analyzeString(speech);
        sortedMapDescending = null;
        lemmasList.forEach(word -> {
            Integer value = unsortedMap.get(word);
            if (value == null)
                value = 0;
            value++;
            unsortedMap.put(word, value);
        });
    }

    public List<TopSlovaEntity> getTopNWords(Integer n, StatistikyEntity statistikyEntity) {
        List<TopSlovaEntity> topSlovaEntities = new ArrayList<>();
        if(sortedMapDescending == null)
            sortMap();

        int i = 0;
        for(Map.Entry<String, Integer> word : sortedMapDescending.entrySet()) {
            if(i >= n || i>sortedMapDescending.size()) {
                break;
            }
            topSlovaEntities.add(new TopSlovaEntity(word.getKey(), word.getValue(), i + 1, statistikyEntity));
            i++;
        }

        return topSlovaEntities;
    }

    private void sortMap() {
        sortedMapDescending = new LinkedHashMap<>();
        unsortedMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> sortedMapDescending.put(x.getKey(), x.getValue()));
    }
}
