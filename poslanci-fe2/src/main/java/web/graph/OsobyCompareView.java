package web.graph;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import poslanciDB.entity.OsobyEntity;

import java.util.Set;

public class OsobyCompareView extends VerticalLayout {
    GraphMainStatsComponent graphMainStatsComponent = new GraphMainStatsComponent(null);
    OsobySetComponent osobySetComponent = new OsobySetComponent(this::getSet);

    public OsobyCompareView() {
        add(new HorizontalLayout(osobySetComponent, graphMainStatsComponent));
    }

    private void getSet(Set<OsobyEntity> set) {
        graphMainStatsComponent.refresh(set);
    }
}
