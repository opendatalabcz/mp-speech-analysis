package web.chart;

import be.ceau.chart.color.Color;
import be.ceau.chart.data.BarData;
import be.ceau.chart.dataset.BarDataset;
import com.syndybat.chartjs.ChartJs;
import com.vaadin.flow.component.html.Div;
import poslanciDB.entity.OrganyEntity;
import poslanciDB.entity.PoslanecEntity;
import poslanciDB.entity.PoslanecStatistikyZminkyEntity;
import web.Colors;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static web.Helper.getStranySetInObdobi;
import static web.SizeUI.wrapToBigDiv;
import static web.SizeUI.wrapToMediumDiv;
import static web.chart.Helper.*;

public class ZminkyBarChart {
    public static Div getStranaZminkyStranyDiv(OrganyEntity obdobi, OrganyEntity strana){
        return wrapToMediumDiv(getStranaZminkyStrany(obdobi, strana));
    }

    public static Div getStranaZminkyStranyDividedByPoslanecCountDiv(OrganyEntity obdobi, OrganyEntity strana){
        return wrapToMediumDiv(getStranaZminkyStranyDividedByPoslanecCount(obdobi, strana));
    }

    public static Div getPoslanecZminkyStranyDiv(PoslanecEntity poslanecEntity){
        return wrapToMediumDiv(getPoslanecZminkyStrany(poslanecEntity));
    }

    public static Div getPoslanecZminkyStranyDivideByPoslanecCountDiv(PoslanecEntity poslanecEntity){
        return wrapToMediumDiv(getPoslanecZminkyStranyDivideByPoslanecCount(poslanecEntity));
    }

    private static ChartJs getStranaZminkyStrany(OrganyEntity obdobi, OrganyEntity strana) {
        if(strana == null || obdobi == null) return null;
        Set<OrganyEntity> stranyObdobi = getStranySetInObdobi(obdobi);
        Map<OrganyEntity, Integer> mapZminky = new HashMap<>();
        for(OrganyEntity party : stranyObdobi) {
            mapZminky.put(party, 0);
        }

        for(PoslanecEntity poslanecEntity : strana.getPoslanecsKandidatkaByIdOrgan()) {
            if(poslanecEntity.getOrganyByIdObdobi() == obdobi) {
                if(poslanecEntity.getPoslanecStatistikyByIdPoslanec() != null) {
                    for(PoslanecStatistikyZminkyEntity zminky
                            : poslanecEntity.getPoslanecStatistikyByIdPoslanec().getPoslanecStatistikyZminkiesByIdPoslanec()) {
                        OrganyEntity party = zminky.getPoslanecByIdPoslanecZmineny().getOrganyByIdKandidatka();
                        try {
                            Integer pocet = mapZminky.get(party);
                            pocet += zminky.getPocetVyskytu();
                            mapZminky.replace(party, pocet);
                        } catch (Exception ignored) {
                        }
                    }
                }
            }
        }

        List<String> labels = getPartyNames(mapZminky.keySet());
        Colors colors = new Colors();

        BarData barData = new BarData();
        barData.setLabels(labels.toArray(new String[0]));
        BarDataset barDataset = new BarDataset().setLabel("Počet zmínek");

        for(Map.Entry<OrganyEntity, Integer> entry : mapZminky.entrySet()) {
            barDataset.addData(entry.getValue()).addBackgroundColor(colors.getColor());
        }

        barData.addDataset(barDataset);
        be.ceau.chart.BarChart barChart = new be.ceau.chart.BarChart(barData,
                getBarOptionsWithBeginZero("Poslanci strany " + strana.getZkratka() + " zmiňují poslance stran")).setVertical();

        return new ChartJs(barChart.toJson());
    }

    private static ChartJs getStranaZminkyStranyDividedByPoslanecCount(OrganyEntity obdobi, OrganyEntity strana) {
        if(strana == null || obdobi == null) return null;
        Set<OrganyEntity> stranyObdobi = getStranySetInObdobi(obdobi);
        Map<OrganyEntity, Integer> mapZminky = new HashMap<>();
        for(OrganyEntity party : stranyObdobi) {
            mapZminky.put(party, 0);
        }

        for(PoslanecEntity poslanecEntity : strana.getPoslanecsKandidatkaByIdOrgan()) {
            if(poslanecEntity.getOrganyByIdObdobi() == obdobi) {
                if(poslanecEntity.getPoslanecStatistikyByIdPoslanec() != null) {
                    for (PoslanecStatistikyZminkyEntity zminky
                            : poslanecEntity.getPoslanecStatistikyByIdPoslanec().getPoslanecStatistikyZminkiesByIdPoslanec()) {
                        OrganyEntity party = zminky.getPoslanecByIdPoslanecZmineny().getOrganyByIdKandidatka();
                        try {
                            Integer pocet = mapZminky.get(party);
                            pocet += zminky.getPocetVyskytu();
                            mapZminky.replace(party, pocet);
                        } catch (Exception ignored) {
                        }
                    }
                }
            }
        }

        List<String> labels = getPartyNames(mapZminky.keySet());
        Colors colors = new Colors();

        BarData barData = new BarData();
        barData.setLabels(labels.toArray(new String[0]));
        BarDataset barDataset = new BarDataset().setLabel("Počet zmínek");

        for(Map.Entry<OrganyEntity, Integer> entry : mapZminky.entrySet()) {
            Integer count = getPoslanciInStranaCount(obdobi, entry.getKey());
            barDataset.addData((double)entry.getValue() / count).addBackgroundColor(colors.getColor());
        }

        barData.addDataset(barDataset);
        be.ceau.chart.BarChart barChart = new be.ceau.chart.BarChart(barData,
                getBarOptionsWithBeginZero("Poslanci strany " + strana.getZkratka() + " zmiňují poslance stran (děleno počtem poslanců dané strany)"))
                .setVertical();

        return new ChartJs(barChart.toJson());
    }

    private static ChartJs getPoslanecZminkyStrany(PoslanecEntity poslanecEntity) {
        if(poslanecEntity == null) return null;
        OrganyEntity obdobi = poslanecEntity.getOrganyByIdObdobi();
        if(obdobi == null) return null;
        Set<OrganyEntity> stranyObdobi = getStranySetInObdobi(obdobi);
        Map<OrganyEntity, Integer> mapZminky = new HashMap<>();
        for(OrganyEntity party : stranyObdobi) {
            mapZminky.put(party, 0);
        }

        if(poslanecEntity.getPoslanecStatistikyByIdPoslanec() != null) {
            for (PoslanecStatistikyZminkyEntity zminky
                    : poslanecEntity.getPoslanecStatistikyByIdPoslanec().getPoslanecStatistikyZminkiesByIdPoslanec()) {
                OrganyEntity party = zminky.getPoslanecByIdPoslanecZmineny().getOrganyByIdKandidatka();
                try {
                    Integer pocet = mapZminky.get(party);
                    pocet += zminky.getPocetVyskytu();
                    mapZminky.replace(party, pocet);
                } catch (Exception ignored) {
                }
            }
        }

        List<String> labels = getPartyNames(mapZminky.keySet());
        Colors colors = new Colors();

        BarData barData = new BarData();
        barData.setLabels(labels.toArray(new String[0]));
        BarDataset barDataset = new BarDataset().setLabel("Počet zmínek");

        for(Map.Entry<OrganyEntity, Integer> entry : mapZminky.entrySet()) {
            barDataset.addData(entry.getValue()).addBackgroundColor(colors.getColor());
        }

        barData.addDataset(barDataset);
        be.ceau.chart.BarChart barChart = new be.ceau.chart.BarChart(barData,
                getBarOptionsWithBeginZero("Poslanec " + poslanecEntity + " zmiňuje poslance stran"))
                .setVertical();

        return new ChartJs(barChart.toJson());
    }


    private static ChartJs getPoslanecZminkyStranyDivideByPoslanecCount(PoslanecEntity poslanecEntity) {
        if(poslanecEntity == null) return null;
        OrganyEntity obdobi = poslanecEntity.getOrganyByIdObdobi();
        if(obdobi == null) return null;
        Set<OrganyEntity> stranyObdobi = getStranySetInObdobi(obdobi);
        Map<OrganyEntity, Integer> mapZminky = new HashMap<>();
        for(OrganyEntity party : stranyObdobi) {
            mapZminky.put(party, 0);
        }

        if(poslanecEntity.getPoslanecStatistikyByIdPoslanec() != null) {
            for (PoslanecStatistikyZminkyEntity zminky
                    : poslanecEntity.getPoslanecStatistikyByIdPoslanec().getPoslanecStatistikyZminkiesByIdPoslanec()) {
                OrganyEntity party = zminky.getPoslanecByIdPoslanecZmineny().getOrganyByIdKandidatka();
                try {
                    Integer pocet = mapZminky.get(party);
                    pocet += zminky.getPocetVyskytu();
                    mapZminky.replace(party, pocet);
                } catch (Exception ignored) {
                }
            }
        }

        List<String> labels = getPartyNames(mapZminky.keySet());
        Colors colors = new Colors();

        BarData barData = new BarData();
        barData.setLabels(labels.toArray(new String[0]));
        BarDataset barDataset = new BarDataset().setLabel("Počet zmínek");

        for(Map.Entry<OrganyEntity, Integer> entry : mapZminky.entrySet()) {
            Integer count = getPoslanciInStranaCount(obdobi, entry.getKey());
            barDataset.addData((double)entry.getValue() / count).addBackgroundColor(colors.getColor());
        }

        barData.addDataset(barDataset);
        be.ceau.chart.BarChart barChart = new be.ceau.chart.BarChart(barData,
                getBarOptionsWithBeginZero("Poslanec " + poslanecEntity + " zmiňuje poslance stran (děleno počtem poslanců dané strany)"))
                .setVertical();

        return new ChartJs(barChart.toJson());
    }

}
