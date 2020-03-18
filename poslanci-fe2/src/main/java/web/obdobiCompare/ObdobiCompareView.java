package web.obdobiCompare;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import poslanciDB.entity.OrganyEntity;
import poslanciDB.service.OrganyEntityService;

import java.util.List;

public class ObdobiCompareView extends VerticalLayout {
    OrganyEntityService organyEntityService = new OrganyEntityService();
    List<OrganyEntity> list;

    public ObdobiCompareView() {
        initialize();
        add(new ObdobiCompareChartsComponent(list));
    }

    private void initialize() {
        list = organyEntityService.getAllChamberSeasons();
    }
}
