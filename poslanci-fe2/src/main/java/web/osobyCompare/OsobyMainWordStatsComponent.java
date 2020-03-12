package web.osobyCompare;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import poslanciDB.entity.OsobyEntity;
import web.chart.OsobyBarChart;
import web.chart.OsobyStackedBarChart;

import java.util.Set;

class OsobyMainWordStatsComponent extends VerticalLayout {
    Set<OsobyEntity> set = null;

    Div osobySlovaPeriodChart = null;
    Div osobyPosSlovaPeriodChart = null;
    Div osobyNegSlovaPeriodChart = null;
    Div osobyPosNegSlovaPeriodChart = null;

    public OsobyMainWordStatsComponent(Set<OsobyEntity> set) {
        this.set = set;
        initialize();
    }

    private void initialize() {
        if(set != null)
        {
            osobySlovaPeriodChart = OsobyBarChart.getOsobyPocetSlovPeriodDiv(set);
            osobyPosSlovaPeriodChart = OsobyBarChart.getOsobyPocetPosSlovPeriodDiv(set);
            osobyNegSlovaPeriodChart = OsobyBarChart.getOsobyPocetNegSlovPeriodDiv(set);
            osobyPosNegSlovaPeriodChart = OsobyBarChart.getOsobyPocetPosNegSlovPeriodDiv(set);
            removeAll();
            add(new HorizontalLayout(osobySlovaPeriodChart, osobyPosNegSlovaPeriodChart),
                    new HorizontalLayout(osobyPosSlovaPeriodChart, osobyNegSlovaPeriodChart));
        }
    }

    public void refresh(Set<OsobyEntity> set) {
        this.set = set;
        initialize();
    }
}
