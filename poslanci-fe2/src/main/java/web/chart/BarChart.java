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
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.theme.material.Material;
import javafx.geometry.Pos;
import poslanciDB.entity.PoslanecEntity;
import poslanciDB.service.PoslanecEntityService;
import web.monthYear.MonthYear;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static web.chart.Helper.wrapToDiv;
import static web.chart.PoslanecData.getMonthLabelsList;
import static web.chart.PoslanecData.getPoslanecDoublesArray;

public class BarChart {
    private static PoslanecEntityService poslanecEntityService = new PoslanecEntityService();

    public static Div getPoslanecSentimentBarChartWrapped(PoslanecEntity poslanecEntity) {
        return wrapToDiv(getPoslanecSentimentBarChart(poslanecEntity));
    }

    private static ChartJs getPoslanecSentimentBarChart(PoslanecEntity poslanecEntity) {
        BarDataset barDataset = getBarDataSet(poslanecEntity, Color.RED);
        List<String> dates = getMonthLabelsList(poslanecEntity);

        BarDataset barDatasetBENDA = getBarDataSet(poslanecEntityService.find(1537), Color.BLUE);

        BarData data = new BarData()
                .addLabels(dates.toArray(new String[0]))
                .addDataset(barDataset)
                .addDataset(barDatasetBENDA);

        LinearTicks ticks = new LinearTicks()
                .setAutoSkip(false)
                .setMin(0)
                .setMax(1)
                .setStepSize(1);
        XAxis axis = new XAxis().setTicks(ticks).setStacked(false);
        BarScale scales = new BarScale().addxAxes(axis);

        JavaScriptFunction label = new JavaScriptFunction(
                "\"function(chart) {console.log('test legend');}\""
        );
        BarOptions barOptions = new BarOptions()
                .setResponsive(true)
                .setTitle(new Title().setText("test"))
                .setLegend(new Legend()
                        .setDisplay(true)
                        .setOnClick(label))
                .setScales(scales);

        return new ChartJs(new be.ceau.chart.BarChart(data,barOptions).toJson());
    }

    private static BarDataset getBarDataSet(PoslanecEntity poslanecEntity, Color color) {
        double[] doublesArr = getPoslanecDoublesArray(poslanecEntity);
        return new BarDataset()
                .setLabel(poslanecEntity.toString())
                .setData(doublesArr)
                .addBackgroundColor(color)
                .setBorderWidth(2);
    }

    Div getPoslanecSentimentBarChart(List<PoslanecEntity> listPoslanecEntity) {
        return null;
    }


    Div getDoubleBarChart(Map<MonthYear, Double> data) {
        return null;
    }

    Div getDoubleBarChart(List<Map<MonthYear, Double>> data) {
        return null;
    }



    public static Div getTest0Div(){
        return wrapToDiv(getTest0());
    }

    public static Div getTest1Div(PoslanecEntity poslanecEntity){
        return wrapToDiv(getTest1(poslanecEntity));
    }

    public static Div getTest1Div(List<PoslanecEntity> list){
        return wrapToDiv(getTest1(list));
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

    private static ChartJs getTest1(PoslanecEntity poslanecEntity) {
        List<PoslanecEntity> list = new ArrayList<>();
        list.add(poslanecEntity);
        return getTest1(list);
    }

    private static ChartJs getTest1(List<PoslanecEntity> listPoslanecEntity)
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
                BarDataset barDataset = getBarDataSet(poslanecEntity, colors.getColor());
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
}
