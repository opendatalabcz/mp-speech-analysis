package creator;

import poslanciDB.entity.*;
import poslanciDB.service.PoslanecEntityService;
import poslanciDB.service.PoslanecStatistikyEntityService;
import poslanciDB.service.TopSlovaEntityService;

import java.util.List;

public class PoslanecStatistikyCreator {
    private static PoslanecEntityService poslanecEntityService = new PoslanecEntityService();
    private static PoslanecStatistikyEntityService poslanecStatistikyEntityService = new PoslanecStatistikyEntityService();
    private static TopSlovaEntityService topSlovaEntityService = new TopSlovaEntityService();

    public static void main(String[] args) {
        ProcessAllStatistics();
    }

    public static void ProcessAllStatistics() {
        List poslanecEntityList = poslanecEntityService.findAllWithPeriod(172);
        for(Object obj : poslanecEntityList) {
            PoslanecEntity poslanecEntity;
            try {
                poslanecEntity = (PoslanecEntity)obj;
            } catch (Exception e){
                e.printStackTrace();
                continue;
            }

            Integer delka = 0, pocetSlov = 0, pocetPosSlov = 0, pocetNegSlov = 0;
            Double sentiment = 0.0;
            TopSlovaCreator topSlovaCreator = new TopSlovaCreator(poslanecEntity.getIdPoslanec());

            for(ProjevEntity projevEntity : poslanecEntity.getProjevsByIdPoslanec()) {
                System.out.println("Poslanec: " + poslanecEntity.getOsobyByIdOsoba().getPrijmeni() + " - " + poslanecEntity.getIdPoslanec());
                delka += projevEntity.getPocetSlov();
                //topSlovaCreator.processSpeech(projevEntity.getText());
                ProjevStatistikyEntity projevStatistikyEntity = projevEntity.getProjevStatistikyByIdProjev();
                pocetPosSlov += projevStatistikyEntity.getPocetPosSlov();
                pocetNegSlov += projevStatistikyEntity.getPocetNegSlov();
                for(SlovoEntity slovoEntity : projevEntity.getSlovosByIdProjev()) {
                    topSlovaCreator.addWord(slovoEntity);
                }
            }
            pocetSlov = pocetPosSlov + pocetNegSlov;
            if(pocetSlov != 0)
                sentiment = ((pocetPosSlov * 1.0) + (pocetNegSlov * (-1.0))) / pocetSlov;
            PoslanecStatistikyEntity poslanecStatistikyEntity = new PoslanecStatistikyEntity(poslanecEntity.getIdPoslanec(), delka, sentiment);
            poslanecStatistikyEntityService.createOrUpdate(poslanecStatistikyEntity);

            List<TopSlovaEntity> topSlovaEntities = topSlovaCreator.getTopNWords(10, poslanecStatistikyEntity);
            topSlovaEntityService.multiBegin();
            for(TopSlovaEntity topSlovaEntity : topSlovaEntities) {
                System.out.println("IdPosl: " + topSlovaEntity.getPoslanecStatistikyByIdPoslanec().getIdPoslanec() + ", Word: " + topSlovaEntity.getSlovo());
                topSlovaEntityService.multiCreate(topSlovaEntity);
            }
            topSlovaEntityService.multiCommit();
            System.out.println();
        }
    }
}
