package web.poslanec;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import poslanciDB.entity.PoslanecEntity;
import web.ChoosePoslanecComponent;

import java.util.function.Consumer;

public class PoslanecView extends VerticalLayout {
    private Consumer<PoslanecEntity> switchToPoslanec;
    Div div = new Div();

    public PoslanecView(Consumer<PoslanecEntity> switchToPoslanec) {
        this.switchToPoslanec = switchToPoslanec;
        add(new ChoosePoslanecComponent(this::setDiv), div);
    }

    public PoslanecView(Consumer<PoslanecEntity> switchToPoslanec, PoslanecEntity poslanecEntity) {
        this.switchToPoslanec = switchToPoslanec;
        add(new ChoosePoslanecComponent(this::setDiv, poslanecEntity), div);
    }

    private void setDiv(PoslanecEntity poslanecEntity){
        div.removeAll();
        if(poslanecEntity != null)
            div.add(new PoslanecProfilComponent(poslanecEntity), new PoslanecStatistikyComponent(poslanecEntity, switchToPoslanec));
    }
}
