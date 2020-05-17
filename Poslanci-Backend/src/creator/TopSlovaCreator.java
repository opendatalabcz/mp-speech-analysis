package creator;

import poslanciDB.entity.PoslanecStatistikyEntity;
import poslanciDB.entity.SlovoEntity;
import poslanciDB.entity.TopSlovaEntity;

import java.util.*;

import static helper.StringHelper.removePostfix;

public class TopSlovaCreator {
    private Map<String, Integer> unsortedMap = new HashMap<>();
    private Map<String, Integer> sortedMapDescending = null;

    public TopSlovaCreator() { }

    //prida slovo s poctem vyskytu do nesarazene mapy, jestli uz slovo existuje, tak jen pricte pocet vyskytu
    public void addWord(SlovoEntity slovoEntity) {
        Integer value = unsortedMap.get(slovoEntity.getSlovo());
        if (value == null)
            value = 0;
        value += slovoEntity.getPocetVyskytu();
        unsortedMap.put(slovoEntity.getSlovo(), value);
    }

    public List<TopSlovaEntity> getTopNWords(Integer n, PoslanecStatistikyEntity poslanecStatistikyEntity) {
        List<TopSlovaEntity> topSlovaEntities = new ArrayList<>();
        //vytvori serazenou mapu, aby slo vybrat n nejcastecjsich slov
        if(sortedMapDescending == null)
            sortMap();

        StopSlova stopSlova = StopSlova.getInstance();
        int i = 0;
        for(Map.Entry<String, Integer> word : sortedMapDescending.entrySet()) {
            //pridavani slov konci, kdyz se vycerpaji vsechna slova nebo je naplnen pozadovany pocet - promenna n
            if(i >= n || i > sortedMapDescending.size()) {
                break;
            }
            //slovo je ocisteno pro moznost porovnani se seznamem stopslov
            String formatedWord = removePostfix(word.getKey());
            //prida se jen slovo urcite delky (eliminace spojek, citoslovci,...) a slovo, ktere neni v seznau stopslov
            if(!stopSlova.isStopSlovo(formatedWord) && formatedWord.length() > 2) {
                topSlovaEntities.add(new TopSlovaEntity(word.getKey(), word.getValue(), i + 1, poslanecStatistikyEntity));
                i++;
            }
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
