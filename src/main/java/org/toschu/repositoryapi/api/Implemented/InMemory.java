/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.toschu.repositoryapi.api.Implemented;

import org.toschu.repositoryapi.api.Repository;
import org.toschu.repositoryapi.api.Identity;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author corbeau
 * @param <T>
 */
public class InMemory<T extends Identity> implements Repository<T> {

    protected Set<T> entities = new HashSet<>();

    @Override
    public Set<T> get() {
        return Collections.unmodifiableSet(entities);
    }

    @Override
    public void persist(T entity) {
        entities.add(entity);
    }

    @Override
    public void remove(T entity) {
        entities.remove(entity);
    }
}
