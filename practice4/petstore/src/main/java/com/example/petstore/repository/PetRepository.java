package com.example.petstore.repository;

import com.example.petstore.model.Pet;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class PetRepository {
    private final Map<Long, Pet> pets = new HashMap<>();
    private long nextId = 1;

    public List<Pet> findAll() {
        return new ArrayList<>(pets.values());
    }

    public Optional<Pet> findById(Long id) {
        return Optional.ofNullable(pets.get(id));
    }

    public Pet save(Pet pet) {
        if (pet.getId() == null) {
            pet.setId(nextId++);
        }
        pets.put(pet.getId(), pet);
        return pet;
    }

    public void deleteById(Long id) {
        pets.remove(id);
    }
}
