package web;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import poslanciDB.entity.OrganyEntity;
import poslanciDB.entity.PoslanecEntity;
import poslanciDB.service.OrganyEntityService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class ChoosePartyComponent extends HorizontalLayout {
    OrganyEntityService organyEntityService = new OrganyEntityService();
    ComboBox<OrganyEntity> seasonsComboBox = new ComboBox<>();
    ComboBox<OrganyEntity> partysComboBox = new ComboBox<>();

    Integer comboBoxWidth = 400;

    public ChoosePartyComponent(Consumer<OrganyEntity> func) {
        initializeComponents(func);
        add(seasonsComboBox, partysComboBox);
    }

    private void initializeComponents(Consumer<OrganyEntity> func) {
        seasonsComboBox.setLabel("Volební odbobí:");
        seasonsComboBox.setWidth(comboBoxWidth.toString());
        setupSeasonsComboBox();

        //TODO zatim takhle, je potreba asi udelat vazbu s nadrazenym organem
        seasonsComboBox.addValueChangeListener(event -> {
            Set<OrganyEntity> set = new HashSet<>();
            if(event.getValue() != null) {
                event.getValue().getPoslanecsObdobiByIdOrgan().forEach(posl -> {
                    //System.out.println(posl.getOrganyByIdKandidatka());
                    set.add(posl.getOrganyByIdKandidatka());
                });
            }
            //System.out.println();
            partysComboBox.setItems(set);
            partysComboBox.setEnabled(true);
        });

        partysComboBox.setLabel("Kandidátka:");
        partysComboBox.setEnabled(false);
        partysComboBox.setWidth(comboBoxWidth.toString());
        partysComboBox.addValueChangeListener(event -> {
            func.accept(event.getValue());
        });
    }

    private void setupSeasonsComboBox() {
        List<OrganyEntity> list = organyEntityService.getAllChamberSeasons();
        seasonsComboBox.setItems(list);
    }
}
