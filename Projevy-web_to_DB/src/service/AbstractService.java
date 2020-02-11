package service;

import entity.HasID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public abstract class AbstractService<T extends HasID> {

    protected Class<T> entityClass;
    protected EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("NewPersistenceUnit");
    protected EntityManager entityManager = entityManagerFactory.createEntityManager();

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
            if(!entityOriginal.equals(entity)){
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
        List list = entityManager.createQuery("SELECT t FROM " + entityClass.getName() + " t").getResultList();
        entityManager.getTransaction().commit();
        return list;
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
}
