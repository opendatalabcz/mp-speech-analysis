package creator;

import helper.MathHelper;
import poslanciDB.entity.*;
import poslanciDB.service.OrganyEntityService;
import poslanciDB.service.OsobyEntityService;
import poslanciDB.service.PoslanecStatistikyEntityService;
import poslanciDB.service.PoslanecStatistikyMesicEntityService;

import java.sql.Date;
import java.util.*;

public class PoslanecStatistikyMesicCreator {
    private static OrganyEntityService organyEntityService = new OrganyEntityService();
    private static PoslanecStatistikyMesicEntityService poslanecStatistikyMesicEntityService = new PoslanecStatistikyMesicEntityService();
    private static PoslanecStatistikyEntityService poslanecStatistikyEntityService = new PoslanecStatistikyEntityService();

    public static void main(String[] args) {
        OrganyEntity season = organyEntityService.find(172);
        processAllPoslanec("PSP8");
    }

    public static void processAllPoslanec(String seasonString) {
        System.out.println("----PoslanecStatistikyMesicCreator----");
        OrganyEntity season = helper.EntityHelper.getSeason(seasonString); //TODO season == null

        Collection<PoslanecEntity> poslanecEntityCollection = season.getPoslanecsObdobiByIdOrgan();
        for(PoslanecEntity poslanecEntity : poslanecEntityCollection) {
            System.out.println("PoslanecStatistikyMesicCreator - poslanec:" + poslanecEntity.toString());
            //poslanecStatistikyMesicEntityService.multiBegin();
            processOnePerson(poslanecEntity);
            //poslanecStatistikyMesicEntityService.multiCommit();
        }
    }

    public static void processOnePerson(PoslanecEntity poslanecEntity) {
        Collection<ProjevEntity> projevEntityCollection = poslanecEntity.getProjevsByIdPoslanec();
        List<ProjevEntity> projevyAllList = new ArrayList<>(projevEntityCollection);
        Set<ProjevEntity> projevyMesicSet;
        List<PoslanecStatistikyMesicEntity> mesicList = new ArrayList<>();
        while(!projevyAllList.isEmpty()) {
            projevyMesicSet = new HashSet<>();
            ProjevEntity projevFirst = projevyAllList.get(0);
            projevyMesicSet.add(projevFirst);
            for(int i = 0; i < projevyAllList.size(); i++) {
                if(equalsMonthAndYear(projevFirst.getBodByIdBod().getDatum(),
                        projevyAllList.get(i).getBodByIdBod().getDatum())) {
                    projevyMesicSet.add(projevyAllList.get(i));
                }
            }
            projevyAllList.removeAll(projevyMesicSet);

            Integer pocetPosSlov = 0, pocetNegSlov = 0, pocetSlov = 0;
            for(ProjevEntity projevEntity : projevyMesicSet) {
                pocetPosSlov += projevEntity.getPocetPosSlov();
                pocetNegSlov += projevEntity.getPocetNegSlov();
                pocetSlov += projevEntity.getPocetSlov();
            }
            Double sentiment = MathHelper.countSentiment(pocetPosSlov, pocetNegSlov);
            Calendar cal = Calendar.getInstance();
            cal.setTime(projevFirst.getBodByIdBod().getDatum());
            Integer day = cal.get(Calendar.DAY_OF_MONTH);
            Integer month = cal.get(Calendar.MONTH);
            Integer year = cal.get(Calendar.YEAR);

            String dateString = year.toString() + "-" + String.format("%02d", month+1) + "-01";
            java.sql.Date date = Date.valueOf(dateString);
            PoslanecStatistikyMesicEntity poslanecStatistikyMesicEntity = new PoslanecStatistikyMesicEntity(date, pocetSlov,
                    pocetPosSlov, pocetNegSlov, sentiment, poslanecEntity.getPoslanecStatistikyByIdPoslanec());
            mesicList.add(poslanecStatistikyMesicEntity);
            //poslanecStatistikyMesicEntityService.multiCreate(poslanecStatistikyMesicEntity);
        }
        PoslanecStatistikyEntity poslanecStatistikyEntity = poslanecEntity.getPoslanecStatistikyByIdPoslanec();
        if(poslanecStatistikyEntity != null) {
            poslanecStatistikyEntity.setPoslanecStatistikyMesicsByIdPoslanec(mesicList);
            poslanecStatistikyEntityService.createOrUpdate(poslanecStatistikyEntity);
        }
    }

    public static List<PoslanecStatistikyMesicEntity> getPoslanecMonthStats(PoslanecEntity poslanecEntity) {
        Collection<ProjevEntity> projevEntityCollection = poslanecEntity.getProjevsByIdPoslanec();
        List<ProjevEntity> projevyAllList = new ArrayList<>(projevEntityCollection);
        Set<ProjevEntity> projevyMesicSet;
        List<PoslanecStatistikyMesicEntity> mesicList = new ArrayList<>();
        while(!projevyAllList.isEmpty()) {
            projevyMesicSet = new HashSet<>();
            ProjevEntity projevFirst = projevyAllList.get(0);
            projevyMesicSet.add(projevFirst);
            for(int i = 0; i < projevyAllList.size(); i++) {
                if(equalsMonthAndYear(projevFirst.getBodByIdBod().getDatum(),
                        projevyAllList.get(i).getBodByIdBod().getDatum())) {
                    projevyMesicSet.add(projevyAllList.get(i));
                }
            }
            projevyAllList.removeAll(projevyMesicSet);

            Integer pocetPosSlov = 0, pocetNegSlov = 0, pocetSlov = 0;
            for(ProjevEntity projevEntity : projevyMesicSet) {
                pocetPosSlov += projevEntity.getPocetPosSlov();
                pocetNegSlov += projevEntity.getPocetNegSlov();
                pocetSlov += projevEntity.getPocetSlov();
            }
            Double sentiment = MathHelper.countSentiment(pocetPosSlov, pocetNegSlov);
            Calendar cal = Calendar.getInstance();
            cal.setTime(projevFirst.getBodByIdBod().getDatum());
            Integer day = cal.get(Calendar.DAY_OF_MONTH);
            Integer month = cal.get(Calendar.MONTH);
            Integer year = cal.get(Calendar.YEAR);

            String dateString = year.toString() + "-" + String.format("%02d", month+1) + "-01";
            java.sql.Date date = Date.valueOf(dateString);
            PoslanecStatistikyMesicEntity poslanecStatistikyMesicEntity = new PoslanecStatistikyMesicEntity(date, pocetSlov,
                    pocetPosSlov, pocetNegSlov, sentiment, poslanecEntity.getPoslanecStatistikyByIdPoslanec());
            mesicList.add(poslanecStatistikyMesicEntity);
            //poslanecStatistikyMesicEntityService.multiCreate(poslanecStatistikyMesicEntity);
        }
        return mesicList;
    }

    private static boolean equalsMonthAndYear(java.sql.Date date1, java.sql.Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        boolean sameMonth = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
        return sameMonth;
    }
}
