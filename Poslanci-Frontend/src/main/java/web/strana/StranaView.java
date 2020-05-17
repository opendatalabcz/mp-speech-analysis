package web.strana;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import poslanciDB.entity.OrganyEntity;
import poslanciDB.entity.PoslanecEntity;
import web.ChooseStranaComponent;

import java.util.function.Consumer;

public class StranaView extends VerticalLayout {
    Div div = new Div();
    Consumer<PoslanecEntity> switchToPoslanec;

    public StranaView(Consumer<PoslanecEntity> switchToPoslanec) {
        this.switchToPoslanec = switchToPoslanec;
        add(new ChooseStranaComponent(this::setDiv), div);
    }


    private void setDiv(OrganyEntity obdobi, OrganyEntity strana){
        div.removeAll();
        div.setSizeFull();
        if(obdobi != null && strana != null)
            div.add(new StranaProfilComponent(strana), new StranaStatistikyComponent(obdobi, strana, switchToPoslanec));
    }
}
