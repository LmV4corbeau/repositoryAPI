/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.toschu.repositoryapi.api;

import java.util.Objects;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import org.mongodb.morphia.annotations.Id;

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

    public String getIdentitier() {
        return identity;
    }

    public void setIdentitier(String identitier) {
        this.identity = identitier;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.identity);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Identity other = (Identity) obj;
        if (!Objects.equals(this.identity, other.identity)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Identity{" + "identitier=" + identity + "}";
    }

}
