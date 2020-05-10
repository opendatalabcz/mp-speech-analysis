package web.chart;

import be.ceau.chart.data.BarData;
import be.ceau.chart.dataset.BarDataset;
import be.ceau.chart.options.BarOptions;
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
import poslanciDB.entity.OsobyEntity;
import web.Colors;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static web.SizeUI.wrapToMediumDiv;
import static web.chart.Helper.getGridLines;

public class OsobyStackedBarChart {

    public static Div getOsobyPocetSlovDiv(Set<OsobyEntity> set) {
        return wrapToMediumDiv(getOsobyPocetSlov(set));
    }

    public static ChartJs getOsobyPocetSlov(Set<OsobyEntity> set) {
        List<String> labels = new ArrayList<>();

        BarScale scale = new BarScale()
                .addxAxes(new XAxis<LinearTicks>().setStacked(true).setTicks(new LinearTicks().setAutoSkip(false)).setGridLines(getGridLines()))
                .addyAxes(new YAxis<LinearTicks>().setStacked(true));

        BarOptions options = new BarOptions();
        options.setResponsive(true)
                .setScales(scale)
                .setTitle(new Title().setText("Slova podle osob").setDisplay(true).setFontColor(Colors.getChartLabelColor())); //todo popisky a barvy

        Colors colors = new Colors();

        BarDataset neutralBarDataset = new BarDataset().addBackgroundColor(colors.getYellow()).setLabel("Neutrální/nerozlišitelná");
        BarDataset posBarDataset = new BarDataset().addBackgroundColor(colors.getGreen()).setLabel("Pozitivní");
        BarDataset negBarDataset = new BarDataset().addBackgroundColor(colors.getRed()).setLabel("Negativní");

        for(OsobyEntity osobyEntity : set) {
            OsobaData.processOsobaSlova(osobyEntity, neutralBarDataset, posBarDataset, negBarDataset);
            labels.add(osobyEntity.getJmeno() + " " + osobyEntity.getPrijmeni());
        }

        BarData barData = new BarData();
        barData.setLabels(labels.toArray(new String[0]));
        barData.addDataset(neutralBarDataset).addDataset(posBarDataset).addDataset(negBarDataset);

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
