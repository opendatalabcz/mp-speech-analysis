package web;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import poslanciDB.entity.OrganyEntity;
import poslanciDB.service.OrganyEntityService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class ChooseObdobiComponent extends HorizontalLayout {
    OrganyEntityService organyEntityService = new OrganyEntityService();
    Select<OrganyEntity> seasonsSelect = new Select<>();

    Integer comboBoxWidth = 400;

    public ChooseObdobiComponent(Consumer<OrganyEntity> func) {
        initializeComponents(func);
        add(seasonsSelect);
    }

    private void initializeComponents(Consumer<OrganyEntity> func) {
        seasonsSelect.setLabel("Volební odbobí:");
        seasonsSelect.setWidth(comboBoxWidth.toString());
        setupSeasonsComboBox();

        //TODO zatim takhle, je potreba asi udelat vazbu s nadrazenym organem
        seasonsSelect.addValueChangeListener(event -> {
            func.accept(event.getValue());
        });
    }

    private void setupSeasonsComboBox() {
        List<OrganyEntity> list = organyEntityService.getAllChamberSeasons();
        seasonsSelect.setItems(list);
    }
}
