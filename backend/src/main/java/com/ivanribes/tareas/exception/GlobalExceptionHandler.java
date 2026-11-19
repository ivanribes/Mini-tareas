package com.ivanribes.tareas.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handler 1 — un DTO con @Valid que no cumple sus anotaciones
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex, HttpServletRequest request) {

        Map<String, List<String>> fields = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.groupingBy(
                        FieldError::getField,
                        Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList())
                ));

        ErrorResponse response = new ErrorResponse(
                LocalDateTime.now().toString(),
                HttpStatus.BAD_REQUEST.value(),
                "Error de validación",
                "La petición contiene campos inválidos",
                request.getRequestURI(),
                fields
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // Handler 2 — un error que ya lanzas tú explícitamente (orElseThrow, por ejemplo)
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleResponseStatusException(
            ResponseStatusException ex, HttpServletRequest request) {

        int statusCode = ex.getStatusCode().value();
        HttpStatus status = HttpStatus.resolve(statusCode);
        String error = status != null ? status.getReasonPhrase() : "Error";
        String message = ex.getReason() != null ? ex.getReason() : error;

        ErrorResponse response = new ErrorResponse(
                LocalDateTime.now().toString(), statusCode, error, message, request.getRequestURI()
        );
        return ResponseEntity.status(ex.getStatusCode()).body(response);
    }

    // Handler 5 — la red de seguridad final, para cualquier otra excepción no prevista
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex, HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse(
                LocalDateTime.now().toString(), 500, "Error interno",
                "Ha ocurrido un error inesperado", request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(
            AuthenticationException ex, HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse(
                LocalDateTime.now().toString(), 401, "No autenticado",
                "Usuario o contraseña incorrectos.", request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
}
