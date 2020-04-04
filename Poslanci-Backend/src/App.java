import creator.PoslanecStatistikyCreator;
import creator.PoslanecStatistikyMesicCreator;
import creator.ProjevStatistikyCreator;
import creator.SlovoCreator;
import helper.Timer;
import poslanciDB.entity.OrganyEntity;
import poslanciDB.service.OrganyEntityService;
import reader.*;
import remover.Remover;

import static helper.EntityHelper.getSeason;

public class App {
    public static void main(String[] args) {
        //String mainPath = args[0];
        String mainPath = "resources";
        String seasonString = "Schuze";
        //OrganyEntity season = organyEntityService.find(172);
        OrganyEntity season = getSeason("PSP8");
        Timer timer = new Timer();

        removeSeason(season);
        System.out.println("++++ TIMER AFTER removeSeason(" + season + ")");
        System.out.println("++++ TIME: " + timer.getTime());

        readAndCreateCommonEntities(mainPath);
        System.out.println("++++ TIMER AFTER readAndCreateCommonEntities(" + mainPath + ")");
        System.out.println("++++ TIME: " + timer.getTime());

        String seasonPath = mainPath + "/" + seasonString + "/";
        processSeason(seasonPath, "PSP8");
        System.out.println("++++ TIMER AFTER processSeason(" + seasonPath + ", " + season + ")");
        System.out.println("++++ TIME: " + timer.getTime());
    }

    private static void removeSeason(OrganyEntity season) {
        System.out.println();
        System.out.println("removeSeason(" + season + ")");
        Remover.removeSeason(season);
    }

    private static void processSeason(String seasonPath, String season) {
        System.out.println();
        System.out.println("processSeason(" + seasonPath + ", " + season + ")");

        BodReader.ProcessAllMeetings(seasonPath, season);
        ProjevReader.ProcessAllMeetings(seasonPath, season);

        //SlovoCreator.processAllPoslanecsInSeason(season);
        //ProjevStatistikyCreator.processAllPoslanec(season);

        PoslanecStatistikyCreator.ProcessAllStatistics(season);
        //PoslanecStatistikyMesicCreator.processAllPoslanec(season);
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
}
