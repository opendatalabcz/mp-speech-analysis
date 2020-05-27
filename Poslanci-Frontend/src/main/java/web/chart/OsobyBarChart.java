package web.chart;

import be.ceau.chart.data.BarData;
import be.ceau.chart.dataset.BarDataset;
import com.syndybat.chartjs.ChartJs;
import com.vaadin.flow.component.html.Div;
import poslanciDB.entity.OsobyEntity;
import poslanciDB.entity.PoslanecEntity;
import web.Colors;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import static web.SizeUI.wrapToMediumDiv;
import static web.chart.Helper.*;
import static web.chart.Helper.getDoublesArrayFromList;
public class OsobyBarChart {


    public static Div getOsobyPocetSlovPeriodDiv(Set<OsobyEntity> set) {
        return wrapToMediumDiv(getOsobyPocetSlovPeriod(set, PoslanecData::getPoslanecTotalPocetSlov, "Počet slov celkem"));
    }

    public static Div getOsobyPocetPosSlovPeriodDiv(Set<OsobyEntity> set) {
        return wrapToMediumDiv(getOsobyPocetSlovPeriod(set, PoslanecData::getPoslanecTotalPocetPosSlov, "Počet pozitivních slov"));
    }

    public static Div getOsobyPocetNegSlovPeriodDiv(Set<OsobyEntity> set) {
        return wrapToMediumDiv(getOsobyPocetSlovPeriod(set, PoslanecData::getPoslanecTotalPocetNegSlov, "Počet negativních slov"));
    }

    public static Div getOsobyPocetPosNegSlovPeriodDiv(Set<OsobyEntity> set) {
        return wrapToMediumDiv(getOsobyPocetSlovPeriod(set, PoslanecData::getPoslanecTotalPocetPosNegSlov, "Rozdíl pozitivních a negativních slov"));
    }

    private static ChartJs getOsobyPocetSlovPeriod(Set<OsobyEntity> set, Function<PoslanecEntity, Integer> func, String label) {
        Integer beginPeriod = 1;
        Integer endPeriod = OsobaData.getEndSessonNumber(set);
        List<String> labels = getPeriodLabelList(beginPeriod, endPeriod);
        Colors colors = new Colors();

        BarData barData = new BarData();
        barData.setLabels(labels.toArray(new String[0]));

        for(OsobyEntity osobyEntity : set) {
            List<Integer> ints = new ArrayList<>();
            for(int i = beginPeriod; i <= endPeriod; i++) {
                PoslanecEntity poslanecEntity = OsobaData.getPoslanecByPeriodNumber(osobyEntity, i);
                if(poslanecEntity == null) {
                    ints.add(0);
                } else {
                    ints.add(func.apply(poslanecEntity));
                }
            }
            BarDataset barDataset = getBarDataSet(getIntsArrayFromList(ints), osobyEntity.toString(),
                    colors.getColor());
            barData.addDataset(barDataset);
        }

        be.ceau.chart.BarChart barChart = new be.ceau.chart.BarChart(barData, getBarOptionsWithBeginZero(label)).setVertical();

        return new ChartJs(barChart.toJson());
    }

    public static Div getOsobySentimentPeriodDiv(Set<OsobyEntity> set) {
        return wrapToMediumDiv(getOsobyPeriodSentiment(set));
    }


    private static ChartJs getOsobyPeriodSentiment(Set<OsobyEntity> set) {
        Integer beginPeriod = 1;
        Integer endPeriod = OsobaData.getEndSessonNumber(set);
        List<String> dates = getPeriodLabelList(beginPeriod, endPeriod);
        Colors colors = new Colors();

        BarData barData = new BarData();
        barData.setLabels(dates.toArray(new String[0]));

        //projde vsechny osoby a spocita celkovy sentiment za urcite obdobi
        for(OsobyEntity osobyEntity : set) {
            List<Double> doubles = new ArrayList<>();
            for(int i = beginPeriod; i <= endPeriod; i++) {
                PoslanecEntity poslanecEntity = OsobaData.getPoslanecByPeriodNumber(osobyEntity, i);
                if(poslanecEntity == null || poslanecEntity.getPoslanecStatistikyByIdPoslanec() == null) {
                    doubles.add(0.0);
                } else {
                    doubles.add(poslanecEntity.getPoslanecStatistikyByIdPoslanec().getSentiment());
                }
            }
            BarDataset barDataset = getBarDataSet(getDoublesArrayFromList(doubles), osobyEntity.toString(),
                    colors.getColor());
            barData.addDataset(barDataset);
        }
        be.ceau.chart.BarChart barChart = new be.ceau.chart.BarChart(barData, getBarOptionsWithBeginZero("Sentiment za volební období")).setVertical();

        return new ChartJs(barChart.toJson());
    }
}
