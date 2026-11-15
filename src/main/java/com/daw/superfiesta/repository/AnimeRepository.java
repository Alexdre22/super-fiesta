package com.daw.superfiesta.repository;

import com.daw.superfiesta.model.Anime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnimeRepository extends JpaRepository<Anime, Long> {

    // Buscar un anime por título exacto ignorando mayúsculas y minúsculas
    Optional<Anime> findByTituloIgnoreCase(String titulo);

    // Buscar animes cuyo título contenga el texto indicado
    List<Anime> findByTituloContainingIgnoreCase(String texto);

    // Buscar animes con un mínimo de capítulos vistos
    List<Anime> findByCapitulosVistosGreaterThanEqual(Integer capitulosVistos);
}