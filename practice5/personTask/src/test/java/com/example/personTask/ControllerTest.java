package com.example.personTask;

import com.example.personTask.controller.ControllerClass;
import com.example.personTask.exception.ExceptionClass;
import com.example.personTask.model.Person;
import com.example.personTask.service.ServiceClass;
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
class ControllerTest {

    @Mock
    private ServiceClass serviceClass;

    @InjectMocks
    private ControllerClass controllerClass;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private Person person;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controllerClass).build();
        objectMapper = new ObjectMapper();
        person = new Person();
        person.setId(1L);
        person.setName("Holodec");
        person.setAge(23);
    }

    @Test
    void addPet_ShouldReturnCreatedPet() throws Exception {
        when(serviceClass.addPerson(any())).thenReturn(person);

        mockMvc.perform(post("/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(person)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Holodec"))
                .andExpect(jsonPath("$.age").value(23));
    }

    @Test
    void getPetById_WhenPetExists_ShouldReturnPet() throws Exception {
        when(serviceClass.getPersonById(1L)).thenReturn(person);

        mockMvc.perform(get("/persons/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void getPetById_WhenPetDoesNotExist_ShouldReturnNotFound() throws Exception {
        when(serviceClass.getPersonById(1L)).thenThrow(new ExceptionClass("Pet not found"));

        mockMvc.perform(get("/persons/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Pet not found")); // Проверяем, что содержимое соответствует сообщению
    }
}