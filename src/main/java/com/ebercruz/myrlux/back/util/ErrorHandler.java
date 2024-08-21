package com.ebercruz.myrlux.back.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class ErrorHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorHandler.class);

    public static <T> Mono<ResponseEntity<ApiResponse<T>>> handleCreateError(Throwable ex) {
        if (ex instanceof CustomerExcepction.BadRequestException) {
            return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(null, ex.getMessage(), "400", false)));
        } else if (ex instanceof CustomerExcepction.ValidationException) {
            CustomerExcepction.ValidationException validationEx = (CustomerExcepction.ValidationException) ex;
            return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(null, "Errores de validación", "400", false, validationEx.getErrors())));
        } else if (ex instanceof CustomerExcepction.ResourceNotFoundException) {
            return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(null, ex.getMessage(), "404", false)));
        }
        LOGGER.error("Error al crear alumno", ex);
        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(null, "Error interno del servidor", "500", false)));
    }

    public static <T> Mono<ResponseEntity<ApiResponse<T>>> handleUpdateError(Throwable ex) {

        if (ex instanceof CustomerExcepction.ValidationException) {
            CustomerExcepction.ValidationException validationEx = (CustomerExcepction.ValidationException) ex;
            LOGGER.warn("Error de validación al actualizar alumno: {}", validationEx.getErrors());
            return Mono.just(ResponseEntity.badRequest()
                    .body(new ApiResponse<>(null, "Errores de validación", "400", false, validationEx.getErrors())));
        } else if (ex instanceof CustomerExcepction.ResourceNotFoundException) {
            LOGGER.warn("Recurso no encontrado al actualizar alumno: {}", ex.getMessage());
            return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(null, ex.getMessage(), "404", false)));
        }
        LOGGER.error("Error inesperado al actualizar alumno", ex);
        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(null, "Error interno del servidor", "500", false)));
    }

    public static <T> Mono<ResponseEntity<ApiResponse<T>>> handleDeleteError(Throwable ex) {
        if (ex instanceof CustomerExcepction.BadRequestException) {
            return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(null, ex.getMessage(), "400", false)));
        } else if (ex instanceof CustomerExcepction.ResourceNotFoundException) {
            return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(null, ex.getMessage(), "404", false)));
        }
        LOGGER.error("Error al eliminar alumno", ex);
        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(null, "Error interno del servidor", "500", false)));
    }

    public static <T> Mono<ResponseEntity<ApiResponse<T>>> handleGetError(Throwable ex) {
        if (ex instanceof CustomerExcepction.BadRequestException) {
            return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(null, ex.getMessage(), "400", false)));
        } else if (ex instanceof CustomerExcepction.ResourceNotFoundException) {
            return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(null, ex.getMessage(), "404", false)));
        }
        LOGGER.error("Error al obtener alumno", ex);
        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(null, "Error interno del servidor", "500", false)));
    }


}