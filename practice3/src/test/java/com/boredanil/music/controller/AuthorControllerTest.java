package com.boredanil.music.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthorController.class)
class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testAuthorPageReturnsCorrectViewAndModel() throws Exception {
        mockMvc.perform(get("/author"))
                .andExpect(status().isOk())
                .andExpect(view().name("author"))
                .andExpect(model().attributeExists("name", "description"))
                .andExpect(model().attribute("name", "Данил"))
                .andExpect(model().attribute("description", "ИП-217, 20 лет, СибГУТИ 3 курс, не люблю холодец."));
    }
}
