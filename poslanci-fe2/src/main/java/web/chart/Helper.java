package web.chart;

import be.ceau.chart.color.Color;
import be.ceau.chart.dataset.BarDataset;
import be.ceau.chart.options.scales.GridLines;
import com.syndybat.chartjs.ChartJs;
import com.vaadin.flow.component.html.Div;
import org.apache.commons.lang3.ArrayUtils;
import web.monthYear.MonthYear;

import java.util.ArrayList;
import java.util.List;

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

        while(monthYearEnd.greaterThan(monthYearCurrent) || monthYearEnd.equals(monthYearCurrent)) {
            dates.add(monthYearCurrent.toString());
            monthYearCurrent.increase();
        }
        return dates;
    }

    public static List<String> getPeriodLabelList(Integer beginPeriod, Integer endPeriod) {
        List<String> list = new ArrayList<>();
        for(int i = beginPeriod; i <= endPeriod; i++) {
            list.add("PSP" + i);
        }
        return list;
    }

    public static double[] getDoublesArrayFromList(List<Double> doubles) {
        Double[] a = doubles.stream().toArray(Double[]::new);
        double[] doublesArr = ArrayUtils.toPrimitive(a);
        return doublesArr;
    }

    public static int[] getIntsArrayFromList(List<Integer> ints) {
        Integer[] a = ints.stream().toArray(Integer[]::new);
        int[] intsArr = ArrayUtils.toPrimitive(a);
        return intsArr;
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
}
