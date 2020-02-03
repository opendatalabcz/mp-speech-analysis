import entity.OsobyEntity;
import entity.PoslanecEntity;
import service.OsobyEntityService;
import service.PoslanecEntityService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class App {
    public static void main(String[] args) {
        /*EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("NewPersistenceUnit");

        OsobyEntity osobyEntity = new OsobyEntity();
        osobyEntity.setIdOsoba(10);

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
       // entityManager.persist(osobyEntity);
        OsobyEntity osobyEntity1 = entityManager.find(OsobyEntity.class, 1);
        entityManager.getTransaction().commit();

        System.out.println("JMENO:" + osobyEntity1.getJmeno());

        entityManagerFactory.close();*/
/*
        OsobyEntityService osobyEntityService = new OsobyEntityService();

        OsobyEntity osobyEntity = new OsobyEntity(10000);
        osobyEntity.setPohlavi("M");

        osobyEntityService.createOrUpdate(osobyEntity);
        ;*/

        OsobyEntityService osobyEntityService = new OsobyEntityService();
        List<OsobyEntity> osobyEntityList = osobyEntityService.findAll();

        PoslanecEntityService poslanecEntityService = new PoslanecEntityService();
        List<PoslanecEntity> poslanecEntityList = poslanecEntityService.findAll();

/*
        PoslanecEntityService poslanecEntityService = new PoslanecEntityService();
        Integer i = 0;
        while(true) {
            System.out.println("TRY: " + i++);
            PoslanecEntity poslanecEntity = new PoslanecEntity(1000 + i, 1,1,1,"","","","","","","","","",1);
            poslanecEntity.setOsobyByIdOsoba(new OsobyEntity(4, "", "", "","", null, "", null, null));
            poslanecEntityService.createOrUpdate(poslanecEntity);
        }*/
    }
}
