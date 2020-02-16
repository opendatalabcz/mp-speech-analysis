import analyzer.Analyzer;
import entity.*;
import service.OsobyEntityService;
import service.PoslanecEntityService;
import service.StatistikyEntityService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.*;

public class App {
    public static void main(String[] args) {
        StatistikyEntityService statistikyEntityService = new StatistikyEntityService();

        List list= statistikyEntityService.findAll();
        for (Object obj : list) {
            StatistikyEntity statistikyEntity;
            try {
                statistikyEntity = (StatistikyEntity) obj;
            } catch (Exception e){
                e.printStackTrace();
                continue;
            }

            OsobyEntity osobyEntity = statistikyEntity.getPoslanecByIdPoslanec().getOsobyByIdOsoba();

            System.out.println(osobyEntity.getPred() + " " + osobyEntity.getJmeno() + " "
                    + osobyEntity.getPrijmeni() + " " + osobyEntity.getZa());
            for (TopSlovaEntity tse : statistikyEntity.getTopSlovaByIdPoslanec()) {
                System.out.println(tse.getPoradi() + ": " + tse.getSlovo() + "(" + tse.getPocetVyskytu() + ")");
            }
            System.out.println();
        }
    }
}
