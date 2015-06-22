package org.toschu.repositoryapi.example;

import java.util.LinkedList;
import java.util.List;
import org.toschu.repositoryapi.api.Implemented.MongoDBMorphia;
import org.toschu.repositoryapi.api.Repository;

/**
 *
 * @author corbeau
 */
public class Main {

    public static void main(String[] args) {
        List<Person> persons = new LinkedList<>();
        List<String> hobby = new LinkedList<>();
        hobby.add("programmieren");
        hobby.add("bogenschießen");
        Adress tomsAdress = new Adress("ReichenhainerMühlberg", "09125", "73", "Germany", "Sachsen");
        Person tom = new Person("Tom", "Schumann", hobby, tomsAdress);
        try {
            Repository<Person> PersonRepo
                    = new MongoDBMorphia<>(Person.class, "org.toschu", "RepositoryTest");
            Repository<Adress> adressRepo
                    = new MongoDBMorphia<>(Adress.class, "org.toschu", "RepositoryTest");
            adressRepo.persist(tomsAdress);
            PersonRepo.persist(tom);
            List<Person> loaded = new LinkedList<>(PersonRepo.get());
            System.out.println(loaded);
            for (int i = 0; i < 1000; i++) {
                List<Person> cloaded = new LinkedList<>(PersonRepo.get());
                System.out.println(cloaded);
                PersonRepo.persist(cloaded);
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
