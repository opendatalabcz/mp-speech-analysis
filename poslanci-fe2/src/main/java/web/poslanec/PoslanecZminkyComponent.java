package web.poslanec;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import poslanciDB.entity.PoslanecEntity;
import poslanciDB.entity.PoslanecStatistikyZminkyEntity;

public class PoslanecZminkyComponent extends HorizontalLayout {
    Grid<PoslanecStatistikyZminkyEntity> gridRecnik = new Grid<>(PoslanecStatistikyZminkyEntity.class);
    Grid<PoslanecStatistikyZminkyEntity> gridZmineny = new Grid<>(PoslanecStatistikyZminkyEntity.class);
    PoslanecEntity poslanecEntity;

    public PoslanecZminkyComponent(PoslanecEntity poslanecEntity) {
        this.poslanecEntity = poslanecEntity;
        initialize();
        add(gridRecnik, gridZmineny);
    }

    private void initialize() {
        gridRecnik.addThemeVariants(GridVariant.MATERIAL_COLUMN_DIVIDERS);
        gridRecnik.setWidth("550px");
        gridRecnik.setHeight("600px");
        gridRecnik.setItems(poslanecEntity.getPoslanecStatistikyByIdPoslanec().getPoslanecStatistikyZminkiesByIdPoslanec());
        gridRecnik.removeAllColumns();
        gridRecnik.setSelectionMode(Grid.SelectionMode.NONE);
        gridRecnik.addColumn(PoslanecStatistikyZminkyEntity::getPoslanecByIdPoslanecZmineny)
                .setHeader("Poslanec " + poslanecEntity + " zmiňoval:").setSortable(true).setFlexGrow(2);
        gridRecnik.addColumn(PoslanecStatistikyZminkyEntity::getPocetVyskytu)
                .setHeader("Počet výskytů").setSortable(true);

        gridZmineny.addThemeVariants(GridVariant.MATERIAL_COLUMN_DIVIDERS);
        gridZmineny.setWidth("550px");
        gridZmineny.setHeight("600px");
        gridZmineny.setItems(poslanecEntity.getPoslanecStatistikyZminkiesByIdPoslanec());
        gridZmineny.removeAllColumns();
        gridZmineny.setSelectionMode(Grid.SelectionMode.NONE);
        gridZmineny.addColumn(PoslanecStatistikyZminkyEntity::getPoslanecStatistikyByIdPoslanecRecnik)
                .setHeader("Poslanec " + poslanecEntity + " byl zmiňován:").setSortable(true).setFlexGrow(2);
        gridZmineny.addColumn(PoslanecStatistikyZminkyEntity::getPocetVyskytu)
                .setHeader("Počet výskytů").setSortable(true);
    }
}
