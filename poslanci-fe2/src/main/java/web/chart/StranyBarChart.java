package web.chart;

import be.ceau.chart.data.BarData;
import be.ceau.chart.dataset.BarDataset;
import com.syndybat.chartjs.ChartJs;
import com.vaadin.flow.component.html.Div;
import poslanciDB.entity.OrganyEntity;
import poslanciDB.entity.PoslanecEntity;
import web.Colors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static web.SizeUI.wrapToBigDiv;
import static web.chart.Helper.*;
import static web.chart.PoslanecData.*;

public class StranyBarChart {

    public static Div getPartyPocetSlovDiv(Map<OrganyEntity, Set<PoslanecEntity>> map, OrganyEntity obdobi){
        return wrapToBigDiv(getPartyPocetSlov(map, obdobi));
    }

    private static ChartJs getPartyPocetSlov(Map<OrganyEntity, Set<PoslanecEntity>> map, OrganyEntity obdobi) {
        if(map == null || map.isEmpty()) return null;
        List<String> labels = getPartyNames(map.keySet());
        Colors colors = new Colors();

        BarData barData = new BarData();
        barData.setLabels(labels.toArray(new String[0]));
        BarDataset barDataset = new BarDataset().setLabel("Počet slov");

        for(OrganyEntity kandidatka : map.keySet()) {
            Integer sum = 0;
            for(PoslanecEntity poslanecEntity : map.get(kandidatka)) {
                if(poslanecEntity != null && poslanecEntity.getPoslanecStatistikyByIdPoslanec() != null) {
                    if(poslanecEntity.getOrganyByIdObdobi() == obdobi) {
                        sum += poslanecEntity.getPoslanecStatistikyByIdPoslanec().getPocetSlov();
                    }
                }
            }
            barDataset.addData(sum).addBackgroundColor(colors.getColor());
        }

        barData.addDataset(barDataset);
        be.ceau.chart.BarChart barChart = new be.ceau.chart.BarChart(barData,
                getBarOptionsWithBeginZero("Součet počtu slov podle stran")).setVertical();
        ChartJs chart = new ChartJs(barChart.toJson());

        return chart;
    }

    public static Div getPartyAveragePocetSlovDiv(Map<OrganyEntity, Set<PoslanecEntity>> map, OrganyEntity obdobi){
        return wrapToBigDiv(getPartyAveragePocetSlov(map, obdobi));
    }

    private static ChartJs getPartyAveragePocetSlov(Map<OrganyEntity, Set<PoslanecEntity>> map, OrganyEntity obdobi) {
        if(map == null || map.isEmpty()) return null;
        List<String> labels = getPartyNames(map.keySet());
        Colors colors = new Colors();

        BarData barData = new BarData();
        barData.setLabels(labels.toArray(new String[0]));
        BarDataset barDataset = new BarDataset().setLabel("Průměrný počet slov");

        for(OrganyEntity kandidatka : map.keySet()) {
            Integer sum = 0;
            for(PoslanecEntity poslanecEntity : map.get(kandidatka)) {
                if(poslanecEntity != null && poslanecEntity.getPoslanecStatistikyByIdPoslanec() != null) {
                    if(poslanecEntity.getOrganyByIdObdobi() == obdobi) {
                        sum += poslanecEntity.getPoslanecStatistikyByIdPoslanec().getPocetSlov();
                    }
                }
            }
            double pocetSlov = (double)sum / map.get(kandidatka).size();
            barDataset.addData(pocetSlov).addBackgroundColor(colors.getColor());
        }

        barData.addDataset(barDataset);
        be.ceau.chart.BarChart barChart = new be.ceau.chart.BarChart(barData,
                getBarOptionsWithBeginZero("Průměrný počet slov podle stran na jednoho poslance")).setVertical();

        return new ChartJs(barChart.toJson());
    }

    public static Div getPartyMedianPocetSlovDiv(Map<OrganyEntity, Set<PoslanecEntity>> map, OrganyEntity obdobi){
        return wrapToBigDiv(getPartyMedianPocetSlov(map, obdobi));
    }

    private static ChartJs getPartyMedianPocetSlov(Map<OrganyEntity, Set<PoslanecEntity>> map, OrganyEntity obdobi) {
        if(map == null || map.isEmpty()) return null;
        List<String> labels = getPartyNames(map.keySet());

        Colors colors = new Colors();

        BarData barData = new BarData();
        barData.setLabels(labels.toArray(new String[0]));
        BarDataset barDataset = new BarDataset().setLabel("Median počet slov");

        for(OrganyEntity kandidatka : map.keySet()) {
            List<Integer> list = new ArrayList<>();
            for(PoslanecEntity poslanecEntity : map.get(kandidatka)) {
                if(poslanecEntity != null && poslanecEntity.getPoslanecStatistikyByIdPoslanec() != null) {
                    if(poslanecEntity.getOrganyByIdObdobi() == obdobi) {
                        list.add(poslanecEntity.getPoslanecStatistikyByIdPoslanec().getPocetSlov());
                    }
                }
            }
            Integer median = getMedianFromIntegerList(list);
            barDataset.addData(median).addBackgroundColor(colors.getColor());
        }

        barData.addDataset(barDataset);
        be.ceau.chart.BarChart barChart = new be.ceau.chart.BarChart(barData,
                getBarOptionsWithBeginZero("Median počtu slov podle stran na jednoho poslance")).setVertical();

        return new ChartJs(barChart.toJson());
    }

    public static Div getPartyTotalSentimentDiv(Map<OrganyEntity, Set<PoslanecEntity>> map, OrganyEntity obdobi){
        return wrapToBigDiv(getPartyTotalSentiment(map, obdobi));
    }

    private static ChartJs getPartyTotalSentiment(Map<OrganyEntity, Set<PoslanecEntity>> map, OrganyEntity obdobi) {
        if(map == null || map.isEmpty()) return null;
        List<String> labels = getPartyNames(map.keySet());

        Colors colors = new Colors();

        BarData barData = new BarData();
        barData.setLabels(labels.toArray(new String[0]));
        BarDataset barDataset = new BarDataset().setLabel("Celkový sentiment");

        for(OrganyEntity kandidatka : map.keySet()) {
            Integer pocetPosSlov = 0, pocetNegSlov = 0;
            for(PoslanecEntity poslanecEntity : map.get(kandidatka)) {
                if(poslanecEntity != null && poslanecEntity.getPoslanecStatistikyByIdPoslanec() != null) {
                    if(poslanecEntity.getOrganyByIdObdobi() == obdobi) {
                        pocetPosSlov += getPoslanecTotalPocetPosSlov(poslanecEntity);
                        pocetNegSlov += getPoslanecTotalPocetNegSlov(poslanecEntity);
                    }
                }
            }
            int pocetCelkem = pocetPosSlov + pocetNegSlov;
            double sentiment = 0.0;
            if(pocetCelkem > 0) {
                sentiment = ((pocetPosSlov * 1.0) + (pocetNegSlov * (-1.0))) / pocetCelkem;
            }
            barDataset.addData(sentiment).addBackgroundColor(colors.getColor());
        }

        barData.addDataset(barDataset);
        be.ceau.chart.BarChart barChart = new be.ceau.chart.BarChart(barData,
                getBarOptions("Celkový sentiment stran" + System.lineSeparator()
                        + "(vypočítaný sentiment z projevů všech poslanců dané strany)")).setVertical();

        return new ChartJs(barChart.toJson());
    }

    public static Div getPartyAverageSentimentDiv(Map<OrganyEntity, Set<PoslanecEntity>> map, OrganyEntity obdobi){
        return wrapToBigDiv(getPartyAverageSentiment(map, obdobi));
    }

    private static ChartJs getPartyAverageSentiment(Map<OrganyEntity, Set<PoslanecEntity>> map, OrganyEntity obdobi) {
        if(map == null || map.isEmpty()) return null;
        List<String> labels = getPartyNames(map.keySet());

        Colors colors = new Colors();

        BarData barData = new BarData();
        barData.setLabels(labels.toArray(new String[0]));
        BarDataset barDataset = new BarDataset().setLabel("Průměrný sentiment");

        for(OrganyEntity kandidatka : map.keySet()) {
            Double sum = 0.0;
            for(PoslanecEntity poslanecEntity : map.get(kandidatka)) {
                if(poslanecEntity != null && poslanecEntity.getPoslanecStatistikyByIdPoslanec() != null) {
                    if(poslanecEntity.getOrganyByIdObdobi() == obdobi) {
                        sum += poslanecEntity.getPoslanecStatistikyByIdPoslanec().getSentiment();
                    }
                }
            }
            double sentiment = sum / map.get(kandidatka).size();
            barDataset.addData(sentiment).addBackgroundColor(colors.getColor());
        }

        barData.addDataset(barDataset);
        be.ceau.chart.BarChart barChart = new be.ceau.chart.BarChart(barData,
                getBarOptions("Průměrný sentiment poslance ze strany" + System.lineSeparator()
                        + "(průměr sentimentů všech poslanců dané strany)")).setVertical();

        return new ChartJs(barChart.toJson());
    }

    public static Div getPartyMedianSentimentDiv(Map<OrganyEntity, Set<PoslanecEntity>> map, OrganyEntity obdobi){
        return wrapToBigDiv(getPartyMedianSentiment(map, obdobi));
    }

    private static ChartJs getPartyMedianSentiment(Map<OrganyEntity, Set<PoslanecEntity>> map, OrganyEntity obdobi) {
        if(map == null || map.isEmpty()) return null;
        List<String> labels = getPartyNames(map.keySet());

        Colors colors = new Colors();

        BarData barData = new BarData();
        barData.setLabels(labels.toArray(new String[0]));
        BarDataset barDataset = new BarDataset().setLabel("Median sentimentu");

        for(OrganyEntity kandidatka : map.keySet()) {
            List<Double> list = new ArrayList<>();
            for(PoslanecEntity poslanecEntity : map.get(kandidatka)) {
                if(poslanecEntity != null && poslanecEntity.getPoslanecStatistikyByIdPoslanec() != null) {
                    if(poslanecEntity.getOrganyByIdObdobi() == obdobi) {
                        list.add(poslanecEntity.getPoslanecStatistikyByIdPoslanec().getSentiment());
                    }
                }
            }
            Double median = getMedianFromDoubleList(list);
            barDataset.addData(median).addBackgroundColor(colors.getColor());
        }

        barData.addDataset(barDataset);
        be.ceau.chart.BarChart barChart = new be.ceau.chart.BarChart(barData,
                getBarOptions("Median sentimentu strany")).setVertical();

        return new ChartJs(barChart.toJson());
    }

    private static List<String> getPartyNames(Set<OrganyEntity> keySet) {
        List<String> labels = new ArrayList<>();
        keySet.forEach(party -> labels.add(party.getZkratka()));
        return labels;
    }
}
