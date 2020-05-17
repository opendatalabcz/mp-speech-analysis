package poslanciDB.service;

import poslanciDB.entity.BodEntity;

import java.util.List;

public class BodEntityService extends AbstractService<BodEntity> {
    public BodEntityService() { super(BodEntity.class); }

    public BodEntity checkTextExists(String text, Integer meetingNumber, Integer seasonNumber) {
        entityManager.getTransaction().begin();
        List list = entityManager.createQuery("SELECT t FROM " + entityClass.getName() +
                " t WHERE t.text = '" + text + "' AND t.cisloSchuze = " + meetingNumber +
                " AND t.organyByIdOrganObdobi = " + seasonNumber).getResultList();
        entityManager.getTransaction().commit();
        if(list.size() > 0) {
            return (BodEntity) list.get(0);
        } else {
            return null;
        }
    }
}
