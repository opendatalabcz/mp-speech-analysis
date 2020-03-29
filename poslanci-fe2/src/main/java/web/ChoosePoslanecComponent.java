package web;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import poslanciDB.entity.OrganyEntity;
import poslanciDB.entity.PoslanecEntity;
import poslanciDB.service.OrganyEntityService;

import java.util.*;
import java.util.function.Consumer;

public class ChoosePoslanecComponent extends HorizontalLayout {
    private OrganyEntityService organyEntityService = new OrganyEntityService();
    private Select<OrganyEntity> seasonsSelect = new Select<>();
    private Select<OrganyEntity> partysSelect = new Select<>();
    private Select<PoslanecEntity> personsSelect = new Select<>();
    private OrganyEntity kandidatkaZbytek;

    private Integer comboBoxWidth = 400;


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
            Set<OrganyEntity> partysSet = new HashSet<>();
            setupKandidatkaZbytek();
            if(event.getValue() != null) {
                event.getValue().getPoslanecsObdobiByIdOrgan().forEach(posl -> {
                    if(posl.getOrganyByIdKandidatka() == null) {
                        posl.setOrganyByIdKandidatka(kandidatkaZbytek);
                        Collection<PoslanecEntity> kandidatka = kandidatkaZbytek.getPoslanecsKandidatkaByIdOrgan();
                        kandidatka.add(posl);
                        kandidatkaZbytek.setPoslanecsKandidatkaByIdOrgan(kandidatka);
                    }
                    partysSet.add(posl.getOrganyByIdKandidatka());
                });
            }
            partysSelect.setItems(partysSet);
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

    private void setupKandidatkaZbytek() {
        kandidatkaZbytek = new OrganyEntity();
        kandidatkaZbytek.setIdOrgan(-1);
        kandidatkaZbytek.setPoslanecsKandidatkaByIdOrgan(new ArrayList<>());
        kandidatkaZbytek.setZkratka("");
        kandidatkaZbytek.setNazevOrganuCz("\"Strana neurčena\"");
    }

    private void setupSeasonsComboBox() {
        List<OrganyEntity> list = organyEntityService.getAllChamberSeasons();
        seasonsSelect.setItems(list);
    }
}
