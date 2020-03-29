package web.strana;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import poslanciDB.entity.OrganyEntity;
import web.ChooseStranaComponent;

public class StranaView extends VerticalLayout {
    Div div = new Div();

    public StranaView() { add(new ChooseStranaComponent(this::setDiv), div);    }

    private void setDiv(OrganyEntity obdobi, OrganyEntity strana){
        div.removeAll();
        div.setSizeFull();
        if(obdobi != null && strana != null)
            div.add(new StranaProfilComponent(strana), new StranaStatistikyComponent(obdobi, strana));
    }
}
