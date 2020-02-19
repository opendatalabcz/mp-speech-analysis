package creator;

import entity.PoslanecEntity;
import entity.ProjevEntity;
import entity.SlovoEntity;
import service.PoslanecEntityService;
import service.SlovoEntityService;

import java.util.List;

public class SlovoCreator {
    private static PoslanecEntityService poslanecEntityService = new PoslanecEntityService();
    private static SlovoEntityService slovoEntityService = new SlovoEntityService();

    public static void main(String[] args) {
        processAllMetingsInPeriod(172);
    }

    private static void processAllMetingsInPeriod(Integer period) {
        List poslanecEntityList = poslanecEntityService.findAllWithPeriod(period);
        for(Object obj : poslanecEntityList) {
            PoslanecEntity poslanecEntity;
            try {
                poslanecEntity = (PoslanecEntity) obj;
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
            slovoEntityService.multiBegin();
            processAllSpeeches(poslanecEntity);
            slovoEntityService.multiCommit();
        }
    }

    private static void processAllSpeeches(PoslanecEntity poslanecEntity) {
        System.out.println("Poslanec: " + poslanecEntity.getOsobyByIdOsoba().getPrijmeni() + " - " + poslanecEntity.getIdPoslanec());
        for(ProjevEntity projevEntity : poslanecEntity.getProjevsByIdPoslanec()) {
            SlovoCreatorData slovoCreatorData = new SlovoCreatorData(projevEntity);
            slovoCreatorData.analyze();
            List<SlovoEntity> slovoEntities = slovoCreatorData.getSlovoEntities();
            for(SlovoEntity slovoEntity : slovoEntities) {
                //TODO hodit na DB
                slovoEntityService.multiCreate(slovoEntity);
                /*System.out.println("Lemma: " + slovoEntity.getSlovo());
                System.out.println("Tag: " + slovoEntity.getTag());
                System.out.println("Pocet: " + slovoEntity.getPocetVyskytu());
                System.out.println();*/
            }
        }
    }
}
