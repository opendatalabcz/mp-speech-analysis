import entity.OsobyEntity;
import entity.PoslanecEntity;
import service.OsobyEntityService;
import service.PoslanecEntityService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Collection;
import java.util.List;

public class App {
    public static void main(String[] args) {
        OsobyEntityService osobyEntityService = new OsobyEntityService();
        //PoslanecEntityService poslanecEntityService = new PoslanecEntityService();

        OsobyEntity benda = osobyEntityService.find(4);
        Collection<PoslanecEntity> bendovePoslanci = benda.getPoslanecsByIdOsoba();
        PoslanecEntity benda172 = benda.getPoslanecByIdOsobaAndPeriond(172);
        System.out.println("PoslanecID: " + benda172.getIdPoslanec());

    }
}
