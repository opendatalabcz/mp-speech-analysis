package web.osobyCompare;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import poslanciDB.entity.PoslanecEntity;
import web.Colors;

import java.util.List;

import static web.chart.PoslanecBarChart.getPoslanecPocetSlovMesicDiv;
import static web.chart.PoslanecBarChart.getPoslanecSentimentMesicDiv;

public class PoslanciCompareStatsComponent extends VerticalLayout {

    public PoslanciCompareStatsComponent(List<PoslanecEntity> list) {
        if(!list.isEmpty())
        {
            add(getPoslanecPocetSlovMesicDiv(list), getPoslanecSentimentMesicDiv(list));
        }
    }
}
