package web.obdobiCompare;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import poslanciDB.entity.OrganyEntity;

import java.util.List;

import static web.chart.ObdobiBarChart.*;

public class ObdobiCompareChartsComponent extends VerticalLayout {

    public ObdobiCompareChartsComponent(List<OrganyEntity> list) {
        add(getObdobiPocetSlovDiv(list), getObdobiAveragePocetSlovDiv(list), getObdobiMedianPocetSlovDiv(list),
                getObdobiTotalSentimentDiv(list), getObdobiAverageSentimentDiv(list), getObdobiMedianSentimentDiv(list));
    }
}
