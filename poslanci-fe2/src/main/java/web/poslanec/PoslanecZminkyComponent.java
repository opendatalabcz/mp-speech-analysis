package web.poslanec;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import poslanciDB.entity.PoslanecEntity;
import poslanciDB.entity.PoslanecStatistikyZminkyEntity;
import web.Colors;
import web.Helper;

import java.util.List;
import java.util.function.Consumer;

import static web.chart.ZminkyBarChart.getPoslanecZminkyStranyDiv;
import static web.chart.ZminkyBarChart.getPoslanecZminkyStranyDivideByPoslanecCountDiv;

public class PoslanecZminkyComponent extends VerticalLayout {
    Grid<PoslanecStatistikyZminkyEntity> gridRecnik = new Grid<>(PoslanecStatistikyZminkyEntity.class);
    Grid<PoslanecStatistikyZminkyEntity> gridZmineny = new Grid<>(PoslanecStatistikyZminkyEntity.class);
    PoslanecEntity poslanecEntity;

    public PoslanecZminkyComponent(PoslanecEntity poslanecEntity, Consumer<PoslanecEntity> switchToPoslanec) {
        this.poslanecEntity = poslanecEntity;
        initialize(switchToPoslanec);
        Label label = new Label("Zmínky poslance:");
        label.getElement().getStyle().set("color", Colors.getHighlightColorString()).set("text-decoration", "underline");
        add(label);
        addDuplicityLabel(poslanecEntity);
        add(new HorizontalLayout(gridRecnik, gridZmineny),
                new HorizontalLayout(getPoslanecZminkyStranyDiv(poslanecEntity),
                        getPoslanecZminkyStranyDivideByPoslanecCountDiv(poslanecEntity)
                )
        );
    }

    private void initialize(Consumer<PoslanecEntity> switchToPoslanec) {
        gridRecnik.addThemeVariants(GridVariant.MATERIAL_COLUMN_DIVIDERS);
        gridRecnik.setWidth("550px");
        gridRecnik.setHeight("600px");
        gridRecnik.setItems(poslanecEntity.getPoslanecStatistikyByIdPoslanec().getPoslanecStatistikyZminkiesByIdPoslanec());
        gridRecnik.removeAllColumns();
        gridRecnik.setSelectionMode(Grid.SelectionMode.NONE);
        gridRecnik.addColumn(PoslanecStatistikyZminkyEntity::getPoslanecByIdPoslanecZmineny)
                .setHeader("Poslanec " + poslanecEntity + " zmiňoval:").setSortable(true).setFlexGrow(2);
        gridRecnik.addColumn(PoslanecStatistikyZminkyEntity::getPocetVyskytu)
                .setHeader("Počet výskytů").setSortable(true).setKey("PocetVyskytu");
        gridRecnik.addItemDoubleClickListener(event -> {
            switchToPoslanec.accept(event.getItem().getPoslanecByIdPoslanecZmineny());
        });

        gridZmineny.addThemeVariants(GridVariant.MATERIAL_COLUMN_DIVIDERS);
        gridZmineny.setWidth("550px");
        gridZmineny.setHeight("600px");
        gridZmineny.setItems(poslanecEntity.getPoslanecStatistikyZminkiesByIdPoslanec());
        gridZmineny.removeAllColumns();
        gridZmineny.setSelectionMode(Grid.SelectionMode.NONE);
        gridZmineny.addColumn(PoslanecStatistikyZminkyEntity::getPoslanecStatistikyByIdPoslanecRecnik)
                .setHeader("Poslanec " + poslanecEntity + " byl zmiňován:").setSortable(true).setFlexGrow(2);
        gridZmineny.addColumn(PoslanecStatistikyZminkyEntity::getPocetVyskytu)
                .setHeader("Počet výskytů").setSortable(true).setKey("PocetVyskytu");
        gridZmineny.addItemDoubleClickListener(event -> {
            switchToPoslanec.accept(event.getItem().getPoslanecStatistikyByIdPoslanecRecnik().getPoslanecByIdPoslanec());
        });
    }

    private void addDuplicityLabel(PoslanecEntity poslanecEntity) {
        List<PoslanecEntity> poslanecEntitiesSamePrijmeni = Helper.getPoslanciWithSamePrijmeniInObdobi(poslanecEntity);
        if(poslanecEntitiesSamePrijmeni.size() > 0) {
            String text = "V tomto volebním obdobím jsou poslanci se stejným příjmením, kteří mohou zkreslovat počet zmínek pro tuto osobu: ";
            for(PoslanecEntity posl : poslanecEntitiesSamePrijmeni) {
                text = text.concat(posl.toString() + " (" + posl.getOrganyByIdKandidatka().getZkratka() + "), ");
            }
            add(new Label(text));
        }
    }
}
