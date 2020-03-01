package creator;

import poslanciDB.entity.OsobyEntity;
import poslanciDB.entity.PoslanecEntity;
import poslanciDB.entity.PoslanecStatistikyMesicEntity;
import poslanciDB.entity.ProjevEntity;
import poslanciDB.service.OsobyEntityService;
import poslanciDB.service.PoslanecStatistikyMesicEntityService;

import java.sql.Date;
import java.util.*;

public class PoslanecStatistikyMesicCreator {
    private static OsobyEntityService osobyEntityService = new OsobyEntityService();
    private static PoslanecStatistikyMesicEntityService poslanecStatistikyMesicEntityService = new PoslanecStatistikyMesicEntityService();

    public static void main(String[] args) {
        processAllPersons();
    }

    public static void processAllPersons() {
        List osobyEntityList = osobyEntityService.findAll();
        for(Object obj : osobyEntityList) {
            OsobyEntity osobyEntity;
            try {
                osobyEntity = (OsobyEntity) obj;
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
            System.out.println("Osoba(" + osobyEntity.getIdOsoba() + "): " + osobyEntity.getJmeno() + " " +
                    osobyEntity.getPrijmeni());
            Collection<PoslanecEntity> poslanecEntityCollection = osobyEntity.getPoslanecsByIdOsoba();
            for(PoslanecEntity poslanecEntity : poslanecEntityCollection) {
                poslanecStatistikyMesicEntityService.multiBegin();
                processOnePerson(poslanecEntity);
                poslanecStatistikyMesicEntityService.multiCommit();
            }
        }
    }

    public static void processOnePerson(PoslanecEntity poslanecEntity) {
        Collection<ProjevEntity> projevEntityCollection = poslanecEntity.getProjevsByIdPoslanec();
        List<ProjevEntity> projevyAllList = new ArrayList<>(projevEntityCollection);
        Set<ProjevEntity> projevyMesicSet;
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
                pocetPosSlov += projevEntity.getProjevStatistikyByIdProjev().getPocetPosSlov();
                pocetNegSlov += projevEntity.getProjevStatistikyByIdProjev().getPocetNegSlov();
                pocetSlov += projevEntity.getPocetSlov();
            }
            Double sentiment = ((pocetPosSlov * 1.0) + (pocetNegSlov * (-1.0))) / (pocetPosSlov + pocetNegSlov);
            Calendar cal = Calendar.getInstance();
            cal.setTime(projevFirst.getBodByIdBod().getDatum());
            Integer day = cal.get(Calendar.DAY_OF_MONTH);
            Integer month = cal.get(Calendar.MONTH);
            Integer year = cal.get(Calendar.YEAR);

            String dateString = year.toString() + "-" + String.format("%02d", month+1) + "-01";
            java.sql.Date date = Date.valueOf(dateString);
            PoslanecStatistikyMesicEntity poslanecStatistikyMesicEntity = new PoslanecStatistikyMesicEntity(date, pocetSlov,
                    pocetPosSlov, pocetNegSlov, sentiment, poslanecEntity.getPoslanecStatistikyByIdPoslanec());
            poslanecStatistikyMesicEntityService.multiCreate(poslanecStatistikyMesicEntity);
        }
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
