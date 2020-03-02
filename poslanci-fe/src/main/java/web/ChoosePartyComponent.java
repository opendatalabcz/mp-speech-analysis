package web;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import poslanciDB.entity.OrganyEntity;
import poslanciDB.service.OrganyEntityService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class ChoosePartyComponent extends HorizontalLayout {
    OrganyEntityService organyEntityService = new OrganyEntityService();
    Select<OrganyEntity> seasonsSelect = new Select<>();
    Select<OrganyEntity> partysSelect = new Select<>();

    Integer comboBoxWidth = 400;

    public ChoosePartyComponent(Consumer<OrganyEntity> func) {
        initializeComponents(func);
        add(seasonsSelect, partysSelect);
    }

    private void initializeComponents(Consumer<OrganyEntity> func) {
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
        });

        partysSelect.setLabel("Kandidátka:");
        partysSelect.setEnabled(false);
        partysSelect.setWidth(comboBoxWidth.toString());
        partysSelect.addValueChangeListener(event -> {
            func.accept(event.getValue());
        });
    }

    private void setupSeasonsComboBox() {
        List<OrganyEntity> list = organyEntityService.getAllChamberSeasons();
        seasonsSelect.setItems(list);
    }
}
