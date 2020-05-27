package web.chart;

import be.ceau.chart.color.Color;
import be.ceau.chart.data.BarData;
import be.ceau.chart.dataset.BarDataset;
import be.ceau.chart.javascript.JavaScriptFunction;
import be.ceau.chart.options.BarOptions;
import be.ceau.chart.options.Legend;
import be.ceau.chart.options.Title;
import com.syndybat.chartjs.ChartJs;
import com.vaadin.flow.component.html.Div;
import poslanciDB.entity.PoslanecEntity;
import web.Colors;

import java.util.List;

import static web.SizeUI.wrapToBigDiv;
import static web.chart.Helper.getBarDataSet;
import static web.chart.Helper.getBarOptions;
import static web.chart.PoslanecData.*;

public class PoslanecBarChart {

    public static Div getPoslanecSentimentMesicDiv(List<PoslanecEntity> list){
        return wrapToBigDiv(getPoslanecSentimentMesic(list));
    }

    public static Div getPoslanecPocetSlovMesicDiv(List<PoslanecEntity> list){
        return wrapToBigDiv(getPoslanecPocetSlovMesic(list));
    }

    private static ChartJs getPoslanecPocetSlovMesic(List<PoslanecEntity> listPoslanecEntity) {
        if(listPoslanecEntity.isEmpty()) return null;
        if(listPoslanecEntity.get(0) == null) return null;
        if(!checkPoslanecStatsExist(listPoslanecEntity)) return null;
        //vytvori popisky k mesicum, v kterych poslanec mel projevy
        List<String> dates = getMonthLabelsList(listPoslanecEntity.get(0));

        Colors colors = new Colors();

        BarData barData = new BarData();
        barData.setLabels(dates.toArray(new String[0]));
        //spocita hodnoty poctu slov za kazdy mesic
        for(PoslanecEntity poslanecEntity : listPoslanecEntity) {
            if(poslanecEntity != null) {
                BarDataset barDataset = getBarDataSet(getPoslanecIntsPocetSlovArray(poslanecEntity),poslanecEntity.toString(),
                        colors.getColor());
                barData.addDataset(barDataset);
            }
        }

        be.ceau.chart.BarChart barChart = new be.ceau.chart.BarChart(barData, getBarOptions("Počet slov v měsících")).setVertical();

        return new ChartJs(barChart.toJson());
    }

    private static ChartJs getPoslanecSentimentMesic(List<PoslanecEntity> listPoslanecEntity)
    {
        if(listPoslanecEntity.isEmpty()) return null;
        if(listPoslanecEntity.get(0) == null) return null;
        if(!checkPoslanecStatsExist(listPoslanecEntity)) return null;
        //vytvori popisky k mesicum, v kterych poslanec mel projevy
        List<String> dates = getMonthLabelsList(listPoslanecEntity.get(0));

        Colors colors = new Colors();

        BarData barData = new BarData();
        barData.setLabels(dates.toArray(new String[0]));
        //spocita hodnoty sentimentu za kazdy mesic
        for(PoslanecEntity poslanecEntity : listPoslanecEntity) {
            if(poslanecEntity != null) {
                BarDataset barDataset = getBarDataSet(getPoslanecDoublesSentimentArray(poslanecEntity),poslanecEntity.toString(),
                        colors.getColor());
                barData.addDataset(barDataset);
            }
        }

        be.ceau.chart.BarChart barChart = new be.ceau.chart.BarChart(barData, getBarOptions("Průměrný sentiment v měsících")).setVertical();

        return new ChartJs(barChart.toJson());
    }

    private static boolean checkPoslanecStatsExist(List<PoslanecEntity> listPoslanecEntity) {
        for(PoslanecEntity poslanecEntity : listPoslanecEntity) {
            if(poslanecEntity.getPoslanecStatistikyByIdPoslanec() != null) return true;
        }
        return false;
    }
}
