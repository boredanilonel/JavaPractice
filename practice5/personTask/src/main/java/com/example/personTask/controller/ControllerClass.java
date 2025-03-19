package com.example.personTask.controller;

import com.example.personTask.model.Person;
import com.example.personTask.service.ServiceClass;
import org.springframework.http.HttpStatus;
import com.example.personTask.exception.ExceptionClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/persons")
public class ControllerClass {
    private final ServiceClass serviceClass;

    @Autowired
    public ControllerClass(ServiceClass serviceClass) {
        this.serviceClass = serviceClass;
    }

    @PostMapping
    public ResponseEntity<Person> addPet(@RequestBody Person person) {
        person.setAge(person.getAge() + 4);
        return ResponseEntity.ok(serviceClass.addPerson(person));
    }

    @GetMapping("/{personId}")
    public ResponseEntity<Person> getPetById(@PathVariable Long personId) {
        Person person = serviceClass.getPersonById(personId);
        return ResponseEntity.ok(person);
    }

    @DeleteMapping("/{personId}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long personId) {
        serviceClass.deletePerson(personId);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(ExceptionClass.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleNotFoundException(ExceptionClass ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}