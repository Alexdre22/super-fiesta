
package com.daw.superfiesta.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ERROR 404: Recurso no encontrado
    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<ErrorResponse> recursoNoEncontrado(
            RecursoNoEncontradoException ex) {

        ErrorResponse error = new ErrorResponse(
                404, ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    // ERROR 400: Datos de anime incorrectos
    @ExceptionHandler(DatosAnimeInvalidosException.class)
    public ResponseEntity<ErrorResponse> datosInvalidos(
            DatosAnimeInvalidosException ex) {

        ErrorResponse error = new ErrorResponse(
                400, ex.getMessage()
        );

        return ResponseEntity.badRequest().body(error);
    }

    // ERROR 400: Validaciones con @Valid
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> errorValidacion(
            MethodArgumentNotValidException ex) {

        String mensaje = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " +
                        error.getDefaultMessage())
                .findFirst()
                .orElse("Error de validación");

        ErrorResponse error = new ErrorResponse(400, mensaje);

        return ResponseEntity.badRequest().body(error);
    }

    // ERROR 409: Conflicto con la base de datos
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> conflictoSQL(
            DataIntegrityViolationException ex) {

        ErrorResponse error = new ErrorResponse(
                409, "Conflicto de integridad en la base de datos"
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

}
