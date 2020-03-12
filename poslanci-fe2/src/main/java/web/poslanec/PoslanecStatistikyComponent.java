package web.poslanec;

import be.ceau.chart.BarChart;
import be.ceau.chart.color.Color;
import be.ceau.chart.data.BarData;
import be.ceau.chart.dataset.BarDataset;
import be.ceau.chart.javascript.JavaScriptFunction;
import be.ceau.chart.options.BarOptions;
import be.ceau.chart.options.Legend;
import be.ceau.chart.options.Title;
import be.ceau.chart.options.scales.*;
import be.ceau.chart.options.ticks.LinearTicks;
import com.syndybat.chartjs.ChartJs;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.apache.commons.lang3.ArrayUtils;
import poslanciDB.entity.PoslanecEntity;
import poslanciDB.entity.PoslanecStatistikyEntity;
import poslanciDB.entity.PoslanecStatistikyMesicEntity;
import poslanciDB.service.PoslanecEntityService;
import web.Helper;
import web.monthYear.MonthYear;

import java.sql.Date;
import java.util.*;

import static web.chart.PoslanecBarChart.*;

public class PoslanecStatistikyComponent extends VerticalLayout {
    private PoslanecEntity poslanecEntity;
    private PoslanecStatistikyEntity poslanecStatistikyEntity;

    public PoslanecStatistikyComponent(PoslanecEntity poslanecEntity) {
        this.poslanecEntity = poslanecEntity;
        this.poslanecStatistikyEntity = poslanecEntity.getPoslanecStatistikyByIdPoslanec();

        PoslanecEntityService poslanecEntityService = new PoslanecEntityService();

        if(poslanecStatistikyEntity != null)
        {
            List<PoslanecEntity> list = new ArrayList<>();
            list.add(poslanecEntityService.find(1537));
            list.add(poslanecEntityService.find(1538));
            list.add(poslanecEntityService.find(1539));
            list.add(poslanecEntityService.find(1540));
            list.add(poslanecEntity);

            add(new Label("STATISTIKY:"), getWordCount(), getSentiment(),
                    new PoslanecStatistikySchuzeComponent(poslanecEntity));
                    /*getTest0Div(),
                    getPoslanecSentimentMesicDiv(list));*/
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
