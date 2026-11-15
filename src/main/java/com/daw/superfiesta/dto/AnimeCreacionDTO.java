package com.daw.superfiesta.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.util.List;

@Schema(description = "DTO de entrada para crear o reemplazar un anime")
public class AnimeCreacionDTO {

    @Schema(description = "Título oficial del anime", example = "Jujutsu Kaisen")
    @NotBlank(message = "El título es obligatorio")
    @Size(min = 2, max = 150, message = "El título debe tener entre 2 y 150 caracteres")
    private String titulo;

    @Schema(description = "Capítulos vistos actualmente", example = "47")
    @NotNull(message = "Los capítulos vistos son obligatorios")
    @PositiveOrZero(message = "Los capítulos vistos no pueden ser negativos")
    private Integer capitulosVistos;

    @Schema(description = "Indica si el usuario se salta los capítulos de relleno", example = "true")
    @NotNull(message = "Debe indicar si salta el relleno")
    private Boolean saltaRelleno;

    @Schema(description = "Puntuación personal del 0.0 al 10.0", example = "9.2")
    @DecimalMin(value = "0.0", message = "La puntuación mínima es 0.0")
    @DecimalMax(value = "10.0", message = "La puntuación máxima es 10.0")
    private Double puntuacionPersonal;

    @Schema(description = "Plataformas donde se visualiza", example = "[\"Crunchyroll\", \"Netflix\"]")
    @NotEmpty(message = "Debe indicar al menos una plataforma")
    private List<String> plataformas;

    @Schema(description = "Confirmación de asumir el riesgo sobre la vida social", example = "true")
    @AssertTrue(message = "Debes aceptar el riesgo de pérdida de vida social")
    private Boolean aceptaRiesgoSocial;

    public AnimeCreacionDTO() {
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

    public Boolean getAceptaRiesgoSocial() { return aceptaRiesgoSocial; }
    public void setAceptaRiesgoSocial(Boolean aceptaRiesgoSocial) { this.aceptaRiesgoSocial = aceptaRiesgoSocial; }
}
