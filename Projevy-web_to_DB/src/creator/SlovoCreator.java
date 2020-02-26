package creator;

import entity.PoslanecEntity;
import entity.ProjevEntity;
import entity.SlovoEntity;
import service.PoslanecEntityService;
import service.SlovoEntityService;

import java.util.Collection;
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
            processAllSpeeches(poslanecEntity);
        }
    }

    private static void processAllSpeeches(PoslanecEntity poslanecEntity) {
        System.out.println("Poslanec: " + poslanecEntity.getOsobyByIdOsoba().getPrijmeni() + " - " + poslanecEntity.getIdPoslanec());
        //Integer count = 0;
        //Integer size = 10;
        slovoEntityService.multiBegin();
        for(ProjevEntity projevEntity : poslanecEntity.getProjevsByIdPoslanec()) {
            /*if(count % size == 0) {
                slovoEntityService.multiCommit();
                slovoEntityService.multiBegin();
            }
            count++;*/
            System.out.println("Projev: " + projevEntity.getIdProjev() + " - Delka: "+  projevEntity.getDelka());
            //System.out.println("Projev: " + projevEntity.getIdProjev() + "\nText: "+  projevEntity.getText());
            SlovoCreatorData slovoCreatorData = new SlovoCreatorData(projevEntity);
            slovoCreatorData.analyze();
            List<SlovoEntity> slovoEntities = slovoCreatorData.getSlovoEntities();


            for(SlovoEntity slovoEntity : slovoEntities) {
                slovoEntityService.multiCreate(slovoEntity);
            }


            /*System.out.println("Projev: " + projevEntity.getDelka() + " slov, z toho " +
                    pos + " pozitivnich, " + neg + " negativnich a " + nor + " nerozlisenych.");*/
        }
        slovoEntityService.multiCommit();
        ProjevStatistikyCreator.processAllSpeeches(poslanecEntityService.refresh(poslanecEntity).getProjevsByIdPoslanec());
        //ProjevStatistikyCreator.processOneSpeech(projevEntity);
    }
}
