package web;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import poslanciDB.entity.OrganyEntity;
import poslanciDB.entity.PoslanecEntity;
import poslanciDB.service.OrganyEntityService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class ChoosePoslanecComponent extends HorizontalLayout {
    OrganyEntityService organyEntityService = new OrganyEntityService();
    Select<OrganyEntity> seasonsSelect = new Select<>();
    Select<OrganyEntity> partysSelect = new Select<>();
    Select<PoslanecEntity> personsSelect = new Select<>();

    Integer comboBoxWidth = 400;


    public ChoosePoslanecComponent(Consumer<PoslanecEntity> func) {
        initializeComponents(func);
        add(seasonsSelect, partysSelect, personsSelect);
    }

    private void initializeComponents(Consumer<PoslanecEntity> func) {
        seasonsSelect.setLabel("Volební odbobí:");
        seasonsSelect.setWidth(comboBoxWidth.toString());
        setupSeasonsComboBox();

        //TODO zatim takhle, je potreba asi udelat vazbu s nadrazenym organem
        seasonsSelect.addValueChangeListener(event -> {
            Set<OrganyEntity> set = new HashSet<>();
            if(event.getValue() != null) {
                event.getValue().getPoslanecsObdobiByIdOrgan().forEach(posl -> {
                    //System.out.println(posl.getOrganyByIdKandidatka());
                    set.add(posl.getOrganyByIdKandidatka());
                });
            }
            //System.out.println();
            partysSelect.setItems(set);
            partysSelect.setEnabled(true);
            personsSelect.setItems();
            personsSelect.setEnabled(false);
        });

        partysSelect.setLabel("Kandidátka:");
        partysSelect.setEnabled(false);
        partysSelect.setWidth(comboBoxWidth.toString());
        partysSelect.addValueChangeListener(event -> {
            List<PoslanecEntity> list = new ArrayList<>();
            if(event.getValue() != null) {
                event.getValue().getPoslanecsKandidatkaByIdOrgan().forEach(posl -> {
                    if(posl.getOrganyByIdObdobi() == seasonsSelect.getValue())
                        list.add(posl);
                });
            }
            personsSelect.setItems(list);
            personsSelect.setEnabled(true);
        });

        personsSelect.setLabel("Poslanec:");
        personsSelect.setEnabled(false);
        personsSelect.setWidth(comboBoxWidth.toString());
        personsSelect.addValueChangeListener(event -> {
            func.accept(event.getValue());
        });
    }

    private void setupSeasonsComboBox() {
        List<OrganyEntity> list = organyEntityService.getAllChamberSeasons();
        seasonsSelect.setItems(list);
    }
}
