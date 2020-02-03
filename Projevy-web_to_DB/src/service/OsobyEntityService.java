package service;

import entity.OsobyEntity;

import java.util.List;

public class OsobyEntityService extends AbstractService<OsobyEntity> {
    public OsobyEntityService() {
        super(OsobyEntity.class);
    }

    /*@Override
    public List<OsobyEntity> findAll() {
        getEntityManager().getTransaction().begin();
        List list = getEntityManager().createQuery("SELECT o FROM OsobyEntity o").getResultList();
        return list;
    }*/
}
