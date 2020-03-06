package web.party;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import poslanciDB.entity.OrganyEntity;
import web.Helper;

public class PartyProfilComponent extends VerticalLayout {
    private OrganyEntity organyEntity;

    public PartyProfilComponent(OrganyEntity organyEntity) {
        this.organyEntity = organyEntity;
        add(getName(), getAbbrevation());
    }

    private HorizontalLayout getName() {
        return Helper.getValueWithLabelComponent("Jméno: ", organyEntity.getNazevOrganuCz());
    }

    private HorizontalLayout getAbbrevation() {
        return Helper.getValueWithLabelComponent("Zkratka: ", organyEntity.getZkratka());
    }
}
