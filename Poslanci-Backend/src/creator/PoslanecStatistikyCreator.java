package creator;

import helper.Timer;
import poslanciDB.entity.*;
import poslanciDB.service.*;

import java.util.List;

import static creator.PoslanecStatistikyMesicCreator.getPoslanecMonthStats;


public class PoslanecStatistikyCreator {
    private static PoslanecStatistikyEntityService poslanecStatistikyEntityService = new PoslanecStatistikyEntityService();
    private static PoslanecEntityService poslanecEntityService = new PoslanecEntityService();
    private static Integer pocetTopSlov = 50;

    /*public static void main(String[] args) {
        OrganyEntity season = organyEntityService.find(172);
        ProcessAllStatistics("PSP8");
    }*/

    public void ProcessAllStatistics(String seasonString) {
        System.out.println("----PoslanecStatistikyCreator----");
        OrganyEntity season = helper.EntityHelper.getSeason(seasonString);
        if(season == null) return;
        poslanecEntityService.iteratePoslanecsInObdobiNew(season, this::ProcessOnePoslanec);


        /*Collection<PoslanecEntity> poslanecEntityList = season.getPoslanecsObdobiByIdOrgan();
        for(PoslanecEntity poslanecEntity : poslanecEntityList) {

        }*/
    }

    public void ProcessOnePoslanec(PoslanecEntity poslanecEntity) {
        System.out.println("PoslanecStatistikyCreator - Poslanec: " + poslanecEntity + " (" + poslanecEntity.getIdPoslanec() + ")");
        Timer timer = new Timer();
        if(poslanecEntity.getIdPoslanec().equals(1432)) {
            int i = 8;
        }

        Integer delka = 0, pocetSlov = 0, pocetPosSlov = 0, pocetNegSlov = 0;
        Double sentiment = 0.0;
        TopSlovaCreator topSlovaCreator = new TopSlovaCreator();
        PoslanecStatistikyZminkyCreator poslanecStatistikyZminkyCreator = new PoslanecStatistikyZminkyCreator();

        int count = 1;
        for(ProjevEntity projevEntity : poslanecEntity.getProjevsByIdPoslanec()) {
            System.out.println("--- Projev num: " + count);
            count++;
            /*System.out.println("--- Projev id: " + projevEntity.getIdProjev() + ", delka: " + projevEntity.getPocetSlov());
            System.out.println("--- Prubezne pocty slov " + delka + "/" + pocetPosSlov + "/" + pocetNegSlov);
            System.out.println();*/
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


        System.out.println("--- Pred Top Words");
        List<TopSlovaEntity> topSlovaEntities = topSlovaCreator.getTopNWords(pocetTopSlov, poslanecStatistikyEntity);
        poslanecStatistikyEntity.setTopSlovaByIdPoslanec(topSlovaEntities);
        System.out.println("--- Po Top Words");

        System.out.println("--- Pred Zminky");
        List<PoslanecStatistikyZminkyEntity> zminkyEntities = poslanecStatistikyZminkyCreator.getAllZminky(poslanecStatistikyEntity);
        poslanecStatistikyEntity.setPoslanecStatistikyZminkiesByIdPoslanec(zminkyEntities);
        System.out.println("--- Po Zminky");

        System.out.println("--- Pred Mesice");
        poslanecEntity.setPoslanecStatistikyByIdPoslanec(poslanecStatistikyEntity);
        List<PoslanecStatistikyMesicEntity> mesicEntities = getPoslanecMonthStats(poslanecEntity);
        poslanecStatistikyEntity.setPoslanecStatistikyMesicsByIdPoslanec(mesicEntities);
        System.out.println("--- Po Mesice");

        System.out.println("--- Pred Create");
        poslanecStatistikyEntityService.createOrUpdate(poslanecStatistikyEntity);
        System.out.println("--- Po Create");
        System.out.println("--- Poslanec celkovy cas: " + timer.getTime());
        System.out.println();
    }
}
