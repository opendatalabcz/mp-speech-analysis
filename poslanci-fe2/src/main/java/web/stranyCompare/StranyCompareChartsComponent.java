package web.stranyCompare;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import poslanciDB.entity.OrganyEntity;
import poslanciDB.entity.PoslanecEntity;
import web.Helper;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static web.chart.StranyBarChart.*;

public class StranyCompareChartsComponent extends VerticalLayout {
    private final OrganyEntity obdobi;
    Map<OrganyEntity, Set<PoslanecEntity>> map = new HashMap<>();

    public StranyCompareChartsComponent(OrganyEntity obdobi) {
        this.obdobi = obdobi;
        initialize();
        add(getPartyPocetSlovDiv(map, obdobi), getPartyAveragePocetSlovDiv(map, obdobi),
                getPartyMedianPocetSlovDiv(map, obdobi),
                getPartyTotalSentimentDiv(map, obdobi), getPartyAverageSentimentDiv(map, obdobi),
                getPartyMedianSentimentDiv(map, obdobi));
    }

    private void initialize() {
        map = Helper.getStranyPoslanecMapInObdobi(obdobi);
    }
}
