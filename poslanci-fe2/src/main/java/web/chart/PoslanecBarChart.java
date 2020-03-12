package web.chart;

import be.ceau.chart.color.Color;
import be.ceau.chart.data.BarData;
import be.ceau.chart.dataset.BarDataset;
import be.ceau.chart.javascript.JavaScriptFunction;
import be.ceau.chart.options.BarOptions;
import be.ceau.chart.options.Legend;
import be.ceau.chart.options.Title;
import be.ceau.chart.options.scales.BarScale;
import be.ceau.chart.options.scales.XAxis;
import be.ceau.chart.options.scales.YAxis;
import be.ceau.chart.options.ticks.LinearTicks;
import com.syndybat.chartjs.ChartJs;
import com.syndybat.chartjs.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import poslanciDB.entity.PoslanecEntity;

import java.util.ArrayList;
import java.util.List;

import static web.SizeUI.wrapToBigDiv;
import static web.chart.Helper.*;
import static web.chart.PoslanecData.*;

public class PoslanecBarChart {

    public static Div getPoslanecSentimentMesicDiv(PoslanecEntity poslanecEntity){
        List<PoslanecEntity> list = new ArrayList<>();
        list.add(poslanecEntity);
        return wrapToBigDiv(getPoslanecSentimentMesic(list));
    }

    public static Div getPoslanecSentimentMesicDiv(List<PoslanecEntity> list){
        return wrapToBigDiv(getPoslanecSentimentMesic(list));
    }

    private static ChartJs getPoslanecSentimentMesic(List<PoslanecEntity> listPoslanecEntity)
    {
        if(listPoslanecEntity.isEmpty()) return null;
        if(listPoslanecEntity.get(0) == null) return null;
        List<String> dates = getMonthLabelsList(listPoslanecEntity.get(0));

        BarScale scale = new BarScale()
                .addxAxes(new XAxis<LinearTicks>().setStacked(false))
                .addyAxes(new YAxis<LinearTicks>().setStacked(false));

        BarOptions options = new BarOptions();
        options.setResponsive(true).setScales(scale);

        Colors colors = new Colors();

        BarData barData = new BarData();
        barData.setLabels(dates.toArray(new String[0]));
        for(PoslanecEntity poslanecEntity : listPoslanecEntity) {
            if(poslanecEntity != null) {
                BarDataset barDataset = getBarDataSet(getPoslanecDoublesSentimentArray(poslanecEntity),poslanecEntity.toString(),
                        colors.getColor());
                barData.addDataset(barDataset);
            }
        }

        be.ceau.chart.BarChart barChart = new be.ceau.chart.BarChart(barData, options).setVertical();


        ChartJs chart = new ChartJs(barChart.toJson());

        chart.addClickListener(new ComponentEventListener<ClickEvent>()
        {
            @Override
            public void onComponentEvent(ClickEvent clickEvent)
            {
                Notification.show(String.format("%s : %s : %s", clickEvent.getLabel(), clickEvent.getDatasetLabel(), clickEvent.getValue()),
                        3000, Notification.Position.TOP_CENTER);
            }
        });

        return chart;
    }





    public static Div getTest0Div(){
        return wrapToBigDiv(getTest0());
    }

    private static ChartJs getTest0(){
        BarDataset dataset = new BarDataset()
                .setLabel("sample chart")
                .setData(65, 59, 80, 81, 56, 55, 40)
                .addBackgroundColors(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.ORANGE, Color.GRAY, Color.BLACK)
                .setBorderWidth(2);

        BarData data = new BarData()
                .addLabels("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
                .addDataset(dataset);

        JavaScriptFunction label = new JavaScriptFunction(
                "\"function(chart) {console.log('test legend');}\""
        );

        BarOptions barOptions = new BarOptions()
                .setResponsive(true)
                .setTitle(new Title().setText("test"))
                .setLegend(new Legend().setDisplay(true)
                        .setOnClick(label));

        return new ChartJs(new be.ceau.chart.BarChart(data,barOptions).toJson());
    }
}
