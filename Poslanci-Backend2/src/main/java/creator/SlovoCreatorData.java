package creator;

import analyzer.MorphoditaAnalyzer;
import analyzer.SubLexWordSentiment;
import poslanciDB.entity.ProjevEntity;
import poslanciDB.entity.SlovoEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SlovoCreatorData {
    private Map<LemmaWithTag, Integer> lemmas = new HashMap<>();
    private ProjevEntity projevEntity;

    public SlovoCreatorData(ProjevEntity projevEntity) {
        this.projevEntity = projevEntity;
    }

    public void analyze() {
        List<LemmaWithTag> lemmasList;
        lemmasList = MorphoditaAnalyzer.analyzeString(projevEntity.getText());
        if(lemmasList != null) {
            for(LemmaWithTag lemmaWithTag : lemmasList) {
                //pro kazde slovo zvazi, jestli uz ve stejne forme neni v mape a kdyz ano, tak jen zvysi pocet vyskytu
                Integer value = lemmas.get(lemmaWithTag);
                if (value == null)
                    value = 0;
                value++;
                lemmas.put(lemmaWithTag, value);
            }
        }
    }

    public List<SlovoEntity> getSlovoEntities() {
        List<SlovoEntity> slovoEntities = new ArrayList<>();
        for(Map.Entry<LemmaWithTag, Integer> entry : lemmas.entrySet()) {
            LemmaWithTag lemmaWithTag = entry.getKey();

            //filtruje nezname typy slov a interpunkci
            if(!(lemmaWithTag.getTag().startsWith("X") || lemmaWithTag.getTag().startsWith("Z"))) {
                SlovoEntity slovoEntity = new SlovoEntity(lemmaWithTag.getLemma(), lemmaWithTag.getTag(), entry.getValue(),
                        SubLexWordSentiment.getSentimentForWord(lemmaWithTag.getLemma()), projevEntity);
                slovoEntities.add(slovoEntity);
            }
        }
        return slovoEntities;
    }
}
