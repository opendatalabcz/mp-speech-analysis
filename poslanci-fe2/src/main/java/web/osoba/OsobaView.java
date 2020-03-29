package web.osoba;

import be.ceau.chart.color.Color;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.accordion.AccordionPanel;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import poslanciDB.entity.OsobyEntity;
import poslanciDB.entity.PoslanecEntity;
import web.ChooseOsobaComponent;
import web.Colors;
import web.poslanec.PoslanecProfilComponent;
import web.poslanec.PoslanecStatistikyComponent;

public class OsobaView extends VerticalLayout {
    Div div = new Div();

    public OsobaView() { add(new ChooseOsobaComponent(this::setDiv), div);
    }

    private void setDiv(OsobyEntity osobyEntity){
        div.removeAll();
        Accordion accordion = new Accordion();
        if(osobyEntity != null){
            div.add(new OsobaProfilComponent(osobyEntity));
            for(PoslanecEntity poslanecEntity : osobyEntity.getPoslanecsByIdOsoba()) {
                Label label = new Label("OBDOBÍ: " + poslanecEntity.getOrganyByIdObdobi());
                label.getElement().getStyle().set("color", Colors.getHighlightColorString()).set("font-weight", "bold");
                PoslanecStatistikyComponent poslanecStatistikyComponent = new PoslanecStatistikyComponent(poslanecEntity);
                accordion.add(new AccordionPanel(label, poslanecStatistikyComponent));
            }
            accordion.close();
            accordion.setWidthFull();
            accordion.setSizeFull();
            div.add(accordion);
        }
    }
}
