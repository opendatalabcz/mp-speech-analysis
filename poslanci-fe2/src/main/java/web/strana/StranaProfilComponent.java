package web.strana;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import poslanciDB.entity.OrganyEntity;
import web.Colors;
import web.Helper;

public class StranaProfilComponent extends VerticalLayout {
    private OrganyEntity organyEntity;

    public StranaProfilComponent(OrganyEntity organyEntity) {
        this.organyEntity = organyEntity;
        Label label = new Label("PROFIL:");
        label.getElement().getStyle().set("color", Colors.getHighlightColorString()).set("text-decoration", "underline");
        add(label, getName(), getAbbrevation());
    }

    private HorizontalLayout getName() {
        return Helper.getValueWithLabelComponent("Jméno: ", organyEntity.getNazevOrganuCz() + " (" +
                organyEntity.getNazevOrganuEn() + ")");
    }

    private HorizontalLayout getAbbrevation() {
        return Helper.getValueWithLabelComponent("Zkratka: ", organyEntity.getZkratka());
    }
}
