package web.graph;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import poslanciDB.entity.OsobyEntity;
import web.ChooseOsobaComponent;

import java.util.HashSet;
import java.util.Set;

public class GraphView extends VerticalLayout {
    Set<OsobyEntity> setOsoby = new HashSet<>();
    Grid<OsobyEntity> grid = new Grid<>(OsobyEntity.class);
    OsobyEntity choosedOsoba = null;
    Button addButton = new Button("Přidat");

    public GraphView() {
        initialize();
        add(new ChooseOsobaComponent(this::setPerson), addButton, grid);
        updateGraphs();
    }

    private void updateGraphs() {
        
    }

    private void initialize() {
        grid.setItems(setOsoby);
        grid.removeAllColumns();
        grid.addColumn(OsobyEntity::toString).setHeader("Osoba");
        grid.addItemDoubleClickListener(event -> {
            setOsoby.remove(event.getItem());
            grid.setItems(setOsoby);
        });

        addButton.addClickListener(buttonClickEvent -> {
            if(choosedOsoba != null) {
                setOsoby.add(choosedOsoba);
                grid.setItems(setOsoby);
            }
        });
    }

    private void setPerson(OsobyEntity osobyEntity) {
        choosedOsoba = osobyEntity;
    }
}
