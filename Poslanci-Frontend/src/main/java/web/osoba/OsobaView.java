package web.osoba;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import poslanciDB.entity.OsobyEntity;
import poslanciDB.entity.PoslanecEntity;
import web.ChooseOsobaComponent;
import web.Colors;
import web.poslanec.PoslanecStatistikyComponent;

import java.util.function.Consumer;

public class OsobaView extends VerticalLayout {
    private Consumer<PoslanecEntity> switchToPoslanec;
    Div div = new Div();
    Div stats = new Div();

    public OsobaView(Consumer<PoslanecEntity> switchToPoslanec) {
        this.switchToPoslanec = switchToPoslanec;
        add(new ChooseOsobaComponent(this::setDiv), div);
    }

    public OsobaView(Consumer<PoslanecEntity> switchToPoslanec, OsobyEntity osobyEntity) {
        this.switchToPoslanec = switchToPoslanec;
        add(new ChooseOsobaComponent(this::setDiv, osobyEntity), div);
    }

    private void setDiv(OsobyEntity osobyEntity){
        div.removeAll();
        if(osobyEntity != null){
            div.add(new OsobaProfilComponent(osobyEntity));
            HorizontalLayout horizontalLayout = new HorizontalLayout();
            Label label = new Label("Volební období:");
            label.getElement().getStyle().set("color", Colors.getHighlightColorString()).set("font-weight", "bold");
            horizontalLayout.add(label, getObdobiComboBox(osobyEntity));
            div.add(horizontalLayout);
            div.add(stats);
        }
    }

    private ComboBox<PoslanecEntity> getObdobiComboBox(OsobyEntity osobyEntity) {
        ComboBox<PoslanecEntity> comboBox = new ComboBox<>();
        comboBox.setWidth("250px");
        comboBox.setItems(osobyEntity.getPoslanecsByIdOsoba());
        comboBox.setItemLabelGenerator(item -> item.getOrganyByIdObdobi().toString());
        comboBox.addValueChangeListener(event -> {
            stats.removeAll();
            if(event.getValue() != null) {
                stats.add(new PoslanecStatistikyComponent(event.getValue(), switchToPoslanec));
            }
        });
        return comboBox;
    }
}
