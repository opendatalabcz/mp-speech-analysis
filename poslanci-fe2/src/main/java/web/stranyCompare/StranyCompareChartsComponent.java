package web.stranyCompare;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import poslanciDB.entity.OrganyEntity;
import poslanciDB.entity.PoslanecEntity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static web.chart.StranyBarChart.*;

public class StranyCompareChartsComponent extends VerticalLayout {
    private final OrganyEntity organyEntity;
    Map<OrganyEntity, Set<PoslanecEntity>> map = new HashMap<>();

    public StranyCompareChartsComponent(OrganyEntity organyEntity) {
        this.organyEntity = organyEntity;
        initialize();
        add(new Label(organyEntity.getNazevOrganuCz()),
                getPartyPocetSlovDiv(map, organyEntity), getPartyAveragePocetSlovDiv(map, organyEntity),
                getPartyMedianPocetSlovDiv(map, organyEntity),
                getPartyTotalSentimentDiv(map, organyEntity), getPartyAverageSentimentDiv(map, organyEntity),
                getPartyMedianSentimentDiv(map, organyEntity));
    }

    private void initialize() {
        for(PoslanecEntity poslanecEntity : organyEntity.getPoslanecsObdobiByIdOrgan()) {
            if(!map.containsKey(poslanecEntity.getOrganyByIdKandidatka())) {
                Set<PoslanecEntity> set = new HashSet<>();
                set.add(poslanecEntity);
                map.put(poslanecEntity.getOrganyByIdKandidatka(), set);
            } else {
                Set<PoslanecEntity> set = map.get(poslanecEntity.getOrganyByIdKandidatka());
                set.add(poslanecEntity);
                map.put(poslanecEntity.getOrganyByIdKandidatka(), set);
            }
        }
        int i = 5;
    }
}
