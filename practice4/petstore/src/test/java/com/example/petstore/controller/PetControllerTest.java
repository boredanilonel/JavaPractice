package com.example.petstore.controller;

import com.example.petstore.exception.NotFoundException;
import com.example.petstore.model.Pet;
import com.example.petstore.service.PetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class PetControllerTest {

    @Mock
    private PetService petService;

    @InjectMocks
    private PetController petController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private Pet pet;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(petController).build();
        objectMapper = new ObjectMapper();
        pet = new Pet();
        pet.setId(1L);
        pet.setName("Buddy");
    }

    @Test
    void addPet_ShouldReturnCreatedPet() throws Exception {
        when(petService.addPet(any())).thenReturn(pet);

        mockMvc.perform(post("/api/v3/pet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pet)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Buddy"));
    }

    @Test
    void getPetById_WhenPetExists_ShouldReturnPet() throws Exception {
        when(petService.getPetById(1L)).thenReturn(pet);

        mockMvc.perform(get("/api/v3/pet/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void getPetById_WhenPetDoesNotExist_ShouldReturnNotFound() throws Exception {
        when(petService.getPetById(1L)).thenThrow(new NotFoundException("Pet not found"));

        mockMvc.perform(get("/api/v3/pet/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Pet not found")); // Проверяем, что содержимое соответствует сообщению
    }

    @Test
    void deletePet_ShouldReturnNoContent() throws Exception {
        doNothing().when(petService).deletePet(1L);

        mockMvc.perform(delete("/api/v3/pet/1"))
                .andExpect(status().isNoContent());
    }
}