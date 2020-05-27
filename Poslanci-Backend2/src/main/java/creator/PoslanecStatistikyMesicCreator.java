package creator;

import helper.MathHelper;
import poslanciDB.entity.PoslanecEntity;
import poslanciDB.entity.PoslanecStatistikyMesicEntity;
import poslanciDB.entity.ProjevEntity;

import java.sql.Date;
import java.util.*;

public class PoslanecStatistikyMesicCreator {

    public static List<PoslanecStatistikyMesicEntity> getPoslanecMonthStats(PoslanecEntity poslanecEntity) {
        Collection<ProjevEntity> projevEntityCollection = poslanecEntity.getProjevsByIdPoslanec();
        List<ProjevEntity> projevyAllList = new ArrayList<>(projevEntityCollection);
        Set<ProjevEntity> projevyMesicSet;
        List<PoslanecStatistikyMesicEntity> mesicList = new ArrayList<>();

        //program zpracovava projevy, dokud nejake jsou
        while(!projevyAllList.isEmpty()) {
            projevyMesicSet = new HashSet<>();
            ProjevEntity projevFirst = projevyAllList.get(0);
            projevyMesicSet.add(projevFirst);
            //program prida prvni projev a nasledne vsechny projevy pronesene ve stejnem mesici
            for(int i = 0; i < projevyAllList.size(); i++) {
                if(equalsMonthAndYear(projevFirst.getBodByIdBod().getDatum(),
                        projevyAllList.get(i).getBodByIdBod().getDatum())) {
                    projevyMesicSet.add(projevyAllList.get(i));
                }
            }
            //projevy z prave zpracovavaneho mesice smaze z puvodniho seznamu
            projevyAllList.removeAll(projevyMesicSet);

            //spocita statisticke hodnoty pro mesic
            Integer pocetPosSlov = 0, pocetNegSlov = 0, pocetSlov = 0;
            for(ProjevEntity projevEntity : projevyMesicSet) {
                pocetPosSlov += projevEntity.getPocetPosSlov();
                pocetNegSlov += projevEntity.getPocetNegSlov();
                pocetSlov += projevEntity.getPocetSlov();
            }
            Double sentiment = MathHelper.countSentiment(pocetPosSlov, pocetNegSlov);
            Calendar cal = Calendar.getInstance();
            cal.setTime(projevFirst.getBodByIdBod().getDatum());
            Integer month = cal.get(Calendar.MONTH);
            Integer year = cal.get(Calendar.YEAR);

            String dateString = year.toString() + "-" + String.format("%02d", month+1) + "-01";
            Date date = Date.valueOf(dateString);
            //vytvori entitu, do ktere informace nasklada a prida do seznamus vysledky
            PoslanecStatistikyMesicEntity poslanecStatistikyMesicEntity = new PoslanecStatistikyMesicEntity(date, pocetSlov,
                    pocetPosSlov, pocetNegSlov, sentiment, poslanecEntity.getPoslanecStatistikyByIdPoslanec());
            mesicList.add(poslanecStatistikyMesicEntity);
        }
        //kdyz je seznam projevu prazdny, tak vraci seznam zpracovanych mesicnich statistik
        return mesicList;
    }

    private static boolean equalsMonthAndYear(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
    }
}
