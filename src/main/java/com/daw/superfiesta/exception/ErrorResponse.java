
package com.daw.superfiesta.exception;

import java.time.LocalDateTime;

public class ErrorResponse {

    private int codigo;
    private String mensaje;
    private LocalDateTime fecha;

    public ErrorResponse(int codigo, String mensaje) {
        this.codigo = codigo;
        this.mensaje = mensaje;
        this.fecha = LocalDateTime.now();
    }

    public int getCodigo() {
        return codigo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }
}
