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
import java.util.function.Consumer;

import static web.chart.ZminkyBarChart.*;

public class StranaStatistikyComponent extends VerticalLayout {
    private OrganyEntity obdobi;
    private OrganyEntity strana;
    private Set<PoslanecEntity> poslanci= new HashSet<>();
    private Grid<PoslanecEntity> grid = new Grid(PoslanecEntity.class);

    public StranaStatistikyComponent(OrganyEntity obdobi, OrganyEntity strana, Consumer<PoslanecEntity> switchToPoslanec) {
        this.obdobi = obdobi;
        this.strana = strana;
        initialize(switchToPoslanec);
        add(new Label("Počet poslanců: " + poslanci.size()), grid,
                getStranaZminkyStranyDiv(obdobi, strana), getStranaZminkyStranyDividedByPoslanecCountDiv(obdobi, strana));
    }

    private void initialize(Consumer<PoslanecEntity> switchToPoslanec) {
        this.setSizeFull();
        for(PoslanecEntity poslanecEntity : strana.getPoslanecsKandidatkaByIdOrgan()) {
            if(poslanecEntity.getOrganyByIdObdobi() == obdobi) {
                poslanci.add(poslanecEntity);
            }
        }
        grid.setItems(poslanci);
        grid.removeAllColumns();
        grid.setWidth("1200px");
        grid.setMaxHeight("600px");
        grid.addColumn(PoslanecEntity::toString).setHeader("Jméno").setSortable(true).setResizable(true).setFlexGrow(2);
        grid.addColumn(this::getPoslanecSentiment).setHeader("Sentiment").setSortable(true).setResizable(true);
        grid.addColumn(this::getPoslanecPocetSlov).setHeader("Počet slov").setSortable(true).setResizable(true);
        grid.addColumn(this::getPoslanecPocetPosSlov).setHeader("Počet positivních slov").setSortable(true).setResizable(true);
        grid.addColumn(this::getPoslanecPocetNegSlov).setHeader("Počet negativních slov").setSortable(true);

        grid.addItemDoubleClickListener(event -> {
            switchToPoslanec.accept(event.getItem());
        });
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
