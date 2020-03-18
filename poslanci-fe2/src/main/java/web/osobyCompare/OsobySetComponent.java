package web.osobyCompare;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import poslanciDB.entity.OsobyEntity;
import web.ChooseOsobaComponent;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class OsobySetComponent extends VerticalLayout {
    Set<OsobyEntity> setOsoby = new HashSet<>();
    Grid<OsobyEntity> grid = new Grid<>(OsobyEntity.class);
    OsobyEntity choosedOsoba = null;
    Button addButton = new Button("Přidat");
    Button removeButton = new Button("Odebrat");
    ChooseOsobaComponent chooseOsobaComponent = new ChooseOsobaComponent(this::setPerson);

    public OsobySetComponent(Consumer<Set<OsobyEntity>> func) {
        initialize(func);
        add(chooseOsobaComponent, new HorizontalLayout(addButton, removeButton), grid);
    }

    private void initialize(Consumer<Set<OsobyEntity>> func) {
        grid.addThemeVariants(GridVariant.MATERIAL_COLUMN_DIVIDERS);
        grid.setItems(setOsoby);
        grid.removeAllColumns();
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.addColumn(OsobyEntity::toString).setHeader("Osoba");
        grid.addItemDoubleClickListener(event -> {
            setOsoby.remove(event.getItem());
            grid.setItems(setOsoby);
            func.accept(setOsoby);
        });

        addButton.addClickListener(buttonClickEvent -> {
            if(choosedOsoba != null) {
                setOsoby.add(choosedOsoba);
                grid.setItems(setOsoby);
                chooseOsobaComponent.clear();
                func.accept(setOsoby);
            }
        });

        removeButton.addClickListener(buttonClickEvent -> {
            if(!grid.getSelectedItems().isEmpty()) {
                setOsoby.removeAll(grid.getSelectedItems());
                grid.setItems(setOsoby);
                func.accept(setOsoby);
            }
        });
    }

    private void setPerson(OsobyEntity osobyEntity) {
        choosedOsoba = osobyEntity;
    }
}
