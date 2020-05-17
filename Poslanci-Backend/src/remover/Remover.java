package remover;

import poslanciDB.entity.BodEntity;
import poslanciDB.entity.OrganyEntity;
import poslanciDB.entity.PoslanecEntity;
import poslanciDB.service.BodEntityService;
import poslanciDB.service.OrganyEntityService;
import poslanciDB.service.PoslanecEntityService;

import java.util.Collection;

public class Remover {
    private static BodEntityService bodEntityService = new BodEntityService();
    private static PoslanecEntityService poslanecEntityService = new PoslanecEntityService();
    private static OrganyEntityService organyEntityService = new OrganyEntityService();

    public static void removeSeason(OrganyEntity season) {
        if(season == null) return;

        removeSeasonPoslanec(season);
        removeSeasonBod(season);

        //obnoveni validniho stavu po mazani
        organyEntityService.resetEntityManager();
    }

    private static void removeSeasonBod(OrganyEntity season) {
        System.out.println("removeSeasonBod(" + season + ")");
        Collection<BodEntity> bodEntityCollection = season.getBodsByIdOrgan();
        for(BodEntity bodEntity : bodEntityCollection) {
            System.out.println("---- removing bod with id: " + bodEntity.getIdBod());
            bodEntityService.remove(bodEntity);
        }
        bodEntityService.resetEntityManager();
    }

    private static void removeSeasonPoslanec(OrganyEntity season) {
        System.out.println("removeSeasonPoslanec(" + season + ")");
        Collection<PoslanecEntity> poslanecEntityCollection = season.getPoslanecsObdobiByIdOrgan();
        for(PoslanecEntity poslanecEntity : poslanecEntityCollection) {
            System.out.println("---- removing poslanec with id: " + poslanecEntity.getIdPoslanec() + " (" + poslanecEntity.toString() + ")");
            poslanecEntityService.remove(poslanecEntity);
        }
        poslanecEntityService.resetEntityManager();
    }
}
