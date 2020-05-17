package web;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import poslanciDB.entity.OrganyEntity;
import poslanciDB.entity.PoslanecEntity;
import poslanciDB.service.OrganyEntityService;

import java.text.DecimalFormat;
import java.util.*;

public class Helper {
    private static OrganyEntityService organyEntityService = new OrganyEntityService();

    public static HorizontalLayout getValueWithLabelComponent(String label, String value) {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        Label labelLabel = new Label(label);
        Label valueLabel = new Label(value);
        horizontalLayout.add(labelLabel, valueLabel);
        return horizontalLayout;
    }

    public static VerticalLayout getValueWithColorfulLabelComponent(String label, String value) {
        VerticalLayout verticalLayout = new VerticalLayout();
        Label labelLabel = new Label(label);
        labelLabel.getElement().getStyle().set("color", Colors.getHighlightColorString()).set("text-decoration", "underline");
        Label valueLabel = new Label();
        valueLabel.getElement().setProperty("innerHTML", value);
        verticalLayout.add(labelLabel, valueLabel);
        return verticalLayout;
    }

    public static String getShortenString(String s, int limit) {
        if(s == null) return null;
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

    public static Double getAveragePocetSlov(Collection<PoslanecEntity> poslanecEntities) {
        Integer count = 0;
        for(PoslanecEntity poslanecEntity : poslanecEntities) {
            if(poslanecEntity.getPoslanecStatistikyByIdPoslanec() != null) {
                count += poslanecEntity.getPoslanecStatistikyByIdPoslanec().getPocetSlov();
            }
        }
        if(poslanecEntities.size() > 0) {
            return (double)count / poslanecEntities.size();
        }
        return 0.0;
    }

    public static String getAveragePocetSlovRound(Collection<PoslanecEntity> poslanecEntities) {
        return getRoundDouble(getAveragePocetSlov(poslanecEntities));
    }

    public static Integer getMedianPocetSlov(Collection<PoslanecEntity> poslanecEntities) {
        List<Integer> list = new ArrayList<>();
        poslanecEntities.forEach(posl -> {
            if(posl.getPoslanecStatistikyByIdPoslanec() != null)
                list.add(posl.getPoslanecStatistikyByIdPoslanec().getPocetSlov());
        });
        return getMedianFromIntegerList(list);
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

    public static String getMedianSentimentRound(Collection<PoslanecEntity> poslanecEntities) {
        return getRoundDouble(getMedianSentiment(poslanecEntities));
    }

    public static Double getMedianSentiment(Collection<PoslanecEntity> poslanecEntities) {
        List<Double> list = new ArrayList<>();
        poslanecEntities.forEach(posl -> {
            if(posl.getPoslanecStatistikyByIdPoslanec() != null)
                list.add(posl.getPoslanecStatistikyByIdPoslanec().getSentiment());
        });
        return getMedianFromDoubleList(list);
    }

    public static Integer getMedianFromIntegerList(List<Integer> list) {
        if(list == null || list.size() == 0) return 0;
        list.sort(Integer::compareTo);
        if(list.size() % 2 == 0) {
            return (list.get(list.size()/2) + list.get(list.size()/2 - 1)) / 2;
        } else {
            return list.get(list.size() / 2);
        }
    }

    public static Double getMedianFromDoubleList(List<Double> list) {
        if(list == null || list.size() == 0) return 0.0;
        list.sort(Double::compareTo);
        if(list.size() % 2 == 0) {
            return (list.get(list.size()/2) + list.get(list.size()/2 - 1)) / 2;
        } else {
            return list.get(list.size() / 2);
        }
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
            if(poslanecEntity.getOrganyByIdKandidatka() != null) {
                set.add(poslanecEntity.getOrganyByIdKandidatka());
            }
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
        if ((pocetPosSlov == 0 && pocetNegSlov == 0) || (pocetPosSlov < 0 || pocetNegSlov < 0)) {
            return 0.0;
        } else {
            return ((pocetPosSlov * 1.0) + (pocetNegSlov * (-1.0))) / (pocetPosSlov + pocetNegSlov);
        }
    }
}
