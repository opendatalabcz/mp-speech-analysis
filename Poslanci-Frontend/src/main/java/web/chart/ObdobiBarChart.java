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

import static web.SizeUI.wrapToBigDiv;
import static web.chart.Helper.*;
import static web.chart.PoslanecData.getPoslanecTotalPocetNegSlov;
import static web.chart.PoslanecData.getPoslanecTotalPocetPosSlov;

public class ObdobiBarChart {
    public static Div getObdobiPocetSlovDiv(List<OrganyEntity> list) {
        return wrapToBigDiv(getObdobiPocetSlov(list));
    }

    private static ChartJs getObdobiPocetSlov(List<OrganyEntity> list) {
        if(list.isEmpty()) return null;
        List<String> labels = getObdobiLabels(list);
        Colors colors = new Colors();

        BarData barData = new BarData();
        barData.setLabels(labels.toArray(new String[0]));
        BarDataset barDataset = new BarDataset().setLabel("Počet slov");

        //projde poslance kazdeho obdobi a secte jejich pocty slov k jednotlivym obdobim
        for(OrganyEntity obdobi : list) {
            Integer sum = 0;
            for(PoslanecEntity poslanecEntity : obdobi.getPoslanecsObdobiByIdOrgan()) {
                if(poslanecEntity.getPoslanecStatistikyByIdPoslanec() != null) {
                    sum += poslanecEntity.getPoslanecStatistikyByIdPoslanec().getPocetSlov();
                }
            }
            barDataset.addData(sum).addBackgroundColor(colors.getColor());
        }

        barData.addDataset(barDataset);
        be.ceau.chart.BarChart barChart = new be.ceau.chart.BarChart(barData,
                getBarOptionsWithBeginZero("Celkový počet slov ve volebním období")).setVertical();

        return new ChartJs(barChart.toJson());
    }

    public static Div getObdobiAveragePocetSlovDiv(List<OrganyEntity> list) {
        return wrapToBigDiv(getObdobiAveragePocetSlov(list));
    }

    private static ChartJs getObdobiAveragePocetSlov(List<OrganyEntity> list) {
        if(list.isEmpty()) return null;
        List<String> labels = getObdobiLabels(list);
        Colors colors = new Colors();

        BarData barData = new BarData();
        barData.setLabels(labels.toArray(new String[0]));
        BarDataset barDataset = new BarDataset().setLabel("Průměrný počet slov");

        //pro kazde obdobi se spocita prumerny pocet slov na jednoho poslance
        for(OrganyEntity obdobi : list) {
            Double average = web.Helper.getAveragePocetSlov(obdobi.getPoslanecsObdobiByIdOrgan());
            barDataset.addData(average).addBackgroundColor(colors.getColor());
        }

        barData.addDataset(barDataset);
        be.ceau.chart.BarChart barChart = new be.ceau.chart.BarChart(barData,
                getBarOptionsWithBeginZero("Průměrný počet slov poslance ve volebním období")).setVertical();

        return new ChartJs(barChart.toJson());
    }

    public static Div getObdobiMedianPocetSlovDiv(List<OrganyEntity> list) {
        return wrapToBigDiv(getObdobiMedianPocetSlov(list));
    }

    private static ChartJs getObdobiMedianPocetSlov(List<OrganyEntity> list) {
        if(list.isEmpty()) return null;
        List<String> labels = getObdobiLabels(list);
        Colors colors = new Colors();

        BarData barData = new BarData();
        barData.setLabels(labels.toArray(new String[0]));
        BarDataset barDataset = new BarDataset().setLabel("Median počtu slov");

        //pro kazde obdobi se spocita median poctu slov na jednoho poslance
        for(OrganyEntity obdobi : list) {
            Integer median = web.Helper.getMedianPocetSlov(obdobi.getPoslanecsObdobiByIdOrgan());
            barDataset.addData(median).addBackgroundColor(colors.getColor());
        }

        barData.addDataset(barDataset);
        be.ceau.chart.BarChart barChart = new be.ceau.chart.BarChart(barData,
                getBarOptionsWithBeginZero("Median počtu slov poslance ve volebním období")).setVertical();

        return new ChartJs(barChart.toJson());
    }

    public static Div getObdobiTotalSentimentDiv(List<OrganyEntity> list) {
        return wrapToBigDiv(getObdobiTotalSentiment(list));
    }

    private static ChartJs getObdobiTotalSentiment(List<OrganyEntity> list) {
        if(list.isEmpty()) return null;
        List<String> labels = getObdobiLabels(list);
        Colors colors = new Colors();

        BarData barData = new BarData();
        barData.setLabels(labels.toArray(new String[0]));
        BarDataset barDataset = new BarDataset().setLabel("Celkový sentiment");

        //vypocet sentimentu ze vsech projevu daneho obdobi
        for(OrganyEntity obdobi : list) {
            Integer pocetPosSlov = 0, pocetNegSlov = 0;
            for(PoslanecEntity poslanecEntity : obdobi.getPoslanecsObdobiByIdOrgan()) {
                if(poslanecEntity.getPoslanecStatistikyByIdPoslanec() != null) {
                    pocetPosSlov += getPoslanecTotalPocetPosSlov(poslanecEntity);
                    pocetNegSlov += getPoslanecTotalPocetNegSlov(poslanecEntity);
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
                getBarOptions("Celkový sentiment volebního období (vypočítaný sentiment z projevů všech poslanců daného období)")).setVertical();

        return new ChartJs(barChart.toJson());
    }

    public static Div getObdobiAverageSentimentDiv(List<OrganyEntity> list) {
        return wrapToBigDiv(getObdobiAverageSentiment(list));
    }

    private static ChartJs getObdobiAverageSentiment(List<OrganyEntity> list) {
        if(list.isEmpty()) return null;
        List<String> labels = getObdobiLabels(list);
        Colors colors = new Colors();

        BarData barData = new BarData();
        barData.setLabels(labels.toArray(new String[0]));
        BarDataset barDataset = new BarDataset().setLabel("Průměrný sentiment");

        //vypocet prumerneho sentimentu poslance v urcitem obdobi
        for(OrganyEntity obdobi : list) {
            Double average = web.Helper.getAverageSentiment(obdobi.getPoslanecsObdobiByIdOrgan()); // sentiment / obdobi.getPoslanecsObdobiByIdOrgan().size();
            barDataset.addData(average).addBackgroundColor(colors.getColor());
        }

        barData.addDataset(barDataset);
        be.ceau.chart.BarChart barChart = new be.ceau.chart.BarChart(barData,
                getBarOptions("Průměrný sentiment poslance ve volebním období (průměr sentimentů všech poslanců z období)")).setVertical();

        return new ChartJs(barChart.toJson());
    }

    public static Div getObdobiMedianSentimentDiv(List<OrganyEntity> list) {
        return wrapToBigDiv(getObdobiMedianSentiment(list));
    }

    private static ChartJs getObdobiMedianSentiment(List<OrganyEntity> list) {
        if(list.isEmpty()) return null;
        List<String> labels = getObdobiLabels(list);
        Colors colors = new Colors();

        BarData barData = new BarData();
        barData.setLabels(labels.toArray(new String[0]));
        BarDataset barDataset = new BarDataset().setLabel("Median sentimentu");

        //vypocet medianu sentimentu poslancu v jednom obdobi
        for(OrganyEntity obdobi : list) {
            Double median = web.Helper.getMedianSentiment(obdobi.getPoslanecsObdobiByIdOrgan()); //web.Helper.getMedianFromDoubleList(listSentiment);
            barDataset.addData(median).addBackgroundColor(colors.getColor());
        }

        barData.addDataset(barDataset);
        be.ceau.chart.BarChart barChart = new be.ceau.chart.BarChart(barData,
                getBarOptions("Median sentimentu poslance ve volebním období")).setVertical();

        return new ChartJs(barChart.toJson());
    }

    private static List<String> getObdobiLabels(List<OrganyEntity> list) {
        List<String> labels = new ArrayList<>();
        list.forEach(obdobi -> labels.add(obdobi.toString()));
        return labels;
    }
}
