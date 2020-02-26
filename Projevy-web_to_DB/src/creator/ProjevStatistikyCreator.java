package creator;

import entity.*;
import helper.StringHelper;
import org.apache.commons.lang3.StringUtils;
import service.OsobyEntityService;
import service.ProjevStatistikyEntityService;

import java.util.Collection;
import java.util.List;

public class ProjevStatistikyCreator {
    private static OsobyEntityService osobyEntityService = new OsobyEntityService();
    private static ProjevStatistikyEntityService projevStatistikyEntityService = new ProjevStatistikyEntityService();

    public static void main(String[] args) {
        processAllPersons();
    }

    public static void processOneSpeech(ProjevEntity projevEntity) {
        System.out.println("---Projev id: " + projevEntity.getIdProjev());
        Integer idProjev = projevEntity.getIdProjev();
        Integer pocetSlov = StringHelper.wordCount(projevEntity.getText());
        Integer pocetPosSlov = 0, pocetNegSlov = 0;
        double sentiment = 0.0;
        for(SlovoEntity slovoEntity : projevEntity.getSlovosByIdProjev()) {
            if(slovoEntity.getSentiment() == 1) pocetPosSlov += slovoEntity.getPocetVyskytu();
            if(slovoEntity.getSentiment() == -1) pocetNegSlov += slovoEntity.getPocetVyskytu();
        }
        if(pocetPosSlov + pocetNegSlov != 0)
            sentiment = ((pocetPosSlov * 1.0) + (pocetNegSlov * (-1.0))) / (pocetPosSlov + pocetNegSlov);
        ProjevStatistikyEntity projevStatistikyEntity = new ProjevStatistikyEntity(idProjev, pocetSlov,
                pocetPosSlov, pocetNegSlov, sentiment, projevEntity);
        projevStatistikyEntityService.multiCreate(projevStatistikyEntity);
    }

    public static void processAllSpeeches(Collection<ProjevEntity> projevEntityCollection) {
        projevStatistikyEntityService.multiBegin();
        for(ProjevEntity projevEntity : projevEntityCollection) {
            processOneSpeech(projevEntity);
        }
        projevStatistikyEntityService.multiCommit();
    }

    private static void processAllPersons() {
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
                for(ProjevEntity projevEntity : projevEntityCollection) {
                    processOneSpeech(projevEntity);
                }
            }
        }
    }


}
