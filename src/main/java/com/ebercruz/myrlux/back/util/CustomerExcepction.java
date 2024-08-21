package com.ebercruz.myrlux.back.util;

import java.util.List;

/**
 * Clase que define excepciones personalizadas para la aplicación.
 *
 * Esta clase contiene subclases de excepciones que representan diferentes
 * tipos de errores específicos del dominio de la aplicación. Estas excepciones
 * son utilizadas en la lógica de negocio para señalar condiciones de error
 * con significado semántico en el contexto de la aplicación.
 *
 * Características principales:
 * - Proporciona excepciones específicas para diferentes escenarios de error.
 * - Permite un control más granular sobre los tipos de errores en las operaciones de negocio.
 * - Se integra con el GlobalExceptionHandler para un manejo consistente de errores.
 *
 * Uso:
 * Estas excepciones se lanzan en las capas de servicio y son capturadas
 * por el GlobalExceptionHandler para ser convertidas en respuestas HTTP apropiadas.
 *
 * @author Eber Cruz (www.ebercruz.com)
 * @version 1.0
 */

public class CustomerExcepction {

    public static class BadRequestException extends RuntimeException {
        public BadRequestException(String message) {
            super(message);
        }
    }

    public static class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String message) {
            super(message);
        }
    }

    public static class InternalServerErrorException extends RuntimeException {
        public InternalServerErrorException(String message) {
            super(message);
        }
    }

    public static class ValidationException extends RuntimeException {
        private final List<String> errors;

        public ValidationException(String message, List<String> errors) {
            super(message);
            this.errors = errors;
        }

        public List<String> getErrors() {
            return errors;
        }
    }




}
