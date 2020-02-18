package creator;

import entity.PoslanecEntity;
import service.PoslanecEntityService;

import java.util.List;

public class SlovoCreator {
    private static PoslanecEntityService poslanecEntityService = new PoslanecEntityService();

    public static void main(String[] args) {
        processAllMetingsInPeriod(172);
    }

    private static void processAllMetingsInPeriod(Integer period) {
        List poslanecEntityList = poslanecEntityService.findAllWithPeriod(period);
        for(Object obj : poslanecEntityList) {
            PoslanecEntity poslanecEntity;
            try {
                poslanecEntity = (PoslanecEntity) obj;
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
            //TODO nacteni vsech projevu a rozsekani je na slova do DB
        }
    }
}
