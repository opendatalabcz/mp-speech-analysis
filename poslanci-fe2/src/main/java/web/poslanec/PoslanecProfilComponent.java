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
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.apache.commons.lang3.ArrayUtils;
import poslanciDB.entity.OsobyEntity;
import poslanciDB.entity.PoslanecEntity;
import poslanciDB.entity.PoslanecStatistikyMesicEntity;
import web.Helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
        if(poslanecEntity.getOrganyByIdKandidatka() != null)
            return Helper.getValueWithLabelComponent("Kandidátka: ", poslanecEntity.getOrganyByIdKandidatka().getNazevOrganuCz());
        else
            return Helper.getValueWithLabelComponent("Kandidátka: ", "");
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
}
