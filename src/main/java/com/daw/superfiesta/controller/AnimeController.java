package com.daw.superfiesta.controller;

import com.daw.superfiesta.dto.AnimeCreacionDTO;
import com.daw.superfiesta.dto.AnimeMashupDTO;
import com.daw.superfiesta.dto.AnimePatchDTO;
import com.daw.superfiesta.dto.AnimeResumenDTO;
import com.daw.superfiesta.service.AnimeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/animes")
@Tag(name = "Gestión de Animes", description = "CRUD completo, Mashup con MyAnimeList y Calculadora Social")
public class AnimeController {

    private final AnimeService animeService;

    public AnimeController(AnimeService animeService) {
        this.animeService = animeService;
    }

    @GetMapping
    @Operation(summary = "Listar todos los animes", description = "Devuelve todos los animes registrados en H2")
    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    public ResponseEntity<List<AnimeResumenDTO>> listarTodos() {
        return ResponseEntity.ok(animeService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener anime por ID", description = "Busca un anime concreto en la base de datos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Anime encontrado"),
            @ApiResponse(responseCode = "404", description = "Anime no encontrado")
    })
    public ResponseEntity<AnimeResumenDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(animeService.obtenerPorId(id));
    }

    @GetMapping("/mashup")
    @Operation(summary = "Obtener Mashup + Calculadora Social", description = "Combina H2, API externa Jikan y caché RAM")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Mashup generado con éxito"),
            @ApiResponse(responseCode = "404", description = "No hay perfiles registrados")
    })
    public ResponseEntity<List<AnimeMashupDTO>> obtenerMashup() {
        return ResponseEntity.ok(animeService.obtenerMashup());
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo anime", description = "Valida y registra un nuevo anime en H2")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Anime creado"),
            @ApiResponse(responseCode = "400", description = "Error de validación"),
            @ApiResponse(responseCode = "409", description = "Título duplicado")
    })
    public ResponseEntity<AnimeResumenDTO> crear(@Valid @RequestBody AnimeCreacionDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(animeService.crear(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Reemplazar un anime", description = "Sustituye todos los datos de un anime existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Anime actualizado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Anime no encontrado")
    })
    public ResponseEntity<AnimeResumenDTO> reemplazar(@PathVariable Long id, @Valid @RequestBody AnimeCreacionDTO dto) {
        return ResponseEntity.ok(animeService.reemplazar(id, dto));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualización parcial", description = "Modifica únicamente los campos enviados")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Actualización aplicada"),
            @ApiResponse(responseCode = "400", description = "Regla de negocio incumplida"),
            @ApiResponse(responseCode = "404", description = "Anime no encontrado")
    })
    public ResponseEntity<AnimeResumenDTO> actualizarParcial(@PathVariable Long id, @Valid @RequestBody AnimePatchDTO dto) {
        return ResponseEntity.ok(animeService.actualizarParcial(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un anime", description = "Borra el anime de H2 e invalida la caché")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Anime eliminado"),
            @ApiResponse(responseCode = "404", description = "Anime no encontrado")
    })
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        animeService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
