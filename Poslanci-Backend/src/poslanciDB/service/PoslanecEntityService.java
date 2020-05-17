package poslanciDB.service;

import poslanciDB.entity.OrganyEntity;
import poslanciDB.entity.PoslanecEntity;

import javax.persistence.FlushModeType;
import javax.persistence.Query;
import java.util.List;
import java.util.function.Consumer;

public class PoslanecEntityService extends AbstractService<PoslanecEntity>{
    public PoslanecEntityService() { super(PoslanecEntity.class); }

    public void iteratePoslanecsInObdobi(OrganyEntity obdobi, Consumer<PoslanecEntity> consumer) {
        Integer idObdobi = obdobi.getIdOrgan();
        if(idObdobi == null) return;
        Query query = entityManager.createQuery("SELECT P FROM PoslanecEntity P where P.organyByIdObdobi.id =:idObdobi")
                .setParameter("idObdobi", idObdobi);
        query.setFlushMode(FlushModeType.AUTO);
        query.setMaxResults(5);
        query.setFirstResult(1);

        int pageSize = 5;
        int lowLimit = 0;
        List list;
        while(lowLimit != -1) {
            query.setFirstResult(lowLimit);
            int size;
            list = query.getResultList();
            if(list == null) break;
            size = list.size();
            System.out.println("------ lowLimit: " + lowLimit);
            processResult(list, consumer);
            if(pageSize != size) {
                lowLimit = -1;
            } else {
                lowLimit = lowLimit + pageSize;
            }
        }
    }

    public void iteratePoslanecsInObdobiNew(OrganyEntity obdobi, Consumer<PoslanecEntity> consumer)
    {
        if(obdobi == null || obdobi.getIdOrgan() == null) return;
        int offset = 0;

        List list;
        while ((list = getAllPoslanecsIterable(offset, 5, obdobi.getIdOrgan())).size() > 0)
        {
            System.out.println("------ offset: " + offset);
            processResult(list, consumer);
            offset += list.size();
            entityManager.clear();
            entityManager.close();
            entityManager = entityManagerFactory.createEntityManager();
        }
    }

    private void processResult(List list, Consumer<PoslanecEntity> consumer) {
        for(Object obj : list) {
            PoslanecEntity poslanecEntity;
            try {
                poslanecEntity = (PoslanecEntity) obj;
            } catch (Exception e) {
                continue;
            }
            consumer.accept(poslanecEntity);
        }
    }

    private List getAllPoslanecsIterable(int offset, int max, Integer idObdobi)
    {
        return entityManager.createQuery("SELECT P FROM PoslanecEntity P where P.organyByIdObdobi.id =:idObdobi")
                .setParameter("idObdobi", idObdobi)
                .setFirstResult(offset)
                .setMaxResults(max)
                .getResultList();
    }
}
