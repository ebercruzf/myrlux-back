package com.ebercruz.myrlux.back.dto;

import java.util.List;

public class AlumnoResponse {
    private List<AlumnoDTO> alumnos;

    public AlumnoResponse(List<AlumnoDTO> alumnos) {
        this.alumnos = alumnos;
    }

    public List<AlumnoDTO> getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(List<AlumnoDTO> alumnos) {
        this.alumnos = alumnos;
    }
}
