package poslanciDB.service;

import poslanciDB.entity.BodEntity;

import javax.persistence.Query;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class BodEntityService extends AbstractService<BodEntity> {
    public BodEntityService() { super(BodEntity.class); }

    public BodEntity checkTextExists(String text, Integer meetingNumber, Integer seasonNumber) {
        entityManager.getTransaction().begin();

        List list = entityManager.createQuery("SELECT t FROM BodEntity t WHERE t.text =:text " +
                "AND t.cisloSchuze =:cisloSchuze AND t.organyByIdOrganObdobi.id =:cisloObdobi")
                .setParameter("text", text)
                .setParameter("cisloSchuze", meetingNumber)
                .setParameter("cisloObdobi", seasonNumber)
                .getResultList();
        entityManager.getTransaction().commit();
        if(list.size() > 0) {
            return (BodEntity) list.get(0);
        } else {
            return null;
        }
    }

    public void removeBodList(Set<Integer> set) {
        String setString = Arrays.toString(set.toArray()).replace("[", "").replace("]", "");
        entityManager.getTransaction().begin();
        entityManager.createQuery("DELETE FROM BodEntity WHERE id in ("  + setString + ")");
        entityManager.getTransaction().commit();
    }
}
