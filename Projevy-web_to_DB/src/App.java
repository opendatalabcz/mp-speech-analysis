import analyzer.Analyzer;
import entity.OsobyEntity;
import entity.PoslanecEntity;
import entity.ProjevEntity;
import entity.StatistikyEntity;
import service.OsobyEntityService;
import service.PoslanecEntityService;
import service.StatistikyEntityService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.*;

public class App {
    public static void main(String[] args) {
        //OsobyEntityService osobyEntityService = new OsobyEntityService();
        PoslanecEntityService poslanecEntityService = new PoslanecEntityService();
/*
        OsobyEntity benda = osobyEntityService.find(4);
        Collection<PoslanecEntity> bendovePoslanci = benda.getPoslanecsByIdOsoba();
        PoslanecEntity benda172 = benda.getPoslanecByIdOsobaAndPeriond(172);
        System.out.println("PoslanecID: " + benda172.getIdPoslanec());
*/
        /*List poslanecEntityList = poslanecEntityService.findAllWithPeriod(172);
        for(Object obj : poslanecEntityList) {
            PoslanecEntity poslanecEntity;
            try {
                poslanecEntity = (PoslanecEntity)obj;
            } catch (Exception e){
                e.printStackTrace();
                continue;
            }*/
            PoslanecEntity poslanecEntity = poslanecEntityService.find(1533);
            Map<String, Integer> mapWords = new HashMap<>();
            for(ProjevEntity projevEntity : poslanecEntity.getProjevsByIdPoslanec()) {
                List<String> lemmasList = Analyzer.analyzeString(projevEntity.getText());
                lemmasList.forEach(word -> {
                    Integer value = mapWords.get(word);
                    if (value == null)
                        value = 0;
                    value++;
                    mapWords.put(word, value);
                });
            }
            int a = 8;
        //}
    }
}
