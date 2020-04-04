package remover;

import poslanciDB.entity.BodEntity;
import poslanciDB.entity.OrganyEntity;
import poslanciDB.entity.PoslanecEntity;
import poslanciDB.entity.PoslanecStatistikyEntity;
import poslanciDB.service.BodEntityService;
import poslanciDB.service.PoslanecEntityService;
import poslanciDB.service.PoslanecStatistikyEntityService;

import java.util.Collection;

public class Remover {
    private static BodEntityService bodEntityService = new BodEntityService();
    private static PoslanecEntityService poslanecEntityService = new PoslanecEntityService();

    public static void removeSeason(OrganyEntity season) {
        if(season == null) return;
        removeSeasonBod(season);
        removeSeasonPoslanec(season);
    }

    private static void removeSeasonBod(OrganyEntity season) {
        System.out.println("----removeSeasonBod(" + season + ")");
        Collection<BodEntity> bodCollection = season.getBodsByIdOrgan();
        bodEntityService.removeCollection(bodCollection);
    }

    private static void removeSeasonPoslanec(OrganyEntity season) {
        System.out.println("----removeSeasonPoslanec(" + season + ")");
        Collection<PoslanecEntity> poslanecEntityCollection = season.getPoslanecsObdobiByIdOrgan();
        for(PoslanecEntity poslanecEntity : poslanecEntityCollection) {
            poslanecEntityService.remove(poslanecEntity);
        }
    }
}
