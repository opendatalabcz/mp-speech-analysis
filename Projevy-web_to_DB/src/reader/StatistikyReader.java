package reader;

import entity.PoslanecEntity;
import entity.ProjevEntity;
import entity.StatistikyEntity;
import service.PoslanecEntityService;
import service.StatistikyEntityService;

import java.util.List;

public class StatistikyReader {
    private static PoslanecEntityService poslanecEntityService = new PoslanecEntityService();
    private static StatistikyEntityService statistikyEntityService = new StatistikyEntityService();

    public static void main(String[] args) {
        ProcessAllStatistics();
    }

    public static void ProcessAllStatistics() {
        List poslanecEntityList = poslanecEntityService.findAllWithPeriod(172);
        for(Object obj : poslanecEntityList) {
            PoslanecEntity poslanecEntity;
            try {
                poslanecEntity = (PoslanecEntity)obj;
            } catch (Exception e){
                e.printStackTrace();
                continue;
            }

            Integer delka = 0;
            for(ProjevEntity projevEntity : poslanecEntity.getProjevsByIdPoslanec()) {
                delka += projevEntity.getDelka();
            }
            StatistikyEntity statistikyEntity = new StatistikyEntity(poslanecEntity.getIdPoslanec(), delka, 0);
            statistikyEntityService.createOrUpdate(statistikyEntity);
        }
    }
}
