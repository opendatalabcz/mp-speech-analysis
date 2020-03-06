import poslanciDB.service.OrganyEntityService;

import java.util.List;

public class AppTest {
    public static void main(String[] args) {
        OrganyEntityService organyEntityService = new OrganyEntityService();
        List list = organyEntityService.getAllChamberSeasons();
        int a = 5;
    }
}
