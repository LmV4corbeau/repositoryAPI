/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.toschu.repositoryapi.example;

import java.util.Objects;
import org.mongodb.morphia.annotations.Entity;
import org.toschu.repositoryapi.api.Identity;

/**
 *
 * @author corbeau
 */
@Entity
public class Adress extends Identity {

    private String street;
    private String zipCode;
    private String housNumber;
    private String country;
    private String fedralState;

    public Adress() {
    }

    public Adress(String street, String zipCode, String housNumber, String country, String fedralState) {
        this.street = street;
        this.zipCode = zipCode;
        this.housNumber = housNumber;
        this.country = country;
        this.fedralState = fedralState;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getHousNumber() {
        return housNumber;
    }

    public void setHousNumber(String housNumber) {
        this.housNumber = housNumber;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getFedralState() {
        return fedralState;
    }

    public void setFedralState(String fedralState) {
        this.fedralState = fedralState;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.street);
        hash = 29 * hash + Objects.hashCode(this.zipCode);
        hash = 29 * hash + Objects.hashCode(this.housNumber);
        hash = 29 * hash + Objects.hashCode(this.country);
        hash = 29 * hash + Objects.hashCode(this.fedralState);
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
        final Adress other = (Adress) obj;
        if (!Objects.equals(this.street, other.street)) {
            return false;
        }
        if (!Objects.equals(this.zipCode, other.zipCode)) {
            return false;
        }
        if (!Objects.equals(this.housNumber, other.housNumber)) {
            return false;
        }
        if (!Objects.equals(this.country, other.country)) {
            return false;
        }
        if (!Objects.equals(this.fedralState, other.fedralState)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Adress{" + "street=" + street + ", zipCode=" + zipCode + ", housNumber=" + housNumber + ", country=" + country + ", fedralState=" + fedralState + '}';
    }

}
