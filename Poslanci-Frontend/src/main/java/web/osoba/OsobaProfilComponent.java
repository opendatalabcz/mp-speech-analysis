package web.osoba;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import poslanciDB.entity.OsobyEntity;
import poslanciDB.entity.PoslanecEntity;
import web.Helper;
import web.chart.OsobyBarChart;
import web.poslanec.PoslanecProfilComponent;

import java.util.HashSet;
import java.util.Set;

public class OsobaProfilComponent extends VerticalLayout {
    private OsobyEntity osobyEntity;

    public OsobaProfilComponent(OsobyEntity osobyEntity) {
        this.osobyEntity = osobyEntity;
        Set<OsobyEntity> set = new HashSet<>();
        set.add(osobyEntity);
        PoslanecProfilComponent poslanecProfilComponent = getMostCurrentProfil();

        if(poslanecProfilComponent != null) {
            add(poslanecProfilComponent,
                    new HorizontalLayout(OsobyBarChart.getOsobySentimentPeriodDiv(set),
                            OsobyBarChart.getOsobyPocetSlovPeriodDiv(set)
                    )
            );
        } else {
            add(getShortOsobaProfil());
        }
    }

    //najde profil pro osobu tak, ze vezme profil nejnovejsiho poslaneckeho mandatu dane osoby
    private PoslanecProfilComponent getMostCurrentProfil() {
        PoslanecEntity poslanecEnt = null;
        for(PoslanecEntity poslanecEntity : osobyEntity.getPoslanecsByIdOsoba()) {
            if(poslanecEntity.getOrganyByIdObdobi().getDoOrgan() == null) {
                poslanecEnt = poslanecEntity;
                break;
            }

            if(poslanecEnt == null || poslanecEnt.getOrganyByIdObdobi().getDoOrgan().getTime() < poslanecEntity.getOrganyByIdObdobi().getDoOrgan().getTime()) {
                poslanecEnt = poslanecEntity;
            }
        }
        PoslanecProfilComponent poslanecProfilComponent = null;
        try {
            poslanecProfilComponent = new PoslanecProfilComponent(poslanecEnt);
        } catch (Exception ignored){}

        return poslanecProfilComponent;
    }

    private VerticalLayout getShortOsobaProfil() {
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.add(Helper.getValueWithLabelComponent("Jméno: ", osobyEntity.toString()));
        verticalLayout.add(Helper.getValueWithLabelComponent("Datum narození: ", osobyEntity.getNarozeni().toString()));
        verticalLayout.add(new Label("Tato osoba nemá a neměla poslanecký mandát."));
        return verticalLayout;
    }
}
