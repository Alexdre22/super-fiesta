
package com.daw.superfiesta.service;

import java.time.LocalDate;
import java.util.List;

public interface PerfilDataProvider {

    List<PerfilAnime> obtenerPerfiles();

    record PerfilAnime(
            Long perfilId,
            String nombreUsuario,
            Long animeId,
            int capitulosVistos,
            LocalDate fechaInicio
    ) {}
}
