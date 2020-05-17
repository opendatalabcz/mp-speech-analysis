package creator;

import helper.MathHelper;
import helper.Timer;
import poslanciDB.entity.*;
import poslanciDB.service.PoslanecEntityService;
import poslanciDB.service.PoslanecStatistikyEntityService;

import java.util.List;

import static creator.PoslanecStatistikyMesicCreator.getPoslanecMonthStats;


public class PoslanecStatistikyCreator {
    private static PoslanecStatistikyEntityService poslanecStatistikyEntityService = new PoslanecStatistikyEntityService();
    private static PoslanecEntityService poslanecEntityService = new PoslanecEntityService();
    private static Integer pocetTopSlov = 50;

    public void ProcessAllStatistics(String seasonString) {
        System.out.println("----PoslanecStatistikyCreator----");
        OrganyEntity season = helper.EntityHelper.getSeason(seasonString);
        if(season == null) return;
        poslanecEntityService.iteratePoslanecsInObdobiNew(season, this::ProcessOnePoslanec);
    }

    public void ProcessOnePoslanec(PoslanecEntity poslanecEntity) {
        System.out.println("PoslanecStatistikyCreator - Poslanec: " + poslanecEntity + " (" + poslanecEntity.getIdPoslanec() + ")");
        Timer timer = new Timer();

        Integer delka = 0, pocetPosSlov = 0, pocetNegSlov = 0;
        TopSlovaCreator topSlovaCreator = new TopSlovaCreator();
        PoslanecStatistikyZminkyCreator poslanecStatistikyZminkyCreator = new PoslanecStatistikyZminkyCreator();

        int count = 1;
        //prochazi vsechny projevz poslance a pocita z nich pocty slova sentiment
        for(ProjevEntity projevEntity : poslanecEntity.getProjevsByIdPoslanec()) {
            System.out.println("--- Projev num: " + count);
            count++;
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
        Double sentiment = MathHelper.countSentiment(pocetPosSlov, pocetNegSlov);
        //vytvori proste statistiky pro poslance, podrobnejsi se pridaji dale
        PoslanecStatistikyEntity poslanecStatistikyEntity = new PoslanecStatistikyEntity(poslanecEntity.getIdPoslanec(), delka, sentiment);

        System.out.println("--- Pred Top Words");
        //pridani statistik topslov
        List<TopSlovaEntity> topSlovaEntities = topSlovaCreator.getTopNWords(pocetTopSlov, poslanecStatistikyEntity);
        poslanecStatistikyEntity.setTopSlovaByIdPoslanec(topSlovaEntities);
        System.out.println("--- Po Top Words");

        System.out.println("--- Pred Zminky");
        //pridani statistik o zminkach
        List<PoslanecStatistikyZminkyEntity> zminkyEntities = poslanecStatistikyZminkyCreator.getAllZminky(poslanecStatistikyEntity);
        poslanecStatistikyEntity.setPoslanecStatistikyZminkiesByIdPoslanec(zminkyEntities);
        System.out.println("--- Po Zminky");

        System.out.println("--- Pred Mesice");
        //pridani mesicnich statistik
        poslanecEntity.setPoslanecStatistikyByIdPoslanec(poslanecStatistikyEntity);
        List<PoslanecStatistikyMesicEntity> mesicEntities = getPoslanecMonthStats(poslanecEntity);
        poslanecStatistikyEntity.setPoslanecStatistikyMesicsByIdPoslanec(mesicEntities);
        System.out.println("--- Po Mesice");

        System.out.println("--- Pred Create");
        //ulozeni statistik do databaze
        poslanecStatistikyEntityService.createOrUpdate(poslanecStatistikyEntity);
        System.out.println("--- Po Create");
        System.out.println("--- Poslanec celkovy cas: " + timer.getTime());
        System.out.println();
    }
}
