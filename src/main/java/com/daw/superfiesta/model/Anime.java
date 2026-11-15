package com.daw.superfiesta.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "animes")
public class Anime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 150)
    private String titulo;

    @Column(name = "capitulos_vistos", nullable = false)
    private Integer capitulosVistos;

    @Column(name = "salta_relleno", nullable = false)
    private Boolean saltaRelleno;

    @Column(name = "puntuacion_personal")
    private Double puntuacionPersonal;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "anime_plataformas",
            joinColumns = @JoinColumn(name = "anime_id")
    )
    @Column(name = "plataforma", length = 50)
    private List<String> plataformas = new ArrayList<>();

    public Anime() {
    }

    public Anime(Long id,
                 String titulo,
                 Integer capitulosVistos,
                 Boolean saltaRelleno,
                 Double puntuacionPersonal,
                 List<String> plataformas) {
        this.id = id;
        this.titulo = titulo;
        this.capitulosVistos = capitulosVistos;
        this.saltaRelleno = saltaRelleno;
        this.puntuacionPersonal = puntuacionPersonal;
        this.plataformas = (plataformas != null) ? plataformas : new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getCapitulosVistos() {
        return capitulosVistos;
    }

    public void setCapitulosVistos(Integer capitulosVistos) {
        this.capitulosVistos = capitulosVistos;
    }

    public Boolean getSaltaRelleno() {
        return saltaRelleno;
    }

    public void setSaltaRelleno(Boolean saltaRelleno) {
        this.saltaRelleno = saltaRelleno;
    }

    public Double getPuntuacionPersonal() {
        return puntuacionPersonal;
    }

    public void setPuntuacionPersonal(Double puntuacionPersonal) {
        this.puntuacionPersonal = puntuacionPersonal;
    }

    public List<String> getPlataformas() {
        return plataformas;
    }

    public void setPlataformas(List<String> plataformas) {
        this.plataformas = plataformas;
    }
}