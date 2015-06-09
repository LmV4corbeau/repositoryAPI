/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.toschu.repositoryapi.api;

import java.util.Objects;
import java.util.UUID;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Id;

/**
 * Identity class. Every Class how want's to stored with repositoringApi has zu
 * extend this Class
 *
 * @author corbeau
 */
@Embedded
public abstract class Identity {

    @Id
    private String identitier;


    public Identity() {
        this.identitier = UUID.randomUUID().toString();
    }

    public String getIdentitier() {
        return identitier;
    }

    public void setIdentitier(String identitier) {
        this.identitier = identitier;
    }


    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.identitier);
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
        if (!Objects.equals(this.identitier, other.identitier)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Identity{" + "identitier=" + identitier + "}";
    }

}
