package web.stranyCompare;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import poslanciDB.entity.OrganyEntity;
import web.ChooseObdobiComponent;

public class StranyCompareView extends VerticalLayout {
    Div div = new Div();

    public StranyCompareView() { add(new ChooseObdobiComponent(this::setDiv), div);  }

    private void setDiv(OrganyEntity organyEntity) {
        div.removeAll();
        if(organyEntity != null)
            div.add(new StranyCompareChartsComponent(organyEntity));
    }
}
