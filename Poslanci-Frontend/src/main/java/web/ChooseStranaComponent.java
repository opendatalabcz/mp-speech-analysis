package web;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import poslanciDB.entity.OrganyEntity;
import poslanciDB.entity.PoslanecEntity;
import poslanciDB.service.OrganyEntityService;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ChooseStranaComponent extends HorizontalLayout {
    OrganyEntityService organyEntityService = new OrganyEntityService();
    Select<OrganyEntity> seasonsSelect = new Select<>();
    Select<OrganyEntity> partysSelect = new Select<>();

    Integer comboBoxWidth = 400;

    public ChooseStranaComponent(BiConsumer<OrganyEntity, OrganyEntity> func) {
        initializeComponents(func);
        add(seasonsSelect, partysSelect);
    }

    private void initializeComponents(BiConsumer<OrganyEntity, OrganyEntity> func) {
        seasonsSelect.setLabel("Volební odbobí:");
        seasonsSelect.setWidth(comboBoxWidth.toString());
        setupSeasonsComboBox();

        seasonsSelect.addValueChangeListener(event -> {
            Set<OrganyEntity> set = new HashSet<>();
            if(event.getValue() != null) {
                event.getValue().getPoslanecsObdobiByIdOrgan().forEach(posl -> {
                    if(posl.getOrganyByIdKandidatka() != null) {
                        set.add(posl.getOrganyByIdKandidatka());
                    }
                });
            }
            partysSelect.setItems(set);
            partysSelect.setEnabled(true);
        });

        partysSelect.setLabel("Kandidátka:");
        partysSelect.setEnabled(false);
        partysSelect.setWidth(comboBoxWidth.toString());
        partysSelect.addValueChangeListener(event -> {
            func.accept(seasonsSelect.getValue(), event.getValue());
        });
    }

    private void setupSeasonsComboBox() {
        List<OrganyEntity> list = organyEntityService.getAllChamberSeasons();
        seasonsSelect.setItems(list);
    }
}
