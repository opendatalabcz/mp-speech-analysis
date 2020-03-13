package web.osobyCompare;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import poslanciDB.entity.PoslanecEntity;
import web.Colors;

import java.util.List;

public class PoslanciCompareStatsComponent extends VerticalLayout {

    public PoslanciCompareStatsComponent(List<PoslanecEntity> list) {
        if(!list.isEmpty())
        {
            Label label = new Label("OBDOBÍ: " + list.get(0).getOrganyByIdObdobi());
            label.getElement().getStyle().set("color", Colors.getHighlightColorString()).set("font-weight", "bold");
            add(label);
        }
    }
}
