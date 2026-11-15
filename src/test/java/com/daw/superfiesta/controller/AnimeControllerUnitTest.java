package com.daw.superfiesta.controller;

import com.daw.superfiesta.dto.AnimeResumenDTO;
import com.daw.superfiesta.exception.RecursoNoEncontradoException;
import com.daw.superfiesta.service.AnimeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AnimeController.class)
class AnimeControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AnimeService animeService;

    @Test
    void obtenerPorId_CuandoExiste_Retorna200() throws Exception {
        AnimeResumenDTO dto = new AnimeResumenDTO(
                1L, "Naruto", 50, true, 9.0, List.of("Crunchyroll")
        );

        when(animeService.obtenerPorId(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/animes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Naruto"))
                .andExpect(jsonPath("$.capitulosVistos").value(50));

        verify(animeService, times(1)).obtenerPorId(1L);
    }

    @Test
    void obtenerPorId_CuandoNoExiste_Retorna404() throws Exception {
        when(animeService.obtenerPorId(99L))
                .thenThrow(new RecursoNoEncontradoException("Anime no encontrado con ID: 99"));

        mockMvc.perform(get("/api/animes/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.codigo").value(404));

        verify(animeService, times(1)).obtenerPorId(99L);
    }
}
