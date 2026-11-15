package com.daw.superfiesta.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.util.List;

@Schema(description = "DTO para actualización parcial (campos opcionales)")
public class AnimePatchDTO {

    @Schema(description = "Nuevo título", example = "Jujutsu Kaisen S2")
    @Size(min = 2, max = 150, message = "El título debe tener entre 2 y 150 caracteres")
    private String titulo;

    @Schema(description = "Actualizar capítulos vistos", example = "55")
    @PositiveOrZero(message = "Los capítulos vistos no pueden ser negativos")
    private Integer capitulosVistos;

    @Schema(description = "Actualizar preferencia de relleno", example = "false")
    private Boolean saltaRelleno;

    @Schema(description = "Actualizar puntuación personal", example = "9.5")
    @DecimalMin(value = "0.0", message = "La puntuación mínima es 0.0")
    @DecimalMax(value = "10.0", message = "La puntuación máxima es 10.0")
    private Double puntuacionPersonal;

    @Schema(description = "Actualizar plataformas")
    private List<String> plataformas;

    public AnimePatchDTO() {
    }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public Integer getCapitulosVistos() { return capitulosVistos; }
    public void setCapitulosVistos(Integer capitulosVistos) { this.capitulosVistos = capitulosVistos; }

    public Boolean getSaltaRelleno() { return saltaRelleno; }
    public void setSaltaRelleno(Boolean saltaRelleno) { this.saltaRelleno = saltaRelleno; }

    public Double getPuntuacionPersonal() { return puntuacionPersonal; }
    public void setPuntuacionPersonal(Double puntuacionPersonal) { this.puntuacionPersonal = puntuacionPersonal; }

    public List<String> getPlataformas() { return plataformas; }
    public void setPlataformas(List<String> plataformas) { this.plataformas = plataformas; }
}
