package com.example.personTask.service;

import com.example.personTask.model.Person;
import com.example.personTask.collection.RepositoryClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")  // Используем тестовый профиль
public class PersonServiceIT {

    @Autowired
    private RepositoryClass repository;

    @Autowired
    private ServiceClass serviceClass;

    private Person testPerson;

    @BeforeEach
    void setUp() {
        testPerson = new Person();
        testPerson.setName("John Doe");
        testPerson.setAge(30);
        repository.save(testPerson);
    }

    @Test
    void testAddPerson() {
        Person newPerson = new Person();
        newPerson.setName("Jane Doe");
        newPerson.setAge(25);

        Person savedPerson = serviceClass.addPerson(newPerson);
        assertNotNull(savedPerson.getId());
        assertEquals("Jane Doe", savedPerson.getName());
    }

    @Test
    void testGetPersonById() {
        Person foundPerson = serviceClass.getPersonById(testPerson.getId());
        assertNotNull(foundPerson);
        assertEquals("John Doe", foundPerson.getName());
    }

    @Test
    void testUpdatePerson() {
        testPerson.setAge(35);
        Person updatedPerson = serviceClass.updatePerson(testPerson);
        assertEquals(35, updatedPerson.getAge());
    }

    @Test
    void testDeletePerson() {
        serviceClass.deletePerson(testPerson.getId());
        Optional<Person> deletedPerson = repository.findById(testPerson.getId());
        assertFalse(deletedPerson.isPresent());
    }
}
