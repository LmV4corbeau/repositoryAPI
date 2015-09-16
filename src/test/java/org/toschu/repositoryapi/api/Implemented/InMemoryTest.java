/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.toschu.repositoryapi.api.Implemented;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.toschu.repositoryapi.api.Repository;
import org.toschu.repositoryapi.example.Adress;
import org.toschu.repositoryapi.example.Person;

/**
 *
 * @author toschu
 */
public class InMemoryTest {

    private Adress entity1Adress
            = new Adress("Robert Schumann Straße", "09129",
                    "101", "Germany", "Berlin");
    private Person entity1 = new Person("Tom", "Schumann",
            new LinkedList<>(), entity1Adress);

    public InMemoryTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of get method, of class InMemory.
     */
    @Test
    public void testGetFromEmptyRepo() {
        System.out.println("get from empty Repo");
        InMemory instance = new InMemory();
        Set expResult = new HashSet<>();
        Set result = instance.get();
        assertEquals(expResult, result);
    }

    /**
     * Test of get method, of class InMemory. One element
     */
    @Test
    public void testGetFromRepoOneResult() {
        System.out.println("get from empty Repo");
        Repository<Person> instance = new InMemory<>();
        instance.persist(entity1);
        Set expResult = new HashSet<>();
        expResult.add(entity1);
        Set result = instance.get();
        assertEquals(expResult, result);
    }

    /**
     * Test of persist method, of class InMemory.
     */
    @Test
    public void testPersist() {
        System.out.println("persist");
        Repository<Person> instance = new InMemory<>();
        Set<Person> beforePersist = instance.get();
        instance.persist(entity1);
        assertTrue(instance.get().contains(entity1));
    }

    /**
     * Test of remove method, of class InMemory.
     */
    @Test
    public void testRemove() {
        System.out.println("remove");
        Repository<Person> instance = new InMemory<>();
        instance.persist(entity1);
         Set<Person> beforePersist = instance.get();
        instance.remove(entity1);
        assertTrue(!instance.get().contains(entity1));
    }

}
