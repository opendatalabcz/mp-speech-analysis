package app;

import creator.PoslanecStatistikyCreator;
import helper.Timer;
import poslanciDB.PersistenceMap;
import poslanciDB.entity.OrganyEntity;
import reader.*;
import remover.Remover;

import java.io.File;

public class App {
    public static void main(String[] args) {
        if(args.length != 4){
            System.out.println("Wrong number of parameters.");
            printGuide();
            return;
        }
        if(!setDatabaseAbsolutePath(args[0])) {
            System.out.println("Wrong path to database - 1st parameter.");
            printGuide();
            return;
        }
        File file = new File(args[1]);
        if(!file.exists()) {
            System.out.println("Wrong path to resources - 2nd parameter.");
            printGuide();
            return;
        }
        String mainPath = args[1];
        String seasonStringYear = args[2];
        String seasonString = args[3];
        Timer timer = new Timer();

        String seasonPath = mainPath + "/" + seasonStringYear + "/";
        file = new File(seasonPath);
        if(!file.exists()) {
            System.out.println("Wrong season year - 3nd parameter.");
            printGuide();
            return;
        }
        if(!seasonString.startsWith("PSP"))
        {
            System.out.println("Wrong season - 4th parameter.");
            printGuide();
            return;
        }

        removeSeason(seasonString);
        System.out.println("++++ TIMER AFTER removeSeason(" + seasonString + ")");
        System.out.println("++++ TIME: " + timer.getTime());

        readAndCreateCommonEntities(mainPath);
        System.out.println("++++ TIMER AFTER readAndCreateCommonEntities(" + mainPath + ")");
        System.out.println("++++ TIME: " + timer.getTime());

        processSeason(seasonPath, seasonString);
        System.out.println("++++ TIMER AFTER processSeason(" + seasonPath + ", " + seasonString + ")");
        System.out.println("++++ TIME: " + timer.getTime());
    }

    private static void removeSeason(String seasonString) {
        OrganyEntity season = helper.EntityHelper.getSeason(seasonString);
        if(season == null) return;

        System.out.println();
        System.out.println("removeSeason(" + season + ")");
        Remover.removeSeason(season);
    }

    private static void processSeason(String seasonPath, String season) {
        System.out.println();
        System.out.println("processSeason(" + seasonPath + ", " + season + ")");

        BodReader.ProcessAllMeetings(seasonPath, season);
        ProjevReader.ProcessAllMeetings(seasonPath, season);

        PoslanecStatistikyCreator poslanecStatistikyCreator = new PoslanecStatistikyCreator();
        poslanecStatistikyCreator.ProcessAllStatistics(season);
    }

    private static void readAndCreateCommonEntities(String mainPath) {
        System.out.println();
        System.out.println("readAndCreateCommonEntities(" + mainPath + ")");

        TypOrganuReader.readAndCreateAllTypOrganu(mainPath + "/typ_organu.unl");
        OrganyReader.readAndCreateAllOrgany(mainPath + "/organy.unl");

        OsobyReader.readAndCreateAllOsoby(mainPath + "/osoby.unl");
        PoslanecReader.readAndCreateAllPoslanec(mainPath + "/poslanec.unl");
        OsobyReader.removeAllOsobyWithoutPoslanec();
    }

    private static boolean setDatabaseAbsolutePath(String path) {
        File file = new File(path);
        if(!file.exists()) return false;
        String absolutePath = file.getAbsolutePath();
        PersistenceMap.setUrl(absolutePath);
        return true;
    }

    private static void printGuide() {
        System.out.println("Arguments: [path to database] [path to resourses] [season year, example: 1993ps, 2002ps, 2013ps,...] [season, example: PSP1, PSP4, PSP7]");
    }
}
