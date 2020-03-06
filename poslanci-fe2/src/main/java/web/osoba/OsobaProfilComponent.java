package web.osoba;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import poslanciDB.entity.OsobyEntity;
import web.Helper;

public class OsobaProfilComponent extends VerticalLayout {
    private OsobyEntity osobyEntity;

    public OsobaProfilComponent(OsobyEntity osobyEntity) {
        this.osobyEntity = osobyEntity;
        add(getName());
    }

    private HorizontalLayout getName() {
        return Helper.getValueWithLabelComponent("Jméno: ", osobyEntity.toString());
    }
}
