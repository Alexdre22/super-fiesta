
package com.daw.superfiesta.service;

import com.daw.superfiesta.dto.AnimeCreacionDTO;
import com.daw.superfiesta.dto.AnimeMashupDTO;
import com.daw.superfiesta.dto.AnimePatchDTO;
import com.daw.superfiesta.dto.AnimeResumenDTO;
import com.daw.superfiesta.exception.DatosAnimeInvalidosException;
import com.daw.superfiesta.exception.RecursoNoEncontradoException;
import com.daw.superfiesta.model.Anime;
import com.daw.superfiesta.repository.AnimeRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnimeService {

    private final AnimeRepository animeRepository;
    private final PerfilDataProvider perfilProvider;
    private final AnimeExternalProvider animeProvider;
    private final CalculadoraSocial calculadoraSocial;

    public AnimeService(
            AnimeRepository animeRepository,
            PerfilDataProvider perfilProvider,
            AnimeExternalProvider animeProvider,
            CalculadoraSocial calculadoraSocial) {
        this.animeRepository = animeRepository;
        this.perfilProvider = perfilProvider;
        this.animeProvider = animeProvider;
        this.calculadoraSocial = calculadoraSocial;
    }

    public List<AnimeResumenDTO> listarTodos() {
        return animeRepository.findAll().stream()
                .map(this::mapToResumenDTO)
                .toList();
    }

    public AnimeResumenDTO obtenerPorId(Long id) {
        Anime anime = animeRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Anime no encontrado con ID: " + id));
        return mapToResumenDTO(anime);
    }

    @CacheEvict(value = "animeMashup", allEntries = true)
    public AnimeResumenDTO crear(AnimeCreacionDTO dto) {
        Anime nuevo = new Anime(
                null,
                dto.getTitulo(),
                dto.getCapitulosVistos(),
                dto.getSaltaRelleno(),
                dto.getPuntuacionPersonal(),
                new ArrayList<>(dto.getPlataformas())
        );
        return mapToResumenDTO(animeRepository.save(nuevo));
    }

    @CacheEvict(value = "animeMashup", allEntries = true)
    public AnimeResumenDTO reemplazar(Long id, AnimeCreacionDTO dto) {
        Anime existente = animeRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Anime no encontrado con ID: " + id));

        existente.setTitulo(dto.getTitulo());
        existente.setCapitulosVistos(dto.getCapitulosVistos());
        existente.setSaltaRelleno(dto.getSaltaRelleno());
        existente.setPuntuacionPersonal(dto.getPuntuacionPersonal());
        existente.setPlataformas(new ArrayList<>(dto.getPlataformas()));

        return mapToResumenDTO(animeRepository.save(existente));
    }

    @CacheEvict(value = "animeMashup", allEntries = true)
    public AnimeResumenDTO actualizarParcial(Long id, AnimePatchDTO dto) {
        Anime existente = animeRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Anime no encontrado con ID: " + id));

        if (dto.getCapitulosVistos() != null && dto.getCapitulosVistos() > 3000) {
            throw new DatosAnimeInvalidosException("Número de capítulos inverosímil (máximo 3000)");
        }

        if (dto.getTitulo() != null) existente.setTitulo(dto.getTitulo());
        if (dto.getCapitulosVistos() != null) existente.setCapitulosVistos(dto.getCapitulosVistos());
        if (dto.getSaltaRelleno() != null) existente.setSaltaRelleno(dto.getSaltaRelleno());
        if (dto.getPuntuacionPersonal() != null) existente.setPuntuacionPersonal(dto.getPuntuacionPersonal());
        if (dto.getPlataformas() != null) existente.setPlataformas(new ArrayList<>(dto.getPlataformas()));

        return mapToResumenDTO(animeRepository.save(existente));
    }

    @CacheEvict(value = "animeMashup", allEntries = true)
    public void eliminar(Long id) {
        if (!animeRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("Anime no encontrado con ID: " + id);
        }
        animeRepository.deleteById(id);
    }

    @CacheEvict(value = "animeMashup", allEntries = true)
    public void invalidarCacheMashup() {
    }

    @Cacheable("animeMashup")
    public List<AnimeMashupDTO> obtenerMashup() {
        List<PerfilDataProvider.PerfilAnime> perfiles = perfilProvider.obtenerPerfiles();

        if (perfiles.isEmpty()) {
            throw new RecursoNoEncontradoException("No hay perfiles de anime registrados");
        }

        return perfiles.stream()
                .map(perfil -> {
                    String titulo = animeProvider.obtenerTituloAnime(perfil.animeId());
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

    private AnimeResumenDTO mapToResumenDTO(Anime anime) {
        return new AnimeResumenDTO(
                anime.getId(),
                anime.getTitulo(),
                anime.getCapitulosVistos(),
                anime.getSaltaRelleno(),
                anime.getPuntuacionPersonal(),
                anime.getPlataformas()
        );
    }
}
