package web.poslanec;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import poslanciDB.entity.PoslanecEntity;
import poslanciDB.entity.PoslanecStatistikyEntity;
import web.Colors;
import web.Helper;

import java.util.*;

import static web.chart.PoslanecBarChart.*;

public class PoslanecStatistikyComponent extends VerticalLayout {
    private PoslanecEntity poslanecEntity;
    private PoslanecStatistikyEntity poslanecStatistikyEntity;

    public PoslanecStatistikyComponent(PoslanecEntity poslanecEntity) {
        this.poslanecEntity = poslanecEntity;
        this.poslanecStatistikyEntity = poslanecEntity.getPoslanecStatistikyByIdPoslanec();

        if(poslanecStatistikyEntity != null)
        {
            List<PoslanecEntity> list = new ArrayList<>();
            list.add(poslanecEntity);
            Label label = new Label("STATISTIKY:");
            label.getElement().getStyle().set("color", Colors.getHighlightColorString()).set("text-decoration", "underline");
            removeAll();
            add(label, getWordCount(), getSentiment(),
                    new PoslanecTopSlovaComponent(poslanecStatistikyEntity),
                    new PoslanecZminkyComponent(poslanecEntity),
                    getPoslanecPocetSlovMesicDiv(list),
                    getPoslanecSentimentMesicDiv(list),
                    new PoslanecStatistikySchuzeComponent(poslanecEntity)
                    );
        }
    }

    private HorizontalLayout getWordCount(){
        return Helper.getValueWithLabelComponent("Počet slov: ",
                poslanecStatistikyEntity.getPocetSlov().toString() +
                        "\t(průměrný počet slov poslance ve volebním období: " +
                        Helper.getAveragePocetSlov(poslanecEntity.getOrganyByIdObdobi().getPoslanecsObdobiByIdOrgan()) +
                        ")"
        );
    }

    private HorizontalLayout getSentiment(){
        return Helper.getValueWithLabelComponent("Celkový sentiment: ",
                Helper.getRoundDouble(poslanecStatistikyEntity.getSentiment()) +
                        "\t(průměrný sentiment poslance ve volebním období: " +
                        Helper.getAverageSentimentRound(poslanecEntity.getOrganyByIdObdobi().getPoslanecsObdobiByIdOrgan()) +
                        ")"
        );
    }
}
