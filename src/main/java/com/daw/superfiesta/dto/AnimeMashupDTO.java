
package com.daw.superfiesta.dto;

public class AnimeMashupDTO {

    private Long perfilId;
    private String nombreUsuario;
    private String tituloAnime;
    private int capitulosVistos;
    private double peligroVidaSocial;

    public AnimeMashupDTO(Long perfilId,
                          String nombreUsuario,
                          String tituloAnime,
                          int capitulosVistos,
                          double peligroVidaSocial) {

        this.perfilId = perfilId;
        this.nombreUsuario = nombreUsuario;
        this.tituloAnime = tituloAnime;
        this.capitulosVistos = capitulosVistos;
        this.peligroVidaSocial = peligroVidaSocial;
    }

    public Long getPerfilId() {
        return perfilId;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getTituloAnime() {
        return tituloAnime;
    }

    public int getCapitulosVistos() {
        return capitulosVistos;
    }

    public double getPeligroVidaSocial() {
        return peligroVidaSocial;
    }
}
