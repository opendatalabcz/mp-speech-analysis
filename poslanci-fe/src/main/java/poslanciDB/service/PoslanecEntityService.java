package poslanciDB.service;

import poslanciDB.entity.PoslanecEntity;

import java.util.List;

public class PoslanecEntityService extends AbstractService<PoslanecEntity> {
    public PoslanecEntityService() { super(PoslanecEntity.class); }

    public List findAllWithPeriod(Integer period) {
        entityManager.getTransaction().begin();
        List list = entityManager.createQuery("SELECT t FROM " + entityClass.getName() + " t WHERE t.organyByIdObdobi.idOrgan = "
                + period).getResultList();
        entityManager.getTransaction().commit();
        return list;
    }
}
