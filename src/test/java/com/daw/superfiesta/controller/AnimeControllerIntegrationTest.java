package com.daw.superfiesta.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AnimeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void flujoCompleto_CrearYListar_Retorna201Y200() throws Exception {
        String jsonValido = """
                {
                  "titulo": "Demon Slayer",
                  "capitulosVistos": 55,
                  "saltaRelleno": true,
                  "puntuacionPersonal": 9.1,
                  "plataformas": ["Crunchyroll", "Netflix"],
                  "aceptaRiesgoSocial": true
                }
                """;

        mockMvc.perform(post("/api/animes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonValido))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.titulo").value("Demon Slayer"))
                .andExpect(jsonPath("$.capitulosVistos").value(55));

        mockMvc.perform(get("/api/animes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].titulo").value("Demon Slayer"));
    }

    @Test
    void crearAnimeInvalido_Retorna400BadRequest() throws Exception {
        String jsonInvalido = """
                {
                  "titulo": "",
                  "capitulosVistos": -5,
                  "saltaRelleno": true,
                  "puntuacionPersonal": 15.0,
                  "plataformas": [],
                  "aceptaRiesgoSocial": false
                }
                """;

        mockMvc.perform(post("/api/animes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInvalido))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.codigo").value(400));
    }
}
