package com.daw.superfiesta.service;

import com.daw.superfiesta.dto.AnimeMashupDTO;
import com.daw.superfiesta.dto.AnimeResumenDTO;
import com.daw.superfiesta.exception.RecursoNoEncontradoException;
import com.daw.superfiesta.model.Anime;
import com.daw.superfiesta.repository.AnimeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AnimeServiceTest {

    @Mock
    private AnimeRepository animeRepository;

    @Mock
    private PerfilDataProvider perfilProvider;

    @Mock
    private AnimeExternalProvider animeProvider;

    @Mock
    private CalculadoraSocial calculadoraSocial;

    @InjectMocks
    private AnimeService animeService;

    @Test
    void obtenerPorId_CuandoNoExiste_LanzaExcepcion() {
        when(animeRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> animeService.obtenerPorId(99L))
                .isInstanceOf(RecursoNoEncontradoException.class);
    }

    @Test
    void obtenerMashup_CalculaPeligroYCombinaDatos() {
        LocalDate inicio = LocalDate.now().minusDays(10);
        PerfilDataProvider.PerfilAnime perfil =
                new PerfilDataProvider.PerfilAnime(1L, "Alex", 20L, 50, inicio);

        when(perfilProvider.obtenerPerfiles()).thenReturn(List.of(perfil));
        when(animeProvider.obtenerTituloAnime(20L)).thenReturn("Naruto");
        when(calculadoraSocial.calcularPeligro(50, inicio)).thenReturn(8.33);

        List<AnimeMashupDTO> resultado = animeService.obtenerMashup();

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getTituloAnime()).isEqualTo("Naruto");
        assertThat(resultado.get(0).getPeligroVidaSocial()).isEqualTo(8.33);
        verify(animeProvider, times(1)).obtenerTituloAnime(20L);
    }

    @Test
    void listarTodos_RetornaListaMapeada() {
        Anime anime = new Anime(1L, "Bleach", 366, true, 8.8, List.of("Disney+"));
        when(animeRepository.findAll()).thenReturn(List.of(anime));

        List<AnimeResumenDTO> lista = animeService.listarTodos();

        assertThat(lista).hasSize(1);
        assertThat(lista.get(0).getTitulo()).isEqualTo("Bleach");
    }
}
