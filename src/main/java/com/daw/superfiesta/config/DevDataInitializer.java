package com.daw.superfiesta.config;

import com.daw.superfiesta.model.Anime;
import com.daw.superfiesta.repository.AnimeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Profile("dev")
public class DevDataInitializer implements CommandLineRunner {

    private final AnimeRepository animeRepository;

    public DevDataInitializer(AnimeRepository animeRepository) {
        this.animeRepository = animeRepository;
    }

    @Override
    public void run(String... args) {
        if (animeRepository.count() == 0) {
            System.out.println("--> [DEV] Precargando 3 animes iniciales en H2...");

            Anime anime1 = new Anime(
                    null,
                    "Naruto Shippuden",
                    50,
                    true,
                    8.7,
                    new ArrayList<>(List.of("Crunchyroll", "Netflix"))
            );

            Anime anime2 = new Anime(
                    null,
                    "Attack on Titan",
                    89,
                    false,
                    9.5,
                    new ArrayList<>(List.of("Crunchyroll", "Prime Video"))
            );

            Anime anime3 = new Anime(
                    null,
                    "One Piece",
                    1100,
                    false,
                    9.2,
                    new ArrayList<>(List.of("Crunchyroll", "Netflix", "Pluto TV"))
            );

            animeRepository.saveAll(List.of(anime1, anime2, anime3));
        }
    }
}