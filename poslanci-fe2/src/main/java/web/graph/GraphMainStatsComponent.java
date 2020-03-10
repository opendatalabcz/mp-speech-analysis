package web.graph;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import poslanciDB.entity.OsobyEntity;
import poslanciDB.entity.PoslanecEntity;
import web.ChooseOsobaComponent;

import java.util.HashSet;
import java.util.Set;

class GraphMainStatsComponent extends VerticalLayout {
    Set<OsobyEntity> set = null;
    Label label = new Label("Tady");

    public GraphMainStatsComponent(Set<OsobyEntity> set) {
        this.set = set;
        initialize();
        add(label);
    }

    private void initialize() {
        if(set != null)
        {
            label = new Label("TADY " + set.size());
            removeAll();
            add(label);
        }
    }

    public void refresh(Set<OsobyEntity> set) {
        this.set = set;
        initialize();
    }
}
