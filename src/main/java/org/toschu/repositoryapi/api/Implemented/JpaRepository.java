/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.toschu.repositoryapi.api.Implemented;

/**
 *
 * @author corbeau
 */
import org.toschu.repositoryapi.api.Repository;
import org.toschu.repositoryapi.api.Identity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class JpaRepository<T extends Identity> implements Repository<T> {

    protected EntityManagerFactory emf;
    protected String persistenceUnitName;
    protected Class<T> type;

    public JpaRepository(Class<T> type, String persistenceUnitName) {
        this.type = type;
        this.persistenceUnitName = persistenceUnitName;
        emf = Persistence.createEntityManagerFactory(persistenceUnitName);
    }

    public JpaRepository(EntityManagerFactory emf, Class<T> type, String persistenceUnitName) {
        this.emf = emf;
        this.persistenceUnitName = persistenceUnitName;
        this.type = type;
    }

    @Override
    public Set<T> get() {
        List<T> resultList = run(entityManager -> {
            final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            final CriteriaQuery<T> criteria = criteriaBuilder.createQuery(type);

            final Root<T> root = criteria.from(type);
            criteria.select(root);

            final TypedQuery<T> query = entityManager.createQuery(criteria);
            return query.getResultList();
        });

        return new HashSet<>(resultList);
    }

    @Override
    public Optional<T> get(String id) {
        return Optional.ofNullable(run(entityManager -> {
            return entityManager.find(type, id);
        }));
    }

    @Override
    public void persist(T entity) {
        runInTransaction(entityManager -> {
            entityManager.merge(entity);
        });
    }

    @Override
    public void persist(Collection<T> entities) {
        runInTransaction(entityManager -> {
            entities.forEach(entityManager::merge);
        });
    }

    @Override
    public void remove(T entity) {
        remove(entity.getIdentity());
    }

    @Override
    public void remove(String id) {
        runInTransaction(entityManager -> {
            final T managedEntity = entityManager.find(type, id);
            if (managedEntity != null) {
                entityManager.remove(managedEntity);
            }
        });
    }

    @Override
    public void remove(Collection<T> entities) {
        runInTransaction(entityManager -> {
            entities
                    .stream()
                    .map(Identity::getIdentity)
                    .map(id -> entityManager.find(type, id))
                    .filter(Objects::nonNull)
                    .forEach(entityManager::remove);
        });
    }

    @Override
    public void remove(Predicate<T> predicate) {
        remove(get(predicate));
    }

    protected <R> R run(Function<EntityManager, R> function) {
        final EntityManager entityManager = emf.createEntityManager();
        try {
            return function.apply(entityManager);
        } finally {
            entityManager.close();
        }
    }

    protected void run(Consumer<EntityManager> function) {
        run(entityManager -> {
            function.accept(entityManager);
            return null;
        });
    }

    protected <R> R runInTransaction(Function<EntityManager, R> function) {
        return run(entityManager -> {
                entityManager.getTransaction().begin();
                final R result = function.apply(entityManager);
                entityManager.getTransaction().commit();
            return result;
        });
    }

    protected void runInTransaction(Consumer<EntityManager> function) {
        runInTransaction(entityManager -> {
            function.accept(entityManager);
            return null;
        });
    }
}
