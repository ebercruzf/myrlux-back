package com.ebercruz.myrluxb.deepseek.api.util;

public class AlumnoNotFoundException extends RuntimeException {
    public AlumnoNotFoundException(Long id) {
        super("No se encontró el alumno con id: " + id);
    }
}
