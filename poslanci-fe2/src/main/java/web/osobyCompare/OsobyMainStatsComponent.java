package web.osobyCompare;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import poslanciDB.entity.OsobyEntity;
import web.chart.OsobyBarChart;
import web.chart.OsobyStackedBarChart;

import java.util.Set;

class OsobyMainStatsComponent extends HorizontalLayout {
    Set<OsobyEntity> set = null;
    Div osobyTotalSentimentChart = null;
    Div osobySlovaChart = null;


    public OsobyMainStatsComponent(Set<OsobyEntity> set) {
        this.set = set;
        initialize();
    }

    private void initialize() {
        if(set != null)
        {
            osobyTotalSentimentChart = OsobyBarChart.getOsobySentimentPeriodDiv(set);
            osobySlovaChart = OsobyStackedBarChart.getOsobyPocetSlovDiv(set);
            removeAll();
            add(osobyTotalSentimentChart, osobySlovaChart);
        }
    }

    public void refresh(Set<OsobyEntity> set) {
        this.set = set;
        initialize();
    }
}
