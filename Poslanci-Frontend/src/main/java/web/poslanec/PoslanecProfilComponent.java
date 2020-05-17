package web.poslanec;

import be.ceau.chart.BarChart;
import be.ceau.chart.color.Color;
import be.ceau.chart.data.BarData;
import be.ceau.chart.dataset.BarDataset;
import be.ceau.chart.javascript.JavaScriptFunction;
import be.ceau.chart.options.BarOptions;
import be.ceau.chart.options.Legend;
import be.ceau.chart.options.Title;
import com.syndybat.chartjs.ChartJs;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.apache.commons.lang3.ArrayUtils;
import poslanciDB.entity.OsobyEntity;
import poslanciDB.entity.PoslanecEntity;
import poslanciDB.entity.PoslanecStatistikyMesicEntity;
import web.Colors;
import web.Helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PoslanecProfilComponent extends VerticalLayout {
    private PoslanecEntity poslanecEntity;
    private OsobyEntity osobyEntity;

    public PoslanecProfilComponent(PoslanecEntity poslanecEntity) {
        this.poslanecEntity = poslanecEntity;
        this.osobyEntity = poslanecEntity.getOsobyByIdOsoba();
        Label label = new Label("PROFIL:");
        label.getElement().getStyle().set("color", Colors.getHighlightColorString()).set("text-decoration", "underline");
        add(label, getName(), getParty(), getBirthDate(), getAdresa(), getPoslanecURL(),
                getEmail(), getWebPage(), getFacebookPage());
    }

    private HorizontalLayout getName() {
        return Helper.getValueWithLabelComponent("Jméno: ", poslanecEntity.toString());
    }

    private HorizontalLayout getParty() {
        if(poslanecEntity.getOrganyByIdKandidatka() != null)
            return Helper.getValueWithLabelComponent("Kandidátka: ", poslanecEntity.getOrganyByIdKandidatka().getNazevOrganuCz());
        else
            return Helper.getValueWithLabelComponent("Kandidátka: ", "");
    }

    private HorizontalLayout getBirthDate() {
        return Helper.getValueWithLabelComponent("Datum narození: ", osobyEntity.getNarozeni().toString());
    }

    private HorizontalLayout getEmail() {
        return Helper.getValueWithLabelComponent("Email: ", poslanecEntity.getEmail());
    }

    private HorizontalLayout getWebPage() {
        return Helper.getValueWithLabelComponent("Web: ", poslanecEntity.getWeb());
    }

    private HorizontalLayout getFacebookPage() {
        return Helper.getValueWithLabelComponent("Facebook: ", poslanecEntity.getFacebook());
    }

    private HorizontalLayout getAdresa() {
        return Helper.getValueWithLabelComponent("Adresa kanceláře: ",
                poslanecEntity.getUlice() + ", " + poslanecEntity.getObec() + ", " + poslanecEntity.getPsc());
    }

    private HorizontalLayout getPoslanecURL() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(new Label("Web poslance na psp.cz: "));
        String urlString = "https://www.psp.cz/sqw/detail.sqw?id=" + poslanecEntity.getOsobyByIdOsoba().getIdOsoba();
        Button url = new Button("Odkaz");
        url.addClickListener(event -> {
            String javaScript = "window.open(\"" + urlString + "\", \"_blank\");";
            UI.getCurrent().getPage().executeJavaScript(javaScript);
        });
        horizontalLayout.add(url);
        return horizontalLayout;
    }
}
