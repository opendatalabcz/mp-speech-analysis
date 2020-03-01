package creator;

import poslanciDB.entity.PoslanecEntity;
import poslanciDB.entity.ProjevEntity;
import poslanciDB.entity.SlovoEntity;
import helper.Timer;
import poslanciDB.service.PoslanecEntityService;
import poslanciDB.service.SlovoEntityService;

import java.util.List;

public class SlovoCreator {
    private static PoslanecEntityService poslanecEntityService = new PoslanecEntityService();
    private static SlovoEntityService slovoEntityService = new SlovoEntityService();
    private static Timer timer = new Timer();

    public static void main(String[] args) {
        processAllPoslanecsInPeriod(172);
    }

    private static void processAllPoslanecsInPeriod(Integer period) {
        List poslanecEntityList = poslanecEntityService.findAllWithPeriod(period);
        for(Object obj : poslanecEntityList) {
            PoslanecEntity poslanecEntity;
            try {
                poslanecEntity = (PoslanecEntity) obj;
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
            processAllSpeeches(poslanecEntity);
        }
        System.out.println("ESTIMATED TIME: " + timer.getTime());
    }

    private static void processAllSpeeches(PoslanecEntity poslanecEntity) {
        System.out.println("Poslanec: " + poslanecEntity.getOsobyByIdOsoba().getPrijmeni() + " - " + poslanecEntity.getIdPoslanec());
        System.out.println("CURRENT ESTIMATED TIME: " + timer.getTime());
        System.out.println();
        slovoEntityService.multiBegin();
        for(ProjevEntity projevEntity : poslanecEntity.getProjevsByIdPoslanec()) {
            //System.out.println("Projev: " + projevEntity.getIdProjev() + " - Delka: "+  projevEntity.getPocetSlov());
            //System.out.println("Projev: " + projevEntity.getIdProjev() + "\nText: "+  projevEntity.getText());
            SlovoCreatorData slovoCreatorData = new SlovoCreatorData(projevEntity);
            slovoCreatorData.analyze();
            List<SlovoEntity> slovoEntities = slovoCreatorData.getSlovoEntities();


            for(SlovoEntity slovoEntity : slovoEntities) {
                slovoEntityService.multiCreate(slovoEntity);
            }
        }
        slovoEntityService.multiCommit();
        ProjevStatistikyCreator.processAllSpeeches(poslanecEntityService.refresh(poslanecEntity).getProjevsByIdPoslanec());
        //ProjevStatistikyCreator.processOneSpeech(projevEntity);
    }
}
