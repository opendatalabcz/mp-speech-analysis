package poslanciDB.service;

import poslanciDB.entity.OrganyEntity;

import java.util.List;

public class OrganyEntityService extends AbstractService<OrganyEntity> {
    public OrganyEntityService() { super(OrganyEntity.class); }

    public List getAllChamberSeasons() {
        entityManager.getTransaction().begin();
        List list = entityManager.createQuery("SELECT t FROM " + entityClass.getName() + " t " +
                "WHERE t.typOrganuByIdTypOrganu.idTypOrg = " + 11).getResultList();
        entityManager.getTransaction().commit();
        return list;
    }
}
