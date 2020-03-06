package web.osoba;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import poslanciDB.entity.OsobyEntity;
import poslanciDB.entity.PoslanecEntity;
import web.ChooseOsobaComponent;
import web.poslanec.PoslanecProfilComponent;
import web.poslanec.PoslanecStatistikyComponent;

public class OsobaView extends VerticalLayout {
    Div div = new Div();

    public OsobaView() { add(new ChooseOsobaComponent(this::setDiv), div);
    }

    private void setDiv(OsobyEntity osobyEntity){
        div.removeAll();
        if(osobyEntity != null){
            div.add(new OsobaProfilComponent(osobyEntity));
            for(PoslanecEntity poslanecEntity : osobyEntity.getPoslanecsByIdOsoba()) {
                Label label = new Label("ODBOBI: " + poslanecEntity.getOrganyByIdObdobi());
                PoslanecProfilComponent poslanecProfilComponent = new PoslanecProfilComponent(poslanecEntity);
                PoslanecStatistikyComponent poslanecStatistikyComponent = new PoslanecStatistikyComponent(poslanecEntity);
                div.add(label, poslanecProfilComponent, poslanecStatistikyComponent);
            }
        }
    }
}
