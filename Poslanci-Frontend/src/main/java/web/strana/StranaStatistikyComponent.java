package web.strana;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import poslanciDB.entity.OrganyEntity;
import poslanciDB.entity.PoslanecEntity;
import poslanciDB.entity.PoslanecStatistikyEntity;
import poslanciDB.entity.PoslanecStatistikyMesicEntity;
import web.Colors;
import web.Helper;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

import static web.Helper.*;
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
        Label label = new Label("STATISTIKY:");
        label.getElement().getStyle().set("color", Colors.getHighlightColorString()).set("text-decoration", "underline");
        add(label);
        addTextStats();
        add(grid, new HorizontalLayout(getStranaZminkyStranyDiv(obdobi, strana),
                getStranaZminkyStranyDividedByPoslanecCountDiv(obdobi, strana))
        );
    }

    private void addTextStats() {
        Label pocet = new Label("Počet poslanců: " + poslanci.size());
        Label slova = new Label("Slova: průměr = " + getAveragePocetSlovRound(poslanci) +
                "; medián = " + getMedianPocetSlov(poslanci));
        //Label slovaPrumer = new Label("\t - průměr:");
        //Label slovaMedian = new Label("\t - medián:");
        Label sentiment = new Label("Sentiment: průměr = " + getAverageSentimentRound(poslanci) +
                "; medián = " + getMedianSentimentRound(poslanci));
        //Label sentimentPrumer = new Label("\t - průměr:");
        //Label sentimentMedian = new Label("\t - medián:");
        add(pocet, slova, sentiment);

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
        grid.addColumn(this::getPoslanecPocetPosSlov).setHeader("Počet pozitivních slov").setSortable(true).setResizable(true);
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
