package web.poslanec;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import poslanciDB.entity.PoslanecEntity;
import poslanciDB.entity.PoslanecStatistikyEntity;
import poslanciDB.entity.PoslanecStatistikyMesicEntity;

import web.Helper;

public class PoslanecStatistikyComponent extends VerticalLayout {
    private PoslanecEntity poslanecEntity;
    private PoslanecStatistikyEntity poslanecStatistikyEntity;

    public PoslanecStatistikyComponent(PoslanecEntity poslanecEntity) {
        this.poslanecEntity = poslanecEntity;
        this.poslanecStatistikyEntity = poslanecEntity.getPoslanecStatistikyByIdPoslanec();

        if(poslanecStatistikyEntity != null)
        {
            add(new Label("STATISTIKY:"), getWordCount(), getSentiment(), getStatsInMonths());
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

    private VerticalLayout getStatsInMonths() {
        VerticalLayout verticalLayout = new VerticalLayout();

        for(PoslanecStatistikyMesicEntity mesic : poslanecStatistikyEntity.getPoslanecStatistikyMesicsByIdPoslanec())
        {
            HorizontalLayout horizontalLayout = new HorizontalLayout();
            horizontalLayout.add(new Label("Mesic: "), new Label(mesic.getMesic().toString()), new Label("-----"));
            horizontalLayout.add(new Label("Pocet slov: "), new Label(mesic.getPocetSlov().toString()),
                    new Label("-----"));
            horizontalLayout.add(new Label("Pocet pos slov: "), new Label(mesic.getPocetPosSlov().toString()),
                    new Label("-----"));
            horizontalLayout.add(new Label("Pocet neg slov: "), new Label(mesic.getPocetNegSlov().toString()),
                    new Label("-----"));
            horizontalLayout.add(new Label("Sentiment: "), new Label(mesic.getSentiment().toString()));

            verticalLayout.add(horizontalLayout);
        }

        return verticalLayout;
    }
}
