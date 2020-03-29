package web.strana;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import poslanciDB.entity.OrganyEntity;
import poslanciDB.entity.PoslanecEntity;
import poslanciDB.entity.PoslanecStatistikyEntity;
import poslanciDB.entity.PoslanecStatistikyMesicEntity;
import web.Helper;

import java.util.HashSet;
import java.util.Set;

public class StranaStatistikyComponent extends VerticalLayout {
    private OrganyEntity obdobi;
    private OrganyEntity strana;
    private Set<PoslanecEntity> poslanci= new HashSet<>();
    private Grid<PoslanecEntity> grid = new Grid(PoslanecEntity.class);

    public StranaStatistikyComponent(OrganyEntity obdobi, OrganyEntity strana) {
        this.obdobi = obdobi;
        this.strana = strana;
        initialize();
        add(new Label("pocet poslancu: " + poslanci.size()), grid);
    }

    private void initialize() {
        this.setSizeFull();
        for(PoslanecEntity poslanecEntity : strana.getPoslanecsKandidatkaByIdOrgan()) {
            if(poslanecEntity.getOrganyByIdObdobi() == obdobi) {
                poslanci.add(poslanecEntity);
            }
        }
        grid.setItems(poslanci);
        grid.removeAllColumns();
        grid.setWidth("1200px");
        grid.setHeight("600px");
        grid.addColumn(PoslanecEntity::toString).setHeader("Jmeno").setSortable(true).setResizable(true).setFlexGrow(2);
        grid.addColumn(this::getPoslanecSentiment).setHeader("Sentiment").setSortable(true).setResizable(true);
        grid.addColumn(this::getPoslanecPocetSlov).setHeader("Pocet slov").setSortable(true).setResizable(true);
        grid.addColumn(this::getPoslanecPocetPosSlov).setHeader("Pocet positivnich slov").setSortable(true).setResizable(true);
        grid.addColumn(this::getPoslanecPocetNegSlov).setHeader("Pocet negativnich slov").setSortable(true);
    }

    private String getPoslanecSentiment(PoslanecEntity poslanecEntity) {
        PoslanecStatistikyEntity stats = poslanecEntity.getPoslanecStatistikyByIdPoslanec();
        if(stats != null) {
            return Helper.getRoundDouble(stats.getSentiment());
        }
        return "0";
    }

    private Integer getPoslanecPocetSlov(PoslanecEntity poslanecEntity) {
        PoslanecStatistikyEntity stats = poslanecEntity.getPoslanecStatistikyByIdPoslanec();
        if(stats != null) {
            return stats.getPocetSlov();
        }
        return 0;
    }

    private Integer getPoslanecPocetPosSlov(PoslanecEntity poslanecEntity) {
        PoslanecStatistikyEntity stats = poslanecEntity.getPoslanecStatistikyByIdPoslanec();
        Integer pocetPosSlov = 0;
        if(stats != null) {
            for(PoslanecStatistikyMesicEntity mesic : stats.getPoslanecStatistikyMesicsByIdPoslanec()) {
                pocetPosSlov += mesic.getPocetPosSlov();
            }
        }
        return pocetPosSlov;
    }

    private Integer getPoslanecPocetNegSlov(PoslanecEntity poslanecEntity) {
        PoslanecStatistikyEntity stats = poslanecEntity.getPoslanecStatistikyByIdPoslanec();
        Integer pocetNegSlov = 0;
        if(stats != null) {
            for(PoslanecStatistikyMesicEntity mesic : stats.getPoslanecStatistikyMesicsByIdPoslanec()) {
                pocetNegSlov += mesic.getPocetNegSlov();
            }
        }
        return pocetNegSlov;
    }
}
