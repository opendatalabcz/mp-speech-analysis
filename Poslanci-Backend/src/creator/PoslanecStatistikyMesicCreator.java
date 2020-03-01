package creator;

import poslanciDB.entity.OsobyEntity;
import poslanciDB.entity.PoslanecEntity;
import poslanciDB.entity.ProjevEntity;
import poslanciDB.service.OsobyEntityService;

import java.util.*;

public class PoslanecStatistikyMesicCreator {
    private static OsobyEntityService osobyEntityService = new OsobyEntityService();

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
                Collection<ProjevEntity> projevEntityCollection = poslanecEntity.getProjevsByIdPoslanec();
                List<ProjevEntity> projevyAllList = new ArrayList<>(projevEntityCollection);
                Set<ProjevEntity> projevyMesicSet = new HashSet<>();
                while(!projevEntityCollection.isEmpty()) {
                    ProjevEntity projevFirst = projevyAllList.remove(0);
                    projevyMesicSet.add(projevFirst);
                    for(int i = 0; i < projevyAllList.size(); i++) {
                        if(equalsMonthAndYear(projevFirst.getBodByIdBod().getDatum(),
                                projevyAllList.get(i).getBodByIdBod().getDatum())) {
                            projevyMesicSet.add(projevyAllList.remove(i));
                        }
                    }

                    Integer pocetPosSlov = 0, pocetNegSlov = 0;
                    for(ProjevEntity projevEntity : projevyMesicSet) {
                        pocetPosSlov += projevEntity.getProjevStatistikyByIdProjev().getPocetPosSlov();
                        pocetNegSlov += projevEntity.getProjevStatistikyByIdProjev().getPocetNegSlov();
                    }


                }
            }
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
