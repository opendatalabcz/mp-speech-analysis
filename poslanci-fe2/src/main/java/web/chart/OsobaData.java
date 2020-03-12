package web.chart;

import be.ceau.chart.dataset.BarDataset;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import poslanciDB.entity.OsobyEntity;
import poslanciDB.entity.PoslanecEntity;
import poslanciDB.entity.PoslanecStatistikyEntity;
import poslanciDB.entity.PoslanecStatistikyMesicEntity;
import web.monthYear.MonthYear;

import java.sql.Date;
import java.util.*;

import static web.chart.Helper.tryParseInt;
import static web.monthYear.Helper.getMonthFromSQLDate;
import static web.monthYear.Helper.getYearFromSQLDate;

public class OsobaData {

    public static double[] getOsobaDoublesSentimentArray(OsobyEntity osobyEntity, MonthYear monthYearCurrent, MonthYear monthYearEnd) {
        Map<MonthYear, Double> dateDoubleMap = getOsobaSentimentMap(osobyEntity);
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

    public static Map<MonthYear, Double> getOsobaSentimentMap(OsobyEntity osobyEntity) {
        Map<MonthYear, Double> dateDoubleMap = new HashMap<>();
        for(PoslanecEntity poslanecEntity : osobyEntity.getPoslanecsByIdOsoba()) {
            dateDoubleMap.putAll(PoslanecData.getPoslanecSentimentMap(poslanecEntity.getPoslanecStatistikyByIdPoslanec()));
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

    public static Integer getEndSessonNumber(Set<OsobyEntity> set) {
        int number = 1;

        for(OsobyEntity osobyEntity : set) {
            for(PoslanecEntity poslanecEntity : osobyEntity.getPoslanecsByIdOsoba()) {
                int meetingNum = 1;
                String meetingNumStr = StringUtils.removeStart(poslanecEntity.getOrganyByIdObdobi().getZkratka(), "PSP");
                if(tryParseInt(meetingNumStr)) {
                    meetingNum = Integer.parseInt(meetingNumStr);
                }
                if(meetingNum > number) {
                    number = meetingNum;
                }
            }
        }
        return number;
    }

    public static PoslanecEntity getPoslanecByPeriodNumber(OsobyEntity osobyEntity, Integer period) {
        for(PoslanecEntity poslanecEntity : osobyEntity.getPoslanecsByIdOsoba()) {
            if(poslanecEntity.getOrganyByIdObdobi().getZkratka().equals("PSP" + period)) {
                return poslanecEntity;
            }
        }
        return null;
    }

    public static MonthYear getBeginMonthYear(Set<OsobyEntity> set) {
        Date date = null;
        for(OsobyEntity osobyEntity : set) {
            for(PoslanecEntity poslanecEntity : osobyEntity.getPoslanecsByIdOsoba()) {
                if(date == null || poslanecEntity.getOrganyByIdObdobi().getOdOrgan().getTime() < date.getTime()) {
                    date = poslanecEntity.getOrganyByIdObdobi().getOdOrgan();
                }
            }
        }

        MonthYear monthYear = null;
        if(date != null) {
            monthYear = new MonthYear(date);
        }
        return monthYear;
    }

    public static MonthYear getEndMonthYear(Set<OsobyEntity> set) {
        Date date = null;
        for(OsobyEntity osobyEntity : set) {
            for(PoslanecEntity poslanecEntity : osobyEntity.getPoslanecsByIdOsoba()) {
                if(date == null || poslanecEntity.getOrganyByIdObdobi().getOdOrgan().getTime() > date.getTime()) {
                    date = poslanecEntity.getOrganyByIdObdobi().getOdOrgan();
                }
                if(poslanecEntity.getOrganyByIdObdobi().getOdOrgan() == null) {
                    date = new Date(Calendar.getInstance().getTime().getTime());
                }
            }
        }

        MonthYear monthYear = null;
        if(date != null) {
            monthYear = new MonthYear(date);
        }
        return monthYear;
    }

    public static void processOsobaSlova(OsobyEntity osobyEntity, BarDataset neutralBarDataset, BarDataset posBarDataset,
                                         BarDataset negBarDataset) {
        Integer neutral = 0, pos = 0, neg = 0;
        Integer total = 0;
        for(PoslanecEntity poslanecEntity : osobyEntity.getPoslanecsByIdOsoba()) {
            if(poslanecEntity.getPoslanecStatistikyByIdPoslanec() != null) {
                total += poslanecEntity.getPoslanecStatistikyByIdPoslanec().getPocetSlov();
                for(PoslanecStatistikyMesicEntity month : poslanecEntity.getPoslanecStatistikyByIdPoslanec().getPoslanecStatistikyMesicsByIdPoslanec()) {
                    pos += month.getPocetPosSlov();
                    neg += month.getPocetNegSlov();
                }
            }
        }
        neutral = total - (pos + neg);
        neutralBarDataset.addData(neutral);
        posBarDataset.addData(pos);
        negBarDataset.addData(neg);
    }
}
