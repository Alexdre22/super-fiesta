package com.daw.superfiesta.service;

import com.daw.superfiesta.repository.AnimeRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class PerfilDataProviderImpl implements PerfilDataProvider {

    private final AnimeRepository animeRepository;

    public PerfilDataProviderImpl(AnimeRepository animeRepository) {
        this.animeRepository = animeRepository;
    }

    @Override
    public List<PerfilAnime> obtenerPerfiles() {
        return animeRepository.findAll().stream()
                .map(anime -> new PerfilAnime(
                        anime.getId(),
                        "Usuario_SuperFiesta",
                        anime.getId(),
                        anime.getCapitulosVistos(),
                        LocalDate.now().minusDays(15)
                ))
                .toList();
    }
}
