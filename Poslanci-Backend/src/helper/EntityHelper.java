package helper;

import poslanciDB.entity.OrganyEntity;
import poslanciDB.service.OrganyEntityService;

import java.util.List;

public class EntityHelper {
    private static OrganyEntityService organyEntityService = new OrganyEntityService();

    public static OrganyEntity getSeason(String seasonString) {
        List obdobiList = organyEntityService.getAllChamberSeasons();
        for(Object obj : obdobiList) {
            OrganyEntity obdobi;
            try {
                obdobi = (OrganyEntity)obj;
            } catch (Exception e) {
                continue;
            }
            if(obdobi.getZkratka().equals(seasonString)) {
                if(organyEntityService.getEntityManager().contains(obdobi)) {
                    obdobi = organyEntityService.refresh(obdobi);
                }
                return obdobi;
            }
        }
        return null;
    }
}
