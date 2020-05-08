package web;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import poslanciDB.entity.OrganyEntity;
import poslanciDB.entity.PoslanecEntity;

import java.text.DecimalFormat;
import java.util.*;

public class Helper {
    public static HorizontalLayout getValueWithLabelComponent(String label, String value) {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        Label labelLabel = new Label(label);
        Label valueLabel = new Label(value);
        horizontalLayout.add(labelLabel, valueLabel);
        return horizontalLayout;
    }

    public static String getShortenString(String s, int limit) {
        if(limit < 0) limit = 10;
        if(limit > s.length())
            return s;
        else
            s = s.substring(0, limit) + "...";
        return s;
    }

    public static String getRoundDouble(Double num) {
        DecimalFormat df3 = new DecimalFormat("#.###");
        return df3.format(num);
    }

    public static Integer getAveragePocetSlov(Collection<PoslanecEntity> poslanecEntities) {
        Integer count = 0;
        for(PoslanecEntity poslanecEntity : poslanecEntities) {
            if(poslanecEntity.getPoslanecStatistikyByIdPoslanec() != null) {
                count += poslanecEntity.getPoslanecStatistikyByIdPoslanec().getPocetSlov();
            }
        }
        if(poslanecEntities.size() > 0) {
            return count / poslanecEntities.size();
        }
        return 0;
    }

    public static String getAverageSentimentRound(Collection<PoslanecEntity> poslanecEntities) {
        return getRoundDouble(getAverageSentiment(poslanecEntities));
    }

    public static Double getAverageSentiment(Collection<PoslanecEntity> poslanecEntities) {
        Double sentiment = 0.0;
        for(PoslanecEntity poslanecEntity : poslanecEntities) {
            if(poslanecEntity.getPoslanecStatistikyByIdPoslanec() != null) {
                sentiment += poslanecEntity.getPoslanecStatistikyByIdPoslanec().getSentiment();
            }
        }
        if(poslanecEntities.size() > 0) {
            return sentiment / poslanecEntities.size();
        }
        return 0.0;
    }

    public static Map<OrganyEntity, Set<PoslanecEntity>> getStranyPoslanecMapInObdobi(OrganyEntity obdobi) {
        if(obdobi == null) return null;
        Map<OrganyEntity, Set<PoslanecEntity>> map = new HashMap<>();
        for(PoslanecEntity poslanecEntity : obdobi.getPoslanecsObdobiByIdOrgan()) {
            if(!map.containsKey(poslanecEntity.getOrganyByIdKandidatka())) {
                Set<PoslanecEntity> set = new HashSet<>();
                set.add(poslanecEntity);
                map.put(poslanecEntity.getOrganyByIdKandidatka(), set);
            } else {
                Set<PoslanecEntity> set = map.get(poslanecEntity.getOrganyByIdKandidatka());
                set.add(poslanecEntity);
                map.put(poslanecEntity.getOrganyByIdKandidatka(), set);
            }
        }
        return map;
    }

    public static Set<OrganyEntity> getStranySetInObdobi(OrganyEntity obdobi) {
        if(obdobi == null) return null;
        Set<OrganyEntity> set = new HashSet<>();
        for(PoslanecEntity poslanecEntity : obdobi.getPoslanecsObdobiByIdOrgan()) {
            set.add(poslanecEntity.getOrganyByIdKandidatka());
        }
        return set;
    }

    public static List<PoslanecEntity> getPoslanciWithSamePrijmeniInObdobi(PoslanecEntity poslanecEntity) {
        List<PoslanecEntity> retList = new ArrayList<>();
        Collection<PoslanecEntity> poslanecEntities = poslanecEntity.getOrganyByIdObdobi().getPoslanecsObdobiByIdOrgan();
        for(PoslanecEntity posl : poslanecEntities) {
            if(posl.getOsobyByIdOsoba().getPrijmeni().equals(poslanecEntity.getOsobyByIdOsoba().getPrijmeni()) &&
                    posl.getIdPoslanec() != poslanecEntity.getIdPoslanec()) {
                retList.add(posl);
            }
        }
        return retList;
    }

    public static Double countSentiment(Integer pocetPosSlov, Integer pocetNegSlov) {
        if(pocetPosSlov == 0 && pocetNegSlov == 0)
            return 0.0;
        else
            return ((pocetPosSlov * 1.0) + (pocetNegSlov * (-1.0))) / (pocetPosSlov + pocetNegSlov);
    }
}
