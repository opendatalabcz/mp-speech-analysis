package creator;

import poslanciDB.entity.*;
import helper.Timer;
import poslanciDB.service.OrganyEntityService;
import poslanciDB.service.PoslanecEntityService;
import poslanciDB.service.SlovoEntityService;
import poslanciDB.service.ZminkaEntityService;

import java.util.Collection;
import java.util.List;

public class SlovoCreator {
    private static SlovoEntityService slovoEntityService = new SlovoEntityService();
    private static OrganyEntityService organyEntityService = new OrganyEntityService();
    private static ZminkaEntityService zminkaEntityService = new ZminkaEntityService();
    private static Timer timer = new Timer();

    public static void main(String[] args) {
        OrganyEntity season = organyEntityService.find(172);
        processAllPoslanecsInSeason("PSP8");
    }

    public static void processAllPoslanecsInSeason(String seasonString) {
        System.out.println("----SlovoCreator---");
        OrganyEntity season = helper.EntityHelper.getSeason(seasonString); //TODO season == null

        Collection<PoslanecEntity> poslanecEntityCollection = season.getPoslanecsObdobiByIdOrgan();
        for(PoslanecEntity poslanecEntity : poslanecEntityCollection) {
            ZminkaCreator zminkaCreator = new ZminkaCreator(poslanecEntityCollection);
            processAllSpeeches(poslanecEntity, zminkaCreator);
        }
        System.out.println("SlovoCreator - FINAL TIME: " + timer.getTime());
    }

    private static void processAllSpeeches(PoslanecEntity poslanecEntity, ZminkaCreator zminkaCreator) {
        System.out.println("SlovoCreator - Poslanec: " + poslanecEntity.toString());
        System.out.println("-- TIME: " + timer.getTime());
        System.out.println();
        slovoEntityService.multiBegin();
        for(ProjevEntity projevEntity : poslanecEntity.getProjevsByIdPoslanec()) {
            SlovoCreatorData slovoCreatorData = new SlovoCreatorData(projevEntity);
            slovoCreatorData.analyze();
            List<SlovoEntity> slovoEntities = slovoCreatorData.getSlovoEntities();

            for(SlovoEntity slovoEntity : slovoEntities) {
                zminkaCreator.processSlovoToZminka(slovoEntity);
                slovoEntityService.multiCreate(slovoEntity);
            }
        }
        slovoEntityService.multiCommit();

        List<ZminkaEntity> zminkaEntities = zminkaCreator.getZminkaList(); //todo udelat, at se zminky daji dohromady a mozna resit jen celkove zminky
        zminkaEntityService.multiBegin();
        for(ZminkaEntity zminkaEntity : zminkaEntities) {
            zminkaEntityService.multiCreate(zminkaEntity);
        }
        zminkaEntityService.multiCommit();
    }
}
