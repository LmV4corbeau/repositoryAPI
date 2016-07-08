/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.toschu.repositoryapi.api;

import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.MappedSuperclass;
import javax.persistence.Id;

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
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Identity identity = (Identity) o;
        return Objects.equals(identity, identity.identity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identity);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName()
                + "[id=" + identity.substring(0, 8) + "...]";
    }
}
