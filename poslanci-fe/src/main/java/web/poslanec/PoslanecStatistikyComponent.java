package web.poslanec;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import poslanciDB.entity.PoslanecEntity;
import poslanciDB.entity.PoslanecStatistikyEntity;
import poslanciDB.service.PoslanecEntityService;
import web.Helper;

public class PoslanecStatistikyComponent extends VerticalLayout {
    private PoslanecEntity poslanecEntity;
    private PoslanecStatistikyEntity poslanecStatistikyEntity;

    public PoslanecStatistikyComponent(PoslanecEntity poslanecEntity) {
        this.poslanecEntity = poslanecEntity;
        this.poslanecStatistikyEntity = poslanecEntity.getPoslanecStatistikyByIdPoslanec();

        if(poslanecStatistikyEntity != null)
        {
            add(new Label("STATISTIKY:"), getWordCount(), getSentiment());
        }
    }

    private HorizontalLayout getWordCount(){
        return Helper.getValueWithLabelComponent("Počet slov: ",
                poslanecStatistikyEntity.getPocetSlov().toString());
    }

    private HorizontalLayout getSentiment(){
        return Helper.getValueWithLabelComponent("Celkový sentiment: ",
                poslanecStatistikyEntity.getSentiment().toString());
    }
}
