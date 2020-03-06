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
import javafx.geometry.Pos;
import org.apache.commons.lang3.ArrayUtils;
import poslanciDB.entity.PoslanecEntity;
import poslanciDB.entity.PoslanecStatistikyEntity;
import poslanciDB.entity.PoslanecStatistikyMesicEntity;
import poslanciDB.service.PoslanecEntityService;
import web.Helper;
import web.monthYear.MonthYear;

import java.sql.Date;
import java.util.*;

import static web.chart.BarChart.*;

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

            add(new Label("STATISTIKY:"), getWordCount(), getSentiment(), getStatsInMonths(),
                    getPoslanecSentimentBarChartWrapped(poslanecEntity),
                    getTest0Div(),
                    getTest1Div(list));
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

    private Div wrapToDiv(ChartJs barChartJs)
    {
        Div div = new Div();
        div.add(barChartJs);
        div.setWidth("800px");
        div.setHeight("1000px");
        return div;
    }

    private int getMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
        return month;
    }

    private int getYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        return year;
    }

    private boolean isThisMonth(int month1, int year1, int month2, int year2) {
        if(year1 == year2) {
            if(month2 - 1 == month1) return true;
            else return false;
        } else {
            if(year2 - 1 == year1 && month1 == 11 && month2 == 0) return true;
            else return false;
        }
    }

    private String getStringMesic(int year, int month) {
        if(month == 0) return year + " - " + month;
        else return month + "";
    }

    private ChartJs getBarChart(){
        List<Double> doubles = new ArrayList<>();
        List<String> dates = new ArrayList<>();
        Map<MonthYear, Double> dateDoubleMap = new HashMap<>();

        for(PoslanecStatistikyMesicEntity monthStats : poslanecStatistikyEntity.getPoslanecStatistikyMesicsByIdPoslanec()) {
            Date date = monthStats.getMesic();
            MonthYear monthYear = new MonthYear(getMonth(date), getYear(date));
            Double num = monthStats.getSentiment();
            dateDoubleMap.put(monthYear, num);
        }
        Date dateBegin = poslanecEntity.getOrganyByIdObdobi().getOdOrgan();
        if(dateBegin == null) {
            dateBegin = new Date(Calendar.getInstance().getTime().getTime());
        }
        MonthYear monthYearCurrent = new MonthYear(getMonth(dateBegin), getYear(dateBegin));

        Date dateEnd = poslanecEntity.getOrganyByIdObdobi().getDoOrgan();
        if(dateEnd == null) {
            dateEnd = new Date(Calendar.getInstance().getTime().getTime());
        }
        MonthYear monthYearEnd = new MonthYear(getMonth(dateEnd), getYear(dateEnd));


        while(monthYearEnd.greaterThan(monthYearCurrent) || monthYearEnd.equals(monthYearCurrent)) {
            Double num = dateDoubleMap.get(monthYearCurrent);
            if(num == null) {
                num = 0.0;
            }
            doubles.add(num);
            dates.add(monthYearCurrent.toString());
            monthYearCurrent.increase();
        }

        Double[] a = doubles.stream().toArray(Double[]::new);
        double[] doublesArr = ArrayUtils.toPrimitive(a);

        BarDataset dataset = new BarDataset()
                .setLabel("Sentiment")
                .setData(doublesArr)
                .addBackgroundColor(Color.RED)
                .setBorderWidth(2);

        BarDataset dataset2 = new BarDataset()
                .setLabel("Sentiment2")
                .setData(doublesArr)
                .addBackgroundColor(Color.BLUE)
                .setBorderWidth(2);

        BarData data = new BarData()
                .addLabels(dates.toArray(new String[0]))
                .addDataset(dataset)
                .addDataset(dataset2);

        JavaScriptFunction label = new JavaScriptFunction(
                "\"function(chart) {console.log('test legend');}\""
        );

        LinearTicks ticks = new LinearTicks()
                .setAutoSkip(false)
                .setMin(0)
                .setMax(1)
                .setStepSize(1);
        XAxis axis = new XAxis().setTicks(ticks).setStacked(true);
        BarScale scales = new BarScale().addxAxes(axis);

        BarOptions barOptions = new BarOptions()
                .setResponsive(true)
                .setTitle(new Title().setText("test"))
                .setLegend(new Legend()
                        .setDisplay(true)
                        .setOnClick(label))
                .setScales(scales);

        return new ChartJs(new BarChart(data,barOptions).toJson());
    }
}
