package com.ebercruz.myrlux.back.util;

public class AlumnoNotFoundException extends RuntimeException {
    public AlumnoNotFoundException(Long id) {
        super("No se encontró el alumno con id: " + id);
    }
}
