package com.example.petstore.service;

import com.example.petstore.exception.NotFoundException;
import com.example.petstore.model.Pet;
import com.example.petstore.repository.PetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PetServiceTest {

    @Mock
    private PetRepository petRepository;

    @InjectMocks
    private PetService petService;

    private Pet pet;

    @BeforeEach
    void setUp() {
        pet = new Pet();
        pet.setId(1L);
        pet.setName("Buddy");
    }

    @Test
    void addPet_ShouldReturnSavedPet() {
        when(petRepository.save(pet)).thenReturn(pet);
        Pet savedPet = petService.addPet(pet);
        assertNotNull(savedPet);
        assertEquals("Buddy", savedPet.getName());
    }

    @Test
    void updatePet_WhenPetExists_ShouldReturnUpdatedPet() {
        when(petRepository.findById(1L)).thenReturn(Optional.of(pet));
        when(petRepository.save(pet)).thenReturn(pet);
        Pet updatedPet = petService.updatePet(pet);
        assertNotNull(updatedPet);
        assertEquals("Buddy", updatedPet.getName());
    }

    @Test
    void updatePet_WhenPetDoesNotExist_ShouldThrowException() {
        when(petRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> petService.updatePet(pet));
    }

    @Test
    void getPetById_WhenPetExists_ShouldReturnPet() {
        when(petRepository.findById(1L)).thenReturn(Optional.of(pet));
        Pet foundPet = petService.getPetById(1L);
        assertNotNull(foundPet);
        assertEquals(1L, foundPet.getId());
    }

    @Test
    void getPetById_WhenPetDoesNotExist_ShouldThrowException() {
        when(petRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> petService.getPetById(1L));
    }

    @Test
    void deletePet_WhenPetExists_ShouldDeletePet() {
        when(petRepository.findById(1L)).thenReturn(Optional.of(pet));
        doNothing().when(petRepository).deleteById(1L);
        assertDoesNotThrow(() -> petService.deletePet(1L));
    }

    @Test
    void deletePet_WhenPetDoesNotExist_ShouldThrowException() {
        when(petRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> petService.deletePet(1L));
    }
}
