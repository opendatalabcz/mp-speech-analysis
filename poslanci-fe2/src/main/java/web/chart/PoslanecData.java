package web.chart;

import org.apache.commons.lang3.ArrayUtils;
import poslanciDB.entity.PoslanecEntity;
import poslanciDB.entity.PoslanecStatistikyEntity;
import poslanciDB.entity.PoslanecStatistikyMesicEntity;
import web.monthYear.MonthYear;

import java.sql.Date;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

import static web.chart.Helper.getDoublesArrayFromList;
import static web.chart.Helper.getIntsArrayFromList;
import static web.monthYear.Helper.getMonthFromSQLDate;
import static web.monthYear.Helper.getYearFromSQLDate;

public class PoslanecData {

    public static List<String> getMonthLabelsList(PoslanecEntity poslanecEntity) {
        MonthYear monthYearCurrent = new MonthYear(poslanecEntity.getOrganyByIdObdobi().getOdOrgan());
        MonthYear monthYearEnd = new MonthYear(poslanecEntity.getOrganyByIdObdobi().getDoOrgan());
        return Helper.getMonthLabelsList(monthYearCurrent, monthYearEnd);
    }

    public static double[] getPoslanecDoublesSentimentArray(PoslanecEntity poslanecEntity) {
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
        return getDoublesArrayFromList(doubles);
    }

    public static int[] getPoslanecIntsPocetSlovArray(PoslanecEntity poslanecEntity) {
        Map<MonthYear, Integer> dateIntegerMap = getPoslanecPocetSlovMap(poslanecEntity.getPoslanecStatistikyByIdPoslanec());
        MonthYear monthYearCurrent = new MonthYear(poslanecEntity.getOrganyByIdObdobi().getOdOrgan());
        MonthYear monthYearEnd = new MonthYear(poslanecEntity.getOrganyByIdObdobi().getDoOrgan());
        List<Integer> integers = new ArrayList<>();

        while(monthYearEnd.greaterThan(monthYearCurrent) || monthYearEnd.equals(monthYearCurrent)) {
            Integer num = dateIntegerMap.get(monthYearCurrent);
            if(num == null) {
                num = 0;
            }
            integers.add(num);
            monthYearCurrent.increase();
        }
        return getIntsArrayFromList(integers);
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

    public static Integer getPoslanecTotalPocetSlov(PoslanecEntity poslanecEntity) {
        if(poslanecEntity.getPoslanecStatistikyByIdPoslanec() != null)
            return poslanecEntity.getPoslanecStatistikyByIdPoslanec().getPocetSlov();
        else
            return 0;
    }

    public static Integer getPoslanecTotalPocetPosSlov(PoslanecEntity poslanecEntity) {
        return getPoslanecTotalPocetXSlov(poslanecEntity, PoslanecStatistikyMesicEntity::getPocetPosSlov);
    }

    public static Integer getPoslanecTotalPocetNegSlov(PoslanecEntity poslanecEntity) {
        return getPoslanecTotalPocetXSlov(poslanecEntity, PoslanecStatistikyMesicEntity::getPocetNegSlov);
    }

    public static Integer getPoslanecTotalPocetPosNegSlov(PoslanecEntity poslanecEntity) {
        return getPoslanecTotalPocetXSlov(poslanecEntity, monthStats -> monthStats.getPocetPosSlov() - monthStats.getPocetNegSlov());
    }

    public static Integer getPoslanecTotalPocetXSlov(PoslanecEntity poslanecEntity, Function<PoslanecStatistikyMesicEntity,Integer> func) {
        Integer slova = 0;
        if(poslanecEntity.getPoslanecStatistikyByIdPoslanec() != null) {
            for(PoslanecStatistikyMesicEntity month : poslanecEntity.getPoslanecStatistikyByIdPoslanec().getPoslanecStatistikyMesicsByIdPoslanec()) {
                slova += func.apply(month);
            }
        }
        return slova;
    }
}
