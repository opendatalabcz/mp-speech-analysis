package web.chart;

import be.ceau.chart.color.Color;
import be.ceau.chart.data.BarData;
import be.ceau.chart.dataset.BarDataset;
import be.ceau.chart.options.BarOptions;
import be.ceau.chart.options.Title;
import be.ceau.chart.options.scales.BarScale;
import be.ceau.chart.options.scales.XAxis;
import be.ceau.chart.options.scales.YAxis;
import be.ceau.chart.options.ticks.LinearTicks;
import com.syndybat.chartjs.ChartJs;
import com.vaadin.flow.component.html.Div;
import poslanciDB.entity.OsobyEntity;
import poslanciDB.entity.PoslanecEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import static web.SizeUI.wrapToMediumDiv;
import static web.SizeUI.wrapToSmallDiv;
import static web.chart.Helper.*;
import static web.chart.Helper.getDoublesArrayFromList;
import static web.chart.PoslanecData.getPoslanecTotalSentiment;

public class OsobyBarChart {


    public static Div getOsobyPocetSlovPeriodDiv(Set<OsobyEntity> set) {
        return wrapToMediumDiv(getOsobyPocetSlovPeriod(set, PoslanecData::getPoslanecTotalPocetSlov, "Počet slov celkem"));
    }

    public static Div getOsobyPocetPosSlovPeriodDiv(Set<OsobyEntity> set) {
        return wrapToMediumDiv(getOsobyPocetSlovPeriod(set, PoslanecData::getPoslanecTotalPocetPosSlov, "Počet positivních slov"));
    }

    public static Div getOsobyPocetNegSlovPeriodDiv(Set<OsobyEntity> set) {
        return wrapToMediumDiv(getOsobyPocetSlovPeriod(set, PoslanecData::getPoslanecTotalPocetNegSlov, "Počet negativních slov"));
    }

    public static Div getOsobyPocetPosNegSlovPeriodDiv(Set<OsobyEntity> set) {
        return wrapToMediumDiv(getOsobyPocetSlovPeriod(set, PoslanecData::getPoslanecTotalPocetPosNegSlov, "Rozdíl positivních a negativních slov"));
    }

    private static ChartJs getOsobyPocetSlovPeriod(Set<OsobyEntity> set, Function<PoslanecEntity, Integer> func, String label) {
        Integer beginPeriod = 1;
        Integer endPeriod = OsobaData.getEndSessonNumber(set);
        List<String> dates = getPeriodLabelList(beginPeriod, endPeriod);
        Colors colors = new Colors();

        BarData barData = new BarData();
        barData.setLabels(dates.toArray(new String[0]));

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

        be.ceau.chart.BarChart barChart = new be.ceau.chart.BarChart(barData, getBarOptions(label)).setVertical();

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

        for(OsobyEntity osobyEntity : set) {
            List<Double> doubles = new ArrayList<>();
            for(int i = beginPeriod; i <= endPeriod; i++) {
                PoslanecEntity poslanecEntity = OsobaData.getPoslanecByPeriodNumber(osobyEntity, i);
                if(poslanecEntity == null) {
                    doubles.add(0.0);
                } else {
                    doubles.add(getPoslanecTotalSentiment(poslanecEntity));
                }
            }
            BarDataset barDataset = getBarDataSet(getDoublesArrayFromList(doubles), osobyEntity.toString(),
                    colors.getColor());
            barData.addDataset(barDataset);
        }

        be.ceau.chart.BarChart barChart = new be.ceau.chart.BarChart(barData, getBarOptions("Průměrný sentiment za volební období")).setVertical();

        return new ChartJs(barChart.toJson());
    }

    private static BarOptions getBarOptions(String label) {
        BarScale scale = new BarScale();
        scale.addyAxes(new YAxis<LinearTicks>().setStacked(false));
        scale.addxAxes(new XAxis<LinearTicks>().setStacked(false).setGridLines(getGridLines()));

        BarOptions options = new BarOptions();
        options.setResponsive(true)
                .setScales(scale)
                .setTitle(new Title().setText(label).setDisplay(true).setFontColor(Colors.getChartLabelColor())); //todo popisky a barvy;

        return options;
    }
}
