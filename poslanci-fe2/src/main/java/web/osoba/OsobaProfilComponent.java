package web.osoba;

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
        add(getMostCurrentProfil(),
                new HorizontalLayout(OsobyBarChart.getOsobySentimentPeriodDiv(set), OsobyBarChart.getOsobyPocetSlovPeriodDiv(set)));
    }

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
        return new PoslanecProfilComponent(poslanecEnt);
    }
}
