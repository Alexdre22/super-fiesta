package com.daw.superfiesta.repository;

import com.daw.superfiesta.model.Anime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@ActiveProfiles("test")
@DisplayName("Tests de Persistencia - AnimeRepository")
class AnimeRepositoryTest {

    @Autowired
    private AnimeRepository animeRepository;

    @Test
    @DisplayName("Debe guardar un anime con sus plataformas y recuperarlo por ID")
    void guardarYRecuperarAnime() {
        // ARRANGE
        Anime anime = new Anime(
                null,
                "Jujutsu Kaisen",
                47,
                false,
                8.9,
                new ArrayList<>(List.of("Crunchyroll", "Netflix"))
        );

        // ACT
        Anime guardado = animeRepository.save(anime);
        Anime encontrado = animeRepository.findById(guardado.getId()).orElse(null);

        // ASSERT
        assertThat(guardado.getId()).isNotNull();
        assertThat(encontrado).isNotNull();
        assertThat(encontrado.getTitulo()).isEqualTo("Jujutsu Kaisen");
        assertThat(encontrado.getCapitulosVistos()).isEqualTo(47);
        assertThat(encontrado.getSaltaRelleno()).isFalse();
        assertThat(encontrado.getPlataformas()).containsExactly("Crunchyroll", "Netflix");
    }

    @Test
    @DisplayName("Debe encontrar un anime por título ignorando mayúsculas y minúsculas")
    void buscarPorTituloIgnoreCase() {
        // ARRANGE
        animeRepository.save(new Anime(
                null,
                "Fullmetal Alchemist: Brotherhood",
                64,
                false,
                9.8,
                new ArrayList<>(List.of("Crunchyroll"))
        ));

        // ACT
        Optional<Anime> resultado = animeRepository.findByTituloIgnoreCase("fullmetal alchemist: brotherhood");

        // ASSERT
        assertThat(resultado).isPresent();
        assertThat(resultado.get().getCapitulosVistos()).isEqualTo(64);
    }

    @Test
    @DisplayName("Debe filtrar animes con capítulos vistos mayores o iguales al mínimo indicado")
    void buscarPorCapitulosVistosGreaterThanEqual() {
        // ARRANGE
        animeRepository.save(new Anime(null, "Chainsaw Man", 12, false, 8.5, List.of("Crunchyroll")));
        animeRepository.save(new Anime(null, "Bleach", 366, true, 8.4, List.of("Disney+")));
        animeRepository.save(new Anime(null, "Hunter x Hunter", 148, false, 9.4, List.of("Crunchyroll", "Netflix")));

        // ACT
        List<Anime> resultado = animeRepository.findByCapitulosVistosGreaterThanEqual(100);

        // ASSERT
        assertThat(resultado).hasSize(2);
        assertThat(resultado)
                .extracting(Anime::getTitulo)
                .containsExactlyInAnyOrder("Bleach", "Hunter x Hunter");
    }

    @Test
    @DisplayName("Debe lanzar DataIntegrityViolationException al guardar un título duplicado")
    void guardarTituloDuplicado_DebeLanzarExcepcion() {
        // ARRANGE
        animeRepository.saveAndFlush(new Anime(null, "Death Note", 37, false, 9.1, List.of("Netflix")));
        Anime duplicado = new Anime(null, "Death Note", 10, false, 8.0, List.of("Crunchyroll"));

        // ACT & ASSERT
        assertThatThrownBy(() -> animeRepository.saveAndFlush(duplicado))
                .isInstanceOf(DataIntegrityViolationException.class);
    }
}