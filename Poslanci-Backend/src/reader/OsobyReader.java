package reader;

import helper.Timer;
import poslanciDB.entity.OsobyEntity;
import poslanciDB.service.OsobyEntityService;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

import static helper.StringHelper.*;

public class OsobyReader {
    static OsobyEntityService osobyEntityService = new OsobyEntityService();

    public static void readAndCreateAllOsoby(String path) {
        System.out.println();
        System.out.println("readAndCreateAllOsoby(" + path + ")");

        UNLFileReader UNLFileReader = new UNLFileReader(path);
        List<String> myList;
        Timer timer = new Timer();

        //program prochazi unl soubor a z kazdeho radku vytvori novou entitu
        while ((myList = UNLFileReader.getLineList()) != null) {
            OsobyEntity osobyEntity = CreateOsobaEntityFromStringList(myList);
            osobyEntityService.createOrUpdate(osobyEntity);
            System.out.println("OSOBY - TIME: " + timer.getTime() + " --- CURRENT ID: " +
                    osobyEntity.getIdOsoba());
        }
        System.out.println("OSOBY - FINAL TIME: " + timer.getTime());
    }

    public static void removeAllOsobyWithoutPoslanec() {
        System.out.println();
        System.out.println("removeAllOsobyWithoutPoslanec()");

        //program projde seznam vsech osob a z databaze smaze vsechny, ktere nemaji navazane zadne poslance
        List osoby = osobyEntityService.findAll();
        for(Object obj : osoby) {
            OsobyEntity osobyEntity;
            try {
                osobyEntity = (OsobyEntity)obj;
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
            osobyEntity = osobyEntityService.refresh(osobyEntity);
            Collection poslanci = osobyEntity.getPoslanecsByIdOsoba();
            if(poslanci == null || poslanci.size() == 0) {
                System.out.println("REMOVE OSOBY - ID: " + osobyEntity.getIdOsoba());
                osobyEntityService.remove(osobyEntity);
            }
        }
    }

    private static OsobyEntity CreateOsobaEntityFromStringList(List<String> list){
        //tato entita ma presne 9 atributu
        if(list.size() != 9) throw new IllegalArgumentException();

        Integer idOsoba;
        String pred, jmeno, prijmeni, za, pohlavi;
        Date narozeni, zmena, umrti;
        String pattern = "dd.MM.yyyy";

        if(tryParseInt(list.get(0))){
            idOsoba = Integer.parseInt(list.get(0));
        } else throw new IllegalArgumentException();

        pred = removeUselessWhitespacesString(list.get(1));
        jmeno = removeUselessWhitespacesString(list.get(3));
        prijmeni = removeUselessWhitespacesString(list.get(2));
        za = removeUselessWhitespacesString(list.get(4));
        narozeni = getSqlDateFromString(list.get(5), pattern);
        pohlavi = removeUselessWhitespacesString(list.get(6));
        zmena = getSqlDateFromString(list.get(7), pattern);
        umrti = getSqlDateFromString(list.get(8), pattern);

        return new OsobyEntity(idOsoba, pred, jmeno, prijmeni, za, narozeni, pohlavi, zmena, umrti);
    }
}
