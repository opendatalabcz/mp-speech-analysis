package creator;

import analyzer.Analyzer;
import entity.ProjevEntity;
import entity.SlovoEntity;

import java.util.*;

public class SlovoCreatorData {
    private Map<LemmaWithTag, Integer> lemmas = new HashMap<>();
    private ProjevEntity projevEntity;

    public SlovoCreatorData(ProjevEntity projevEntity) {
        this.projevEntity = projevEntity;
    }

    public void analyze() {
        List<LemmaWithTag> lemmasList;
        lemmasList = Analyzer.analyzeString(projevEntity.getText());
        if(lemmasList != null) {
            for(LemmaWithTag lemmaWithTag : lemmasList) {
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
            SlovoEntity slovoEntity = new SlovoEntity(lemmaWithTag.getLemma(), lemmaWithTag.getTag(), entry.getValue(),
                    processSentiment(lemmaWithTag.getLemma()), projevEntity);
            slovoEntities.add(slovoEntity);
        }
        return slovoEntities;
    }

    private Integer processSentiment(String word) {
        return 0;
    }
}
