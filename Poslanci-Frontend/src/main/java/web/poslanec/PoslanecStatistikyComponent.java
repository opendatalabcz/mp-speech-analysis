package web.poslanec;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import poslanciDB.entity.PoslanecEntity;
import poslanciDB.entity.PoslanecStatistikyEntity;
import web.Colors;
import web.Helper;

import java.util.function.Consumer;

public class PoslanecStatistikyComponent extends VerticalLayout {
    private PoslanecEntity poslanecEntity;
    private PoslanecStatistikyEntity poslanecStatistikyEntity;

    public PoslanecStatistikyComponent(PoslanecEntity poslanecEntity, Consumer<PoslanecEntity> switchToPoslanec) {
        this.poslanecEntity = poslanecEntity;
        this.poslanecStatistikyEntity = poslanecEntity.getPoslanecStatistikyByIdPoslanec();

        if(poslanecStatistikyEntity != null)
        {
            Label label = new Label("STATISTIKY:");
            label.getElement().getStyle().set("color", Colors.getHighlightColorString()).set("text-decoration", "underline");
            removeAll();
            add(label, getWordCount(), getSentiment(),
                    new PoslanecTopSlovaComponent(poslanecStatistikyEntity),
                    new PoslanecZminkyComponent(poslanecEntity, switchToPoslanec),
                    new PoslanecChartsComponent(poslanecEntity),
                    new PoslanecProjevyComponent(poslanecEntity)
                    );
        }
    }

    private HorizontalLayout getWordCount(){
        return Helper.getValueWithLabelComponent("Počet slov: ",
                poslanecStatistikyEntity.getPocetSlov().toString() +
                        "\t(průměrný počet slov poslance ve volebním období: " +
                        Helper.getAveragePocetSlovRound(poslanecEntity.getOrganyByIdObdobi().getPoslanecsObdobiByIdOrgan()) +
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
