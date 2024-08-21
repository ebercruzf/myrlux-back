package com.ebercruz.myrlux.back.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Manejador global de excepciones para la aplicación.
 *
 * Esta clase actúa como un interceptor centralizado para todas las excepciones
 * lanzadas en la aplicación, incluyendo aquellas originadas en controladores y servicios.
 * Proporciona un mecanismo uniforme para convertir excepciones en respuestas HTTP apropiadas,
 * asegurando consistencia en el manejo de errores a través de toda la aplicación.
 *
 * Características clave:
 * - Maneja excepciones específicas (como ResourceNotFoundException, BadRequestException)
 *   y excepciones genéricas.
 * - Formatea las respuestas de error utilizando la clase ApiResponse para mantener
 *   una estructura de respuesta consistente.
 * - Se aplica automáticamente a todos los controladores sin necesidad de configuración adicional.
 *
 * El uso de este manejador global permite que los controladores y servicios se centren
 * en la lógica de negocio, delegando el manejo de errores a esta clase centralizada.
 *
 * @author Eber Cruz (www.ebercruz.com)
 * @version 1.0
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomerExcepction.ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Mono<ResponseEntity<ApiResponse<Void>>> handleResourceNotFoundException(CustomerExcepction.ResourceNotFoundException ex) {
        ApiResponse<Void> response = new ApiResponse<>(HttpStatus.NOT_FOUND.toString(), ex.getMessage());
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(response));
    }

    @ExceptionHandler(CustomerExcepction.BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<ResponseEntity<ApiResponse<Void>>> handleBadRequestException(CustomerExcepction.BadRequestException ex) {
        ApiResponse<Void> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.toString(), ex.getMessage());
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response));
    }

    @ExceptionHandler(CustomerExcepction.InternalServerErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Mono<ResponseEntity<ApiResponse<Void>>> handleInternalServerErrorException(CustomerExcepction.InternalServerErrorException ex) {
        ApiResponse<Void> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.toString(), ex.getMessage());
        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Mono<ResponseEntity<ApiResponse<Void>>> handleGenericException(Exception ex) {
        ApiResponse<Void> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.toString(), "Se produjo un error inesperado");
        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response));
    }

    @ExceptionHandler(ResponseStatusException.class)
    public Mono<ResponseEntity<ApiResponse<Void>>> handleResponseStatusException(ResponseStatusException ex) {
        // Solo maneja errores específicos, no los 404 generales
        if (ex.getStatusCode().value() != HttpStatus.NOT_FOUND.value()) {
            return Mono.just(ResponseEntity
                    .status(ex.getStatusCode())
                    .body(new ApiResponse<>(null, ex.getReason(), String.valueOf(ex.getStatusCode().value()), false)));
        }
        // Para 404, deja que Spring lo maneje
        return Mono.error(ex);
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationExceptions(WebExchangeBindException ex) {
        List<String> errors = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.toList());

        ApiResponse<Void> response = new ApiResponse<>(null, "Validation failure", "400", false, errors);
        return ResponseEntity.badRequest().body(response);
    }
}