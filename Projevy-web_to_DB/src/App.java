import entity.OsobyEntity;
import entity.PoslanecEntity;
import entity.ProjevEntity;
import service.OsobyEntityService;
import service.PoslanecEntityService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.*;

public class App {
    public static void main(String[] args) {
        //OsobyEntityService osobyEntityService = new OsobyEntityService();
        PoslanecEntityService poslanecEntityService = new PoslanecEntityService();
/*
        OsobyEntity benda = osobyEntityService.find(4);
        Collection<PoslanecEntity> bendovePoslanci = benda.getPoslanecsByIdOsoba();
        PoslanecEntity benda172 = benda.getPoslanecByIdOsobaAndPeriond(172);
        System.out.println("PoslanecID: " + benda172.getIdPoslanec());
*/
        Map<OsobyEntity, Integer> osobyEntityIntegerTreeMap = new HashMap<>();
        List poslanecEntityList = poslanecEntityService.findAllWithPeriod(172);
        for(Object obj : poslanecEntityList) {
            PoslanecEntity poslanecEntity;
            try {
                poslanecEntity = (PoslanecEntity)obj;
            } catch (Exception e){
                continue;
            }

            Integer delka = 0;
            for(ProjevEntity projevEntity : poslanecEntity.getProjevsByIdPoslanec()) {
                delka += projevEntity.getDelka();
            }
            osobyEntityIntegerTreeMap.put(poslanecEntity.getOsobyByIdOsoba(), delka);
        }
        for(Map.Entry<OsobyEntity, Integer> entry : osobyEntityIntegerTreeMap.entrySet()){
            System.out.println("Osoba: " + entry.getKey().getJmeno() + " " + entry.getKey().getPrijmeni());
            System.out.println("Pocet slov: " + entry.getValue());
            System.out.println();
        }
    }
}
