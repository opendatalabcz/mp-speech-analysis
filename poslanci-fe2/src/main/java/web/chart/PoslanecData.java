package web.chart;

import org.apache.commons.lang3.ArrayUtils;
import poslanciDB.entity.PoslanecEntity;
import poslanciDB.entity.PoslanecStatistikyEntity;
import poslanciDB.entity.PoslanecStatistikyMesicEntity;
import web.monthYear.MonthYear;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static web.monthYear.Helper.getMonthFromSQLDate;
import static web.monthYear.Helper.getYearFromSQLDate;

public class PoslanecData {

    public static List<String> getMonthLabelsList(PoslanecEntity poslanecEntity) {
        MonthYear monthYearCurrent = new MonthYear(poslanecEntity.getOrganyByIdObdobi().getOdOrgan());
        MonthYear monthYearEnd = new MonthYear(poslanecEntity.getOrganyByIdObdobi().getDoOrgan());
        List<String> dates = new ArrayList<>();

        while(monthYearEnd.greaterThan(monthYearCurrent) || monthYearEnd.equals(monthYearCurrent)) {
            dates.add(monthYearCurrent.toString());
            monthYearCurrent.increase();
        }
        return dates;
    }

    public static double[] getPoslanecDoublesArray(PoslanecEntity poslanecEntity) {
        Map<MonthYear, Double> dateDoubleMap = getPoslanecSentimentMap(poslanecEntity.getPoslanecStatistikyByIdPoslanec());
        MonthYear monthYearCurrent = new MonthYear(poslanecEntity.getOrganyByIdObdobi().getOdOrgan());
        MonthYear monthYearEnd = new MonthYear(poslanecEntity.getOrganyByIdObdobi().getDoOrgan());
        List<Double> doubles = new ArrayList<>();

        while(monthYearEnd.greaterThan(monthYearCurrent) || monthYearEnd.equals(monthYearCurrent)) {
            Double num = dateDoubleMap.get(monthYearCurrent);
            if(num == null) {
                num = 0.0;
            }
            doubles.add(num);
            monthYearCurrent.increase();
        }
        Double[] a = doubles.stream().toArray(Double[]::new);
        double[] doublesArr = ArrayUtils.toPrimitive(a);
        return doublesArr;
    }

    public static Map<MonthYear, Double> getPoslanecSentimentMap(PoslanecStatistikyEntity poslanecStatistikyEntity) {
        Map<MonthYear, Double> dateDoubleMap = new HashMap<>();

        for(PoslanecStatistikyMesicEntity monthStats : poslanecStatistikyEntity.getPoslanecStatistikyMesicsByIdPoslanec()) {
            Date date = monthStats.getMesic();
            MonthYear monthYear = new MonthYear(getMonthFromSQLDate(date), getYearFromSQLDate(date));
            Double num = monthStats.getSentiment();
            dateDoubleMap.put(monthYear, num);
        }
        return  dateDoubleMap;
    }

    public static Map<MonthYear, Integer> getPoslanecPocetSlovMap(PoslanecStatistikyEntity poslanecStatistikyEntity) {
        Map<MonthYear, Integer> dateDoubleMap = new HashMap<>();

        for(PoslanecStatistikyMesicEntity monthStats : poslanecStatistikyEntity.getPoslanecStatistikyMesicsByIdPoslanec()) {
            Date date = monthStats.getMesic();
            MonthYear monthYear = new MonthYear(getMonthFromSQLDate(date), getYearFromSQLDate(date));
            Integer num = monthStats.getPocetSlov();
            dateDoubleMap.put(monthYear, num);
        }
        return  dateDoubleMap;
    }
}
