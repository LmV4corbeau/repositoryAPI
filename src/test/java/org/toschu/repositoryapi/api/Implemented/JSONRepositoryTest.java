/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.toschu.repositoryapi.api.Implemented;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.toschu.repositoryapi.api.Identity;
import org.toschu.repositoryapi.example.Adress;
import org.toschu.repositoryapi.example.Person;

/**
 *
 * @author toschu
 */
public class JSONRepositoryTest {

    private File testfolder;

    public JSONRepositoryTest() {
        testfolder = new File("testfiles");
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
     * Test of get method, of class JSONRepository.
     */
    @Test
    public void testGet() {
        /*
        System.out.println("get");
        JSONRepository instance
                = new JSONRepository<Person>(new File("tmp"), Person.class);
        Set expResult = null;
        Set result = instance.get();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to //fail.
        //fail("The test case is a prototype.");
        */
    }

    /**
     * Test of persist method, of class JSONRepository.
     */
    @Test
    public void testPersist() {
        /*
        System.out.println("persist");
        Identity entity = null;
        JSONRepository instance = null;
        instance.persist(entity);
        // TODO review the generated test code and remove the default call to //fail.
        //fail("The test case is a prototype.");
        */
    }

    /**
     * Test of remove method, of class JSONRepository.
     */
    @Test
    public void testRemove() {
        System.out.println("remove");
        List<String> hobbies = new ArrayList<>();
        hobbies.add("Bogenschießen");
        hobbies.add("Programmieren");
        Person entity = new Person("Tom", "Schumann", hobbies,
                new Adress("Reichenhainer Mühlberg",
                        "09125", "73", "Germany", "Sachsen"));

        JSONRepository instance
                = new JSONRepository(this.testfolder, Person.class);
        instance.remove(entity);
        List<File> currentfiles = new ArrayList<>();
        currentfiles.addAll(Arrays.asList(this.testfolder.listFiles()));
        currentfiles.stream().forEach((currentChildrenFromFolder) -> {
            assertFalse(currentChildrenFromFolder.getName().equals(instance.createFilename(entity)));
        });

    }

    /**
     * Test of parseJSONFileToObject method, of class JSONRepository.
     */
    @Test
    public void testParseJSONFileToObject() {
    /*    System.out.println("parseJSONFileToObject");
        JSONRepository instance = null;
        Identity expResult = null;
            Identity result = instance.parseJSONFileToObject(null, null);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to //fail.
        ////fail("The test case is a prototype.");
            
            */
    }

}
