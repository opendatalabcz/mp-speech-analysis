package web.party;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import poslanciDB.entity.OrganyEntity;
import poslanciDB.entity.PoslanecEntity;
import web.ChoosePartyComponent;
import web.poslanec.PoslanecProfilComponent;

public class PartyView extends VerticalLayout {
    Div div = new Div();

    public PartyView() { add(new ChoosePartyComponent(this::setDiv), div);    }

    private void setDiv(OrganyEntity organyEntity){
        div.removeAll();
        if(organyEntity != null)
            div.add(new PartyProfilComponent(organyEntity));
    }
}
