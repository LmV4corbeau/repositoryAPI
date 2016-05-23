/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.toschu.repositoryapi.api;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author corbeau
 * @param <T>
 */
public interface Repository<T extends Identity> {

    Logger logger
            = LoggerFactory.getLogger(Repository.class);

    default Optional<T> get(String id) {
        return get()
                .stream()
                .filter(entity -> entity.getIdentity().equals(id))
                .findAny();
    }

    default Set<T> get(Predicate<T> predicate) {
        return get()
                .stream()
                .filter(predicate)
                .collect(Collectors.toSet());
    }

    Set<T> get();

    void persist(T entity);

    default void persist(T... entities) {
        persist(Arrays.asList(entities));
    }

    default void persist(Collection<T> entities) {
        entities.forEach(this::persist);
    }

    void remove(T entity);

    default void remove(T... entities) {
        remove(Arrays.asList(entities));
    }

    default void remove(Collection<T> entities) {
        entities.forEach(this::remove);
    }

    default void remove(String id) {
        remove(entity -> entity.getIdentity().equals(id)); // predicate to match the id
    }

    default void remove(Predicate<T> predicate) {
        get(predicate).forEach(this::remove);
    }
}
