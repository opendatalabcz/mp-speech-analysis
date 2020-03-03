package web;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import poslanciDB.entity.OrganyEntity;
import poslanciDB.entity.OsobyEntity;
import poslanciDB.service.OsobyEntityService;

import java.util.List;
import java.util.function.Consumer;

public class ChooseOsobaComponent extends HorizontalLayout {
    ComboBox<OsobyEntity> osobyComboBox = new ComboBox<>("Jméno osoby:");
    OsobyEntityService osobyEntityService = new OsobyEntityService();


    public ChooseOsobaComponent(Consumer<OsobyEntity> func) {
        initializeComponents(func);
        add(osobyComboBox);
    }

    private void initializeComponents(Consumer<OsobyEntity> func) {
        List<OsobyEntity> list = osobyEntityService.findAll();
        osobyComboBox.setItems(list);

        osobyComboBox.addValueChangeListener(event -> {
           func.accept(event.getValue());
        });
    }
}
