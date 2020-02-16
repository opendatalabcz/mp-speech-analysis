package creator;

import entity.PoslanecEntity;
import entity.ProjevEntity;
import entity.StatistikyEntity;
import entity.TopSlovaEntity;
import service.PoslanecEntityService;
import service.StatistikyEntityService;
import service.TopSlovaEntityService;

import java.util.List;

public class StatistikyCreator {
    private static PoslanecEntityService poslanecEntityService = new PoslanecEntityService();
    private static StatistikyEntityService statistikyEntityService = new StatistikyEntityService();
    private static TopSlovaEntityService topSlovaEntityService = new TopSlovaEntityService();

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
            TopSlovaCreator topSlovaCreator = new TopSlovaCreator(poslanecEntity.getIdPoslanec());

            for(ProjevEntity projevEntity : poslanecEntity.getProjevsByIdPoslanec()) {
                delka += projevEntity.getDelka();
                topSlovaCreator.processSpeech(projevEntity.getText());
            }
            StatistikyEntity statistikyEntity = new StatistikyEntity(poslanecEntity.getIdPoslanec(), delka, 0);
            statistikyEntityService.createOrUpdate(statistikyEntity);

            List<TopSlovaEntity> topSlovaEntities = topSlovaCreator.getTopNWords(10, statistikyEntity);
            for(TopSlovaEntity topSlovaEntity : topSlovaEntities) {
                System.out.println("IdPosl: " + topSlovaEntity.getStatistikyByIdPoslanec().getIdPoslanec() + ", Word: " + topSlovaEntity.getSlovo());
                topSlovaEntityService.create(topSlovaEntity);
            }
            System.out.println();
        }
    }
}
