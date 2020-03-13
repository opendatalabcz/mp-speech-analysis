package web.osobyCompare;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import poslanciDB.entity.OsobyEntity;
import poslanciDB.entity.PoslanecEntity;
import web.chart.OsobaData;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class OsobyStatsBySeasonsComponent extends VerticalLayout {
    Set<OsobyEntity> set = null;


    public OsobyStatsBySeasonsComponent(Set<OsobyEntity> set) {
        this.set = set;
        initialize();
    }

    private void initialize() {
        removeAll();
        if (set != null) {
            Integer beginPeriod = 1;
            Integer endPeriod = OsobaData.getEndSessonNumber(set);

            for(int i = beginPeriod; i <= endPeriod; i++) {
                List<PoslanecEntity> list = new ArrayList<>();
                for(OsobyEntity osobyEntity : set) {
                    PoslanecEntity poslanecEntity = OsobaData.getPoslanecByPeriodNumber(osobyEntity, i);
                    if(poslanecEntity != null) {
                        list.add(poslanecEntity);
                    }
                }
                if(!list.isEmpty()) {
                    add(new PoslanciCompareStatsComponent(list));
                }
            }
        }
    }

    public void refresh(Set<OsobyEntity> set) {
        this.set = set;
        initialize();
    }
}
