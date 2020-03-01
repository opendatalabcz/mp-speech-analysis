package web.poslanec;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import poslanciDB.entity.OsobyEntity;
import poslanciDB.entity.PoslanecEntity;
import web.Helper;

public class PoslanecProfilComponent extends VerticalLayout {
    private PoslanecEntity poslanecEntity;
    private OsobyEntity osobyEntity;
    private Integer labelWidth = 300;

    public PoslanecProfilComponent(PoslanecEntity poslanecEntity) {
        this.poslanecEntity = poslanecEntity;
        this.osobyEntity = poslanecEntity.getOsobyByIdOsoba();
        add(getName(), getParty(), getBirthDate(), getEmail(), getWebPage());
    }

    private HorizontalLayout getName() {
        return Helper.getValueWithLabelComponent("Jméno: ", poslanecEntity.toString());
    }

    private HorizontalLayout getParty() {
        return Helper.getValueWithLabelComponent("Kandidátka: ", poslanecEntity.getOrganyByIdKandidatka().getNazevOrganuCz());
    }

    private HorizontalLayout getBirthDate() {
        return Helper.getValueWithLabelComponent("Narození: ", osobyEntity.getNarozeni().toString());
    }

    private HorizontalLayout getEmail() {
        return Helper.getValueWithLabelComponent("Email: ", poslanecEntity.getEmail());
    }

    private HorizontalLayout getWebPage() {
        return Helper.getValueWithLabelComponent("Web: ", poslanecEntity.getWeb());
    }

    private HorizontalLayout getValueWithLabelComponent(String label, String value) {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        Label labelLabel = new Label(label);

        labelLabel.setWidth(labelWidth.toString());
        Label valueLabel = new Label(value);

        horizontalLayout.add(labelLabel, valueLabel);
        return horizontalLayout;
    }
}
