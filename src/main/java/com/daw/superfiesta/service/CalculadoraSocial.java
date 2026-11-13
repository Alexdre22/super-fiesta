
package com.daw.superfiesta.service;

import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
public class CalculadoraSocial {

    private static final int MINUTOS_CAPITULO = 24;
    private static final int MINUTOS_DIA = 1440;

    public double calcularPeligro(int capitulosVistos,
                                  LocalDate fechaInicio) {

        if (fechaInicio == null || fechaInicio.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Fecha de inicio no válida");
        }

        if (capitulosVistos < 0) {
            throw new IllegalArgumentException("Los capítulos no pueden ser negativos");
        }

        long dias = ChronoUnit.DAYS.between(fechaInicio, LocalDate.now());

        dias = Math.max(1, dias);

        double minutosTotales = capitulosVistos * (double) MINUTOS_CAPITULO;
        double minutosDiarios = minutosTotales / dias;

        double porcentaje = (minutosDiarios / MINUTOS_DIA) * 100;

        return Math.min(100, Math.round(porcentaje * 100.0) / 100.0);
    }
}
