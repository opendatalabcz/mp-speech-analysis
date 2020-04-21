package web;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import poslanciDB.entity.OsobyEntity;
import poslanciDB.service.OsobyEntityService;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

public class ChooseOsobaComponent extends HorizontalLayout {
    ComboBox<OsobyEntity> osobyComboBox = new ComboBox<>("Jméno osoby:");
    OsobyEntityService osobyEntityService = new OsobyEntityService();


    public ChooseOsobaComponent(Consumer<OsobyEntity> func) {
        initializeComponents(func);
        add(osobyComboBox);
    }

    public ChooseOsobaComponent(Consumer<OsobyEntity> func, OsobyEntity osobyEntity) {
        initializeComponents(func);
        add(osobyComboBox);
        chooseOsoba(osobyEntity);
    }

    private void chooseOsoba(OsobyEntity osobyEntity) {
        osobyComboBox.setValue(osobyEntity);
    }

    public void clear() {
        osobyComboBox.clear();
    }

    private void initializeComponents(Consumer<OsobyEntity> func) {
        List<OsobyEntity> list = osobyEntityService.findAll();
        list.sort((o1, o2) -> o1.getPrijmeni().compareToIgnoreCase(o2.getPrijmeni()));

        osobyComboBox.setItems(list);

        osobyComboBox.addValueChangeListener(event -> {
           func.accept(event.getValue());
        });
    }
}
