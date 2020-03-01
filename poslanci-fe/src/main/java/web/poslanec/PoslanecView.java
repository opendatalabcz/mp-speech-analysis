package web.poslanec;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import poslanciDB.entity.PoslanecEntity;
import web.ChoosePoslanecComponent;

public class PoslanecView extends VerticalLayout {
    Div div = new Div();

    public PoslanecView() {
        add(new ChoosePoslanecComponent(this::setDiv), div);
    }

    private void setDiv(PoslanecEntity poslanecEntity){
        div.removeAll();
        if(poslanecEntity != null)
            div.add(new PoslanecProfilComponent(poslanecEntity), new PoslanecStatistikyComponent(poslanecEntity));
    }
}
