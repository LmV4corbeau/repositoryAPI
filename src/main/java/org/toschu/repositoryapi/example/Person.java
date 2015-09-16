/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.toschu.repositoryapi.example;

import java.util.List;
import java.util.Objects;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;
import org.toschu.repositoryapi.api.Identity;

/**
 *
 * @author corbeau
 */
@Entity
public class Person extends Identity {

    private String name;
    private String surname;
    private List<String> hobbys;

    @Reference
    private Adress adress;

    public Person() {
    }

    public Person(String name, String surname, List<String> hobbys, Adress adress) {
        super();
        this.name = name;
        this.surname = surname;
        this.hobbys = hobbys;
        this.adress = adress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public List<String> getHobbys() {
        return hobbys;
    }

    public void setHobbys(List<String> hobbys) {
        this.hobbys = hobbys;
    }

    public Adress getAdress() {
        return adress;
    }

    public void setAdress(Adress adress) {
        this.adress = adress;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.name);
        hash = 97 * hash + Objects.hashCode(this.surname);
        hash = 97 * hash + Objects.hashCode(this.hobbys);
        hash = 97 * hash + Objects.hashCode(this.adress);
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
        final Person other = (Person) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.surname, other.surname)) {
            return false;
        }
        if (!Objects.equals(this.hobbys, other.hobbys)) {
            return false;
        }
        if (!Objects.equals(this.adress, other.adress)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Person{" + "name=" + name + 
                ", surname=" + surname + 
                ", hobbys=" + hobbys + 
                ", adress=" + adress + '}';
    }
}
