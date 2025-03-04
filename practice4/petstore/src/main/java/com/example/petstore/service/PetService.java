package com.example.petstore.service;

import com.example.petstore.exception.NotFoundException;
import com.example.petstore.model.Pet;
import com.example.petstore.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {
    private final PetRepository petRepository;

    @Autowired
    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public Pet addPet(Pet pet) {
        return petRepository.save(pet);
    }

    public Pet updatePet(Pet pet) {
        if (pet.getId() == null || petRepository.findById(pet.getId()).isEmpty()) {
            throw new NotFoundException("Pet not found");
        }
        return petRepository.save(pet);
    }

    public Pet getPetById(Long id) {
        return petRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pet not found"));
    }

    public void deletePet(Long id) {
        if (petRepository.findById(id).isEmpty()) {
            throw new NotFoundException("Pet not found");
        }
        petRepository.deleteById(id);
    }
}
