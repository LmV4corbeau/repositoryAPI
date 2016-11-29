/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.toschu.repositoryapi.api;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

/**
 * Identity class. Every Class how want's to stored with repositoringApi has zu
 * extend this Class
 *
 * @author corbeau
 */
@MappedSuperclass
public abstract class Identity {

    @Id
    private String identity;

    public Identity() {
        this.identity = UUID.randomUUID().toString();
    }

    public Identity(String identitier) {
        this.identity = identitier;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Identity)) return false;

        Identity identity1 = (Identity) o;

        return getIdentity().equals(identity1.getIdentity());
    }

    @Override
    public int hashCode() {
        return getIdentity().hashCode();
    }

    @Override
    public String toString() {
        return "Identity{" +
                "identity='" + identity + '\'' +
                '}';
    }
}
