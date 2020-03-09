package web.graph;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import poslanciDB.entity.OsobyEntity;
import poslanciDB.entity.PoslanecEntity;
import web.ChooseOsobaComponent;

import java.util.HashSet;
import java.util.Set;

class GraphMainStatsComponent extends VerticalLayout {
    Set<OsobyEntity> set = null;

    public GraphMainStatsComponent(Set<OsobyEntity> set) {
        this.set = set;
    }
}
