package creator;

import poslanciDB.entity.*;
import poslanciDB.service.OrganyEntityService;

import java.util.Collection;

public class ProjevStatistikyCreator {
    private static OrganyEntityService organyEntityService = new OrganyEntityService();
    /*private static ProjevStatistikyEntityService projevStatistikyEntityService = new ProjevStatistikyEntityService();

    public static void main(String[] args) {
        OrganyEntity season = organyEntityService.find(172);
        processAllPoslanec("PSP8");
    }

    private static void processOneSpeech(ProjevEntity projevEntity) {
        System.out.println("---Projev id: " + projevEntity.getIdProjev());
        Integer idProjev = projevEntity.getIdProjev();
        Integer pocetPosSlov = 0, pocetNegSlov = 0;
        double sentiment = 0.0;
        for(SlovoEntity slovoEntity : projevEntity.getSlovosByIdProjev()) {
            if(slovoEntity.getSentiment() == 1) pocetPosSlov += slovoEntity.getPocetVyskytu();
            if(slovoEntity.getSentiment() == -1) pocetNegSlov += slovoEntity.getPocetVyskytu();
        }
        if(pocetPosSlov + pocetNegSlov != 0)
            sentiment = ((pocetPosSlov * 1.0) + (pocetNegSlov * (-1.0))) / (pocetPosSlov + pocetNegSlov);
        ProjevStatistikyEntity projevStatistikyEntity = new ProjevStatistikyEntity(idProjev, pocetPosSlov, pocetNegSlov,
                sentiment);
        projevStatistikyEntityService.multiCreate(projevStatistikyEntity);
    }

    private static void processAllSpeeches(Collection<ProjevEntity> projevEntityCollection) {
        projevStatistikyEntityService.multiBegin();
        for(ProjevEntity projevEntity : projevEntityCollection) {
            processOneSpeech(projevEntity);
        }
        projevStatistikyEntityService.multiCommit();
    }

    public static void processAllPoslanec(String seasonString) {
        System.out.println("----ProjevStatistikyCreator----");
        OrganyEntity season = helper.EntityHelper.getSeason(seasonString); //TODO season == null

        Collection<PoslanecEntity> poslanecEntityCollection = season.getPoslanecsObdobiByIdOrgan();
        for(PoslanecEntity poslanecEntity : poslanecEntityCollection) {
            System.out.println("ProjevStatistikyCreator - Poslanec: " + poslanecEntity.toString());
            Collection<ProjevEntity> projevEntityCollection = poslanecEntity.getProjevsByIdPoslanec();
            processAllSpeeches(projevEntityCollection);
        }
    }*/
}
