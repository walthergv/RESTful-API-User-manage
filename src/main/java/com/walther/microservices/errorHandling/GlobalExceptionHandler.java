package com.walther.microservices.errorHandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final String USER_NOT_FOUND_MESSAGE = "User not found";

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(ValidationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getErrors().forEach(error -> {
            String[] parts = error.split(":");
            if (parts.length >= 2) {
                String field = parts[0].trim();
                String message = parts[1].trim();
                errors.put(field, message);
            }
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<?, ?> handleRuntimeException(RuntimeException ex) {
        return Map.of("error", "Error interno del servidor: " + ex.getMessage(), "success", false);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(org.springframework.http.HttpStatus.BAD_REQUEST)
    public Map<String, String> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        return Map.of("error", "El parámetro " + ex.getName() + " debe ser de tipo " + ex.getRequiredType().getSimpleName());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(
                error -> {
                    String fieldName = ((FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();
                    errors.put(fieldName, errorMessage);
                });
        return errors;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<?, ?> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return Map.of("error", "El cuerpo de la petición no es válido: " + ex.getCause(), "success", false);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<?, ?> handleHttpClientErrorException(HttpClientErrorException ex) {
        return Map.of("error", "Error en la petición al servidor externo: " + ex.getMessage(), "success", false);
    }

    @ExceptionHandler(ResponseStatusException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<?, ?> handleResponseStatusException(ResponseStatusException ex) {
        return Map.of("error", USER_NOT_FOUND_MESSAGE + ex.getMessage(), "success", false);
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<?, ?> handleNoSuchFieldError(NoSuchElementException ex) {
        return Map.of("error", "Usuario no encontrado " + ex.getMessage(), "success", false);
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<?, ?> handleNullPointerException(NullPointerException ex) {
        return Map.of("error", "Error interno del servidor: " + ex.getMessage(), "success", false);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public Map<?, ?> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        return Map.of("error", "Método no permitido: " + ex.getMessage(), "success", false);
    }
}
