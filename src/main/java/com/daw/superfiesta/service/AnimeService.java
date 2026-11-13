
package com.daw.superfiesta.service;

import com.daw.superfiesta.dto.AnimeMashupDTO;
import com.daw.superfiesta.exception.RecursoNoEncontradoException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.CacheEvict;

import java.util.List;

@Service
public class AnimeService {

    private final PerfilDataProvider perfilProvider;
    private final AnimeExternalProvider animeProvider;
    private final CalculadoraSocial calculadoraSocial;

    public AnimeService(
            PerfilDataProvider perfilProvider,
            AnimeExternalProvider animeProvider,
            CalculadoraSocial calculadoraSocial) {

        this.perfilProvider = perfilProvider;
        this.animeProvider = animeProvider;
        this.calculadoraSocial = calculadoraSocial;
    }

    @CacheEvict(value = "animeMashup", allEntries = true)
    public void invalidarCacheMashup() {
    }

    @Cacheable("animeMashup")
    public List<AnimeMashupDTO> obtenerMashup() {

        List<PerfilDataProvider.PerfilAnime> perfiles =
                perfilProvider.obtenerPerfiles();

        if (perfiles.isEmpty()) {
            throw new RecursoNoEncontradoException(
                    "No hay perfiles de anime registrados"
            );
        }

        return perfiles.stream()
                .map(perfil -> {

                    String titulo = animeProvider.obtenerTituloAnime(
                            perfil.animeId()
                    );

                    double peligro = calculadoraSocial.calcularPeligro(
                            perfil.capitulosVistos(),
                            perfil.fechaInicio()
                    );

                    return new AnimeMashupDTO(
                            perfil.perfilId(),
                            perfil.nombreUsuario(),
                            titulo,
                            perfil.capitulosVistos(),
                            peligro
                    );
                })
                .toList();
    }
}
