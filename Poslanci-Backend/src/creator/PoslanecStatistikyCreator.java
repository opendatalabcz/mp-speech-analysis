package creator;

import poslanciDB.entity.*;
import poslanciDB.service.*;

import java.util.Collection;
import java.util.List;

import static creator.PoslanecStatistikyMesicCreator.getPoslanecMonthStats;

public class PoslanecStatistikyCreator {
    private static PoslanecStatistikyEntityService poslanecStatistikyEntityService = new PoslanecStatistikyEntityService();
    private static ProjevEntityService projevEntityService = new ProjevEntityService();
    private static TopSlovaEntityService topSlovaEntityService = new TopSlovaEntityService();
    private static OrganyEntityService organyEntityService = new OrganyEntityService();
    private static Integer pocetTopSlov = 50;

    public static void main(String[] args) {
        OrganyEntity season = organyEntityService.find(172);
        ProcessAllStatistics("PSP8");
    }

    public static void ProcessAllStatistics(String seasonString) {
        System.out.println("----PoslanecStatistikyCreator----");
        OrganyEntity season = helper.EntityHelper.getSeason(seasonString); //TODO season == null

        Collection<PoslanecEntity> poslanecEntityList = season.getPoslanecsObdobiByIdOrgan();
        for(PoslanecEntity poslanecEntity : poslanecEntityList) {
            System.out.println("PoslanecStatistikyCreator - Poslanec: " + poslanecEntity.toString());

            Integer delka = 0, pocetSlov = 0, pocetPosSlov = 0, pocetNegSlov = 0;
            Double sentiment = 0.0;
            TopSlovaCreator topSlovaCreator = new TopSlovaCreator();
            PoslanecStatistikyZminkyCreator poslanecStatistikyZminkyCreator = new PoslanecStatistikyZminkyCreator();

            for(ProjevEntity projevEntity : poslanecEntity.getProjevsByIdPoslanec()) {
                //projevEntity = projevEntityService.refresh(projevEntity);
                delka += projevEntity.getPocetSlov();
                pocetPosSlov += projevEntity.getPocetPosSlov();
                pocetNegSlov += projevEntity.getPocetNegSlov();
                for(SlovoEntity slovoEntity : projevEntity.getSlovosByIdProjev()) {
                    topSlovaCreator.addWord(slovoEntity);
                }
                for(ZminkaEntity zminkaEntity : projevEntity.getZminkasByIdProjev()){
                    poslanecStatistikyZminkyCreator.addZminka(zminkaEntity);
                }
            }
            pocetSlov = pocetPosSlov + pocetNegSlov;
            if(pocetSlov != 0)
                sentiment = ((pocetPosSlov * 1.0) + (pocetNegSlov * (-1.0))) / pocetSlov;
            PoslanecStatistikyEntity poslanecStatistikyEntity = new PoslanecStatistikyEntity(poslanecEntity.getIdPoslanec(), delka, sentiment);


            List<TopSlovaEntity> topSlovaEntities = topSlovaCreator.getTopNWords(pocetTopSlov, poslanecStatistikyEntity);
            poslanecStatistikyEntity.setTopSlovaByIdPoslanec(topSlovaEntities);

            List<PoslanecStatistikyZminkyEntity> zminkyEntities = poslanecStatistikyZminkyCreator.getAllZminky(poslanecStatistikyEntity);
            poslanecStatistikyEntity.setPoslanecStatistikyZminkiesByIdPoslanec(zminkyEntities);

            poslanecEntity.setPoslanecStatistikyByIdPoslanec(poslanecStatistikyEntity);
            List<PoslanecStatistikyMesicEntity> mesicEntities = getPoslanecMonthStats(poslanecEntity);
            poslanecStatistikyEntity.setPoslanecStatistikyMesicsByIdPoslanec(mesicEntities);

            poslanecStatistikyEntityService.createOrUpdate(poslanecStatistikyEntity);
            System.out.println();
        }
    }
}
