package web.poslanec;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import poslanciDB.entity.PoslanecEntity;
import web.Colors;

import java.util.ArrayList;
import java.util.List;

import static web.chart.PoslanecBarChart.getPoslanecPocetSlovMesicDiv;
import static web.chart.PoslanecBarChart.getPoslanecSentimentMesicDiv;

public class PoslanecChartsComponent extends VerticalLayout {
    public PoslanecChartsComponent(PoslanecEntity poslanecEntity) {
        Label label = new Label("Grafy poslance v měsících:");
        label.getElement().getStyle().set("color", Colors.getHighlightColorString()).set("text-decoration", "underline");
        add(label);
        List<PoslanecEntity> list = new ArrayList<>();
        list.add(poslanecEntity);
        add(getPoslanecPocetSlovMesicDiv(list), getPoslanecSentimentMesicDiv(list));
    }
}
