package poslanciDB.service;

import poslanciDB.entity.HasID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractService<T extends HasID> {

    protected Class<T> entityClass;
    protected EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("NewPersistenceUnit");
    protected EntityManager entityManager = entityManagerFactory.createEntityManager();
    protected Integer batchSize = 1000;

    public AbstractService(Class<T> entityClass){
        this.entityClass = entityClass;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void create(T entity) {
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
    }

    public void update(T entity) {
        entityManager.getTransaction().begin();
        entityManager.merge(entity);
        entityManager.getTransaction().commit();
    }

    public void createOrUpdate(T entity) {
        if(entity.takeID() == null) {
            create(entity);
            System.out.println("Creating");
        } else {
            T entityOriginal = find(entity.takeID());
            if(entityOriginal == null) {
                create(entity);
                System.out.println("Creating");
            } else if(!entityOriginal.equals(entity)){
                update(entity);
                System.out.println("Updating");
            } else {
                System.out.println("No operation");
            }
        }
    }

    public T find(int id) {
        entityManager.getTransaction().begin();
        T entity = entityManager.find(entityClass, id);
        entityManager.getTransaction().commit();
        return entity;
    }

    public List findAll() {
        entityManager.getTransaction().begin();
        List list = entityManager.createQuery("FROM " + entityClass.getName()).getResultList();
        entityManager.getTransaction().commit();
        return list;
    }

    public T refresh(T entity) {
        entityManager.getTransaction().begin();
        entityManager.refresh(entity);
        entityManager.getTransaction().commit();
        return entity;
    }

    public void remove(T entity) {
        entityManager.getTransaction().begin();
        entityManager.remove(entity);
        entityManager.getTransaction().commit();
    }

    public void removeById(int id) {
        entityManager.getTransaction().begin();
        T entity = entityManager.find(entityClass, id);
        entityManager.remove(entity);
        entityManager.getTransaction().commit();
    }

    public void removeAll() {
        entityManager.getTransaction().begin();
        entityManager.createQuery("DELETE FROM " + entityClass.getName());
        entityManager.getTransaction().commit();
    }

    public void multiBegin() {
        entityManager.getTransaction().begin();
    }

    public void multiCommit() {
        entityManager.flush();
        entityManager.clear();
        entityManager.getTransaction().commit();
    }

    public void multiCreate(T entity) { entityManager.persist(entity); }

    public void ultraCreate(List<T> entities) {
        EntityTransaction tx = entityManager.getTransaction();
        Iterator<T> iterator = entities.iterator();
        tx.begin();
        int cont = 0;
        while (iterator.hasNext()) {
            entityManager.persist(iterator.next());
            cont++;
            if (cont % batchSize == 0) {
                entityManager.flush();
                entityManager.clear();
                tx.commit();
                tx.begin();
            }
        }
        tx.commit();
    }
}
