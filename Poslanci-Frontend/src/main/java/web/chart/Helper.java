package web.chart;

import be.ceau.chart.color.Color;
import be.ceau.chart.dataset.BarDataset;
import be.ceau.chart.options.BarOptions;
import be.ceau.chart.options.Title;
import be.ceau.chart.options.scales.BarScale;
import be.ceau.chart.options.scales.GridLines;
import be.ceau.chart.options.scales.XAxis;
import be.ceau.chart.options.scales.YAxis;
import be.ceau.chart.options.ticks.LinearTicks;
import org.apache.commons.lang3.ArrayUtils;
import poslanciDB.entity.OrganyEntity;
import poslanciDB.entity.PoslanecEntity;
import web.Colors;
import web.monthYear.MonthYear;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Helper {
    public static boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static List<String> getMonthLabelsList(MonthYear monthYearCurrent, MonthYear monthYearEnd) {
        List<String> dates = new ArrayList<>();
        if(monthYearCurrent == null || monthYearEnd == null) return null;

        while(monthYearEnd.greaterThan(monthYearCurrent) || monthYearEnd.equals(monthYearCurrent)) {
            dates.add(monthYearCurrent.toString());
            monthYearCurrent.increase();
        }
        return dates;
    }

    public static List<String> getPeriodLabelList(Integer beginPeriod, Integer endPeriod) {
        List<String> list = new ArrayList<>();
        if(beginPeriod == null || endPeriod == null) return null;

        for(int i = beginPeriod; i <= endPeriod; i++) {
            list.add("PSP" + i);
        }
        return list;
    }

    public static double[] getDoublesArrayFromList(List<Double> doubles) {
        Double[] a = doubles.stream().toArray(Double[]::new);
        return ArrayUtils.toPrimitive(a);
    }

    public static int[] getIntsArrayFromList(List<Integer> ints) {
        Integer[] a = ints.stream().toArray(Integer[]::new);
        return ArrayUtils.toPrimitive(a);
    }

    public static BarDataset getBarDataSet(double[] doublesArr, String label, Color color) {
        return new BarDataset()
                .setLabel(label)
                .setData(doublesArr)
                .addBackgroundColor(color)
                .setBorderWidth(2);
    }

    public static BarDataset getBarDataSet(int[] doublesArr, String label, Color color) {
        return new BarDataset()
                .setLabel(label)
                .setData(doublesArr)
                .addBackgroundColor(color)
                .setBorderWidth(2);
    }

    public static GridLines getGridLines() {
        GridLines gridLines = new GridLines();
        Colors colors = new Colors();
        gridLines.addColor(colors.getGridLinesColor());
        gridLines.setDisplay(true);
        return gridLines;
    }

    public static BarOptions getBarOptions(String label) {
        BarScale scale = new BarScale();
        scale.addyAxes(new YAxis<LinearTicks>().setStacked(false));
        scale.addxAxes(new XAxis<LinearTicks>().setStacked(false).setGridLines(getGridLines()));

        BarOptions options = new BarOptions();
        options.setResponsive(true)
                .setScales(scale)
                .setTitle(new Title().setText(label).setDisplay(true).setFontColor(Colors.getChartLabelColor()));

        return options;
    }

    public static BarOptions getBarOptionsWithBeginZero(String label) {
        BarScale scale = new BarScale();
        scale.addyAxes(new YAxis<LinearTicks>().setStacked(false).setTicks(new LinearTicks().setBeginAtZero(true)));
        scale.addxAxes(new XAxis<LinearTicks>().setStacked(false).setGridLines(getGridLines()));

        BarOptions options = new BarOptions();
        options.setResponsive(true)
                .setScales(scale)
                .setTitle(new Title().setText(label).setDisplay(true).setFontColor(Colors.getChartLabelColor()));

        return options;
    }

    public static List<String> getPartyNames(Set<OrganyEntity> keySet) {
        List<String> labels = new ArrayList<>();
        keySet.forEach(party -> {
            if(party != null && party.getZkratka() != null && !party.getZkratka().isEmpty())
                labels.add(party.getZkratka());
            else
                labels.add("\"Strana neurčena\"");
        });
        return labels;
    }

    public static Integer getPoslanciInStranaCount(OrganyEntity obdobi, OrganyEntity strana) {
        if(obdobi == null || strana == null)
            return null;

        Integer count = 0;
        for(PoslanecEntity poslanecEntity : strana.getPoslanecsKandidatkaByIdOrgan()) {
            if(poslanecEntity.getOrganyByIdObdobi() == obdobi)
                count++;
        }
        return count;
    }
}
