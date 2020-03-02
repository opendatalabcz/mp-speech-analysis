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
    private Integer idPoslanec;

    public TopSlovaCreator(Integer idPoslanec) {
        this.idPoslanec = idPoslanec;
    }

    public void processSpeech(String speech) {
        List<LemmaWithTag> lemmasList = MorphoditaAnalyzer.analyzeString(speech);
        sortedMapDescending = null;
        lemmasList.forEach(word -> {
            Integer value = unsortedMap.get(word.getLemma());
            if (value == null)
                value = 0;
            value++;
            unsortedMap.put(word.getLemma(), value);
        });
    }

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

        int i = 0;
        for(Map.Entry<String, Integer> word : sortedMapDescending.entrySet()) {
            if(i >= n || i>sortedMapDescending.size()) {
                break;
            }
            topSlovaEntities.add(new TopSlovaEntity(word.getKey(), word.getValue(), i + 1, poslanecStatistikyEntity));
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