package com.daw.superfiesta.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "DTO de salida con los datos locales del anime")
public class AnimeResumenDTO {

    private Long id;
    private String titulo;
    private Integer capitulosVistos;
    private Boolean saltaRelleno;
    private Double puntuacionPersonal;
    private List<String> plataformas;

    public AnimeResumenDTO() {
    }

    public AnimeResumenDTO(Long id, String titulo, Integer capitulosVistos,
                           Boolean saltaRelleno, Double puntuacionPersonal, List<String> plataformas) {
        this.id = id;
        this.titulo = titulo;
        this.capitulosVistos = capitulosVistos;
        this.saltaRelleno = saltaRelleno;
        this.puntuacionPersonal = puntuacionPersonal;
        this.plataformas = plataformas;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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
