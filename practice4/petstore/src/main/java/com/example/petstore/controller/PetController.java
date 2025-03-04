package com.example.petstore.controller;

import com.example.petstore.model.Pet;
import com.example.petstore.service.PetService;
import org.springframework.http.HttpStatus;
import com.example.petstore.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/v3/pet")
public class PetController {
    private final PetService petService;

    @Autowired
    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping
    public ResponseEntity<Pet> addPet(@RequestBody Pet pet) {
        return ResponseEntity.ok(petService.addPet(pet));
    }

    @PutMapping
    public ResponseEntity<Pet> updatePet(@RequestBody Pet pet) {
        return ResponseEntity.ok(petService.updatePet(pet));
    }

    @GetMapping("/{petId}")
    public ResponseEntity<Pet> getPetById(@PathVariable Long petId) {
        return ResponseEntity.ok(petService.getPetById(petId));
    }

    @DeleteMapping("/{petId}")
    public ResponseEntity<Void> deletePet(@PathVariable Long petId) {
        petService.deletePet(petId);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleNotFoundException(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}