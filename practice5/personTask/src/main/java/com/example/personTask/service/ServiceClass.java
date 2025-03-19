package com.example.personTask.service;

import com.example.personTask.model.Person;
import com.example.personTask.collection.RepositoryClass;
import com.example.personTask.exception.ExceptionClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceClass {
    private final RepositoryClass repository;

    @Autowired
    public ServiceClass(RepositoryClass repository) {
        this.repository = repository;
    }

    public Person addPerson(Person person) {
        return repository.save(person);
    }

    public Person updatePerson(Person person) {
        if (!repository.existsById(person.getId())) {
            throw new ExceptionClass("Person not found");
        }
        return repository.save(person);
    }

    public Person getPersonById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ExceptionClass("Person not found"));
    }

    public void deletePerson(Long id) {
        if (!repository.existsById(id)) {
            throw new ExceptionClass("Person not found");
        }
        repository.deleteById(id);
    }
}
