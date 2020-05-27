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

    //vrati poslance z uciteho obdobi od urcite osoby
    public static PoslanecEntity getPoslanecByPeriodNumber(OsobyEntity osobyEntity, Integer period) {
        for(PoslanecEntity poslanecEntity : osobyEntity.getPoslanecsByIdOsoba()) {
            if(poslanecEntity.getOrganyByIdObdobi().getZkratka().equals("PSP" + period)) {
                return poslanecEntity;
            }
        }
        return null;
    }

    //pripravuje data pro stacked bar graf
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
